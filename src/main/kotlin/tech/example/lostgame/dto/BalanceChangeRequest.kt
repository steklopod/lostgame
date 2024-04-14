package tech.example.lostgame.dto

import tech.example.lostgame.model.BalanceTransaction
import tech.example.lostgame.model.UserBalance
import java.util.*

//data class BalanceChangeRequest(val api: ApiType, val data: BalanceOperation)

data class BalanceOperation(
    override val currency: Currency,
    override val gameSessionId: UUID,
    val amount: Long,
    val transactionId: UUID,

    val betId: String? = null,
    val notes: String? = null,
    val betMeta: BetMeta? = null,
    val spinMeta: SpinMeta? = null,
) : RequestData {
    fun toTransaction(userBalance: UserBalance) = BalanceTransaction(
        id = transactionId,
        amount = amount,
        currency = currency,
        gameSessionId = gameSessionId,
    ).apply { this.userBalance = userBalance }
}


data class BetMeta(val bets: List<Bet>? = null)

data class SpinMeta(
    val betPerLine: Int? = null,
    val lines: Int? = null,
    val symbolMatrix: List<List<Int>>? = null,
    val totalBet: Int? = null,
)


data class Bet(
    val amount: Int? = null,
    val balls: List<Int>? = null,
    val colors: List<String>? = null,
)
