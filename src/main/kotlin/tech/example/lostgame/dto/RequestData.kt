package tech.example.lostgame.dto

import java.util.*

data class RequestCommon(
    val api: ApiType,
    val data: Any
)

interface RequestData{
    val gameSessionId: UUID
    val currency: Currency
}
