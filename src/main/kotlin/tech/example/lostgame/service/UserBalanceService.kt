package tech.example.lostgame.service

import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import tech.example.lostgame.controller.GameBalanceController
import tech.example.lostgame.dto.ApiType.credit
import tech.example.lostgame.dto.ApiType.debit
import tech.example.lostgame.dto.BalanceChangeResponse
import tech.example.lostgame.dto.BalanceOperation
import tech.example.lostgame.exceptions.AlreadyExecutedException
import tech.example.lostgame.exceptions.InsufficientBalanceException
import tech.example.lostgame.exceptions.NotFoundException
import tech.example.lostgame.model.BalanceTransaction
import tech.example.lostgame.model.UserBalance
import tech.example.lostgame.repository.SessionRepository
import tech.example.lostgame.repository.UserBalanceRepository
import java.util.*

@Service
class UserBalanceService(
    private val userBalanceRepository: UserBalanceRepository,
    private val sessionRepository: SessionRepository,
    private val balanceTransactionService: BalanceTransactionService,
) {

    fun getUserBalance(gameSessionId: UUID): UserBalance {
        val userBalance: UserBalance = sessionRepository.findByIdOrNull(gameSessionId)
            ?.userBalance
            ?: throw NotFoundException(gameSessionId)
        log.info("💶 balance is found for user: ${userBalance.userId}")
        return userBalance
    }

    @Transactional
    fun creditOperation(data: BalanceOperation): BalanceChangeResponse {
        val transactionId: UUID = checkTransaction(data)

        val userBalance: UserBalance = getUserBalance(data.gameSessionId)
        if (userBalance.amount < data.amount) throw InsufficientBalanceException(transactionId) // <-- CHECK
        userBalance.amount -= data.amount

        updateBalance(userBalance, data)

        log.info("(−) Credit processed for user: ${userBalance.userId}")
        return BalanceChangeResponse(api = credit, data = userBalance)
    }


    @Transactional
    fun debitOperation(data: BalanceOperation): BalanceChangeResponse {
        checkTransaction(data)

        val userBalance: UserBalance = getUserBalance(data.gameSessionId)
        userBalance.amount += data.amount

        updateBalance(userBalance, data)

        log.info("(+) Debit processed for user: ${userBalance.userId}")
        return BalanceChangeResponse(api = debit, data = userBalance)
    }

    private fun updateBalance(userBalance: UserBalance, data: BalanceOperation) {
        userBalanceRepository.save(userBalance)
        val transaction: BalanceTransaction = data.toTransaction(userBalance)
        balanceTransactionService.save(transaction)
    }

    private fun checkTransaction(data: BalanceOperation): UUID {
        val transactionId: UUID = data.transactionId
        val alreadyExecuted: Boolean = balanceTransactionService.exists(transactionId)
        if (alreadyExecuted) throw AlreadyExecutedException(transactionId)
        return transactionId
    }


    companion object {
        private val log = LoggerFactory.getLogger(GameBalanceController::class.java)

    }
}
