package tech.example.lostgame.service

import org.springframework.stereotype.Service
import tech.example.lostgame.model.BalanceTransaction
import tech.example.lostgame.repository.BalanceTransactionRepository
import java.util.*

@Service
class BalanceTransactionService(private val repository: BalanceTransactionRepository) {
    fun exists(transactionId: UUID): Boolean = repository.existsById(transactionId)

    fun save(transaction: BalanceTransaction): BalanceTransaction = repository.save(transaction)
}
