package tech.example.lostgame.dto

import tech.example.lostgame.model.UserBalance

data class BalanceChangeResponse(
    val api: ApiType,
    val isSuccess: Boolean = true,
    val error: String = "",
    val errorMsg: String = "NO_ERRORS",
    val data: UserBalance
)
