package tech.example.lostgame.dto

import java.util.*

data class BalanceRequest(
    override val gameSessionId: UUID,
    override val currency: Currency
) : RequestData

