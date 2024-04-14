package tech.example.lostgame.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.example.lostgame.dto.ApiType.balance
import tech.example.lostgame.dto.ApiType.credit
import tech.example.lostgame.dto.ApiType.debit
import tech.example.lostgame.dto.BalanceOperation
import tech.example.lostgame.dto.BalanceRequest
import tech.example.lostgame.dto.RequestCommon
import tech.example.lostgame.service.UserBalanceService
import tech.example.lostgame.utils.SecurityUtils

@RestController
@RequestMapping("/open-api-games/v1/games-processor")
class GameBalanceController(private val userBalanceService: UserBalanceService) {

    @PostMapping
    fun processUserBalance(@RequestBody request: RequestCommon, @RequestHeader("Sign") signHeader: String?): Any {
        val body = request.data
        SecurityUtils.validateSignature(mapper.writeValueAsString(body), hash = signHeader)

        log.info("Got ${request.api} request")
        return when (request.api) {
            balance -> userBalanceService.getUserBalance(toBalanceRequest(body).gameSessionId)
            credit -> userBalanceService.creditOperation(toBalanceOperation(body))
            debit -> userBalanceService.debitOperation(toBalanceOperation(body))
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(GameBalanceController::class.java)
        private val mapper = jacksonObjectMapper().findAndRegisterModules()
        private fun toBalanceRequest(data: Any) = mapper.convertValue(data, BalanceRequest::class.java)
        private fun toBalanceOperation(data: Any) = mapper.convertValue(data, BalanceOperation::class.java)
    }
}


