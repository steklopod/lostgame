package tech.example.lostgame.controller

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tech.example.lostgame.model.UserBalance
import tech.example.lostgame.model.UserSession
import tech.example.lostgame.repository.SessionRepository
import tech.example.lostgame.repository.UserBalanceRepository
import tech.example.lostgame.utils.SecurityUtils
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
internal class GameBalanceControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val sessionRepository: SessionRepository,
    @Autowired private val userBalanceRepository: UserBalanceRepository,
) {
    private val baseUrl = "/open-api-games/v1/games-processor"

    private val user = UserBalance(
        userId = UUID.randomUUID(),
        userNick = "tester",
        amount = 100,
        denomination = 8,
        maxWin = 15000,
        currency = "BTC",
    )
    private val gameSession = UserSession(UUID.randomUUID()).apply { userBalance = user }

    @BeforeEach
    fun init() {
        userBalanceRepository.save(user)
        sessionRepository.save(gameSession)
    }

    @AfterEach
    fun clear() {
        userBalanceRepository.deleteById(user.userId)
    }

    @Test
    fun `should return user BALANCE`() {
        val data = """{
                         "gameSessionId": "${gameSession.sessionId}",
                         "currency": "BTC"
                      }"""
        val request = """{ "api": "balance", "data": $data }"""
        val signature = SecurityUtils.generateSignature(data)

        mockMvc.perform(
            post(baseUrl)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Sign", signature)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.userNick").value(user.userNick))
            .andExpect(jsonPath("$.amount").value(user.amount))
            .andExpect(jsonPath("$.denomination").value(user.denomination))
            .andExpect(jsonPath("$.maxWin").value(user.maxWin))
            .andExpect(jsonPath("$.currency").value(user.currency))
            .andExpect(jsonPath("$.userId").value(user.userId.toString()))
            .andExpect(jsonPath("$.jpKey").doesNotExist())
    }

    @Test
    fun `should debit (+) user balance only once`() {
        val transactionId = UUID.randomUUID()
        val amountInit = user.amount
        val debitAmount = 1

        val data = """{
                          "transactionId": "$transactionId",
                          "gameSessionId": "${gameSession.sessionId}",
                          "currency": "RUB",
                          "amount": $debitAmount
                      }"""

        val request = """{ "api": "debit", "data": $data }"""

        val signature = SecurityUtils.generateSignature(data)

        mockMvc.perform(
            post(baseUrl)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Sign", signature)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.api").value("debit"))
            .andExpect(jsonPath("$.isSuccess").value("true"))
            .andExpect(jsonPath("$.error").isEmpty)
            .andExpect(jsonPath("$.errorMsg").value("NO_ERRORS"))
            .andExpect(jsonPath("$.data.amount").value(amountInit + debitAmount))
            .andExpect(jsonPath("$.data.userId").value(user.userId.toString()))

        // Duplicated debit request
        mockMvc.perform(
            post(baseUrl)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Sign", signature)
        )
            .andExpect(status().isConflict)
    }


    @Test
    fun `should credit (-) user balance only once`() {
        val transactionId = UUID.randomUUID()
        val amountInit = user.amount
        val creditAmount = 1

        val data = """{
                          "transactionId": "$transactionId",
                          "gameSessionId": "${gameSession.sessionId}",
                          "currency": "RUB",
                          "amount": $creditAmount
                      }"""
        val request = """{ "api": "credit", "data": $data }"""
        val signature = SecurityUtils.generateSignature(data)

        mockMvc.perform(
            post(baseUrl)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Sign", signature)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.api").value("credit"))
            .andExpect(jsonPath("$.isSuccess").value("true"))
            .andExpect(jsonPath("$.error").isEmpty)
            .andExpect(jsonPath("$.errorMsg").value("NO_ERRORS"))
            .andExpect(jsonPath("$.data.amount").value(amountInit - creditAmount))
            .andExpect(jsonPath("$.data.userId").value(user.userId.toString()))

        // Duplicated credit request
        mockMvc.perform(
            post(baseUrl)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Sign", signature)
        )
            .andExpect(status().isConflict)
    }


    @Test
    fun `should NOT credit (-) user balance if balance is too low`() {
        val transactionId = UUID.randomUUID()
        val creditAmount = 1_000_000

        val data = """{
                          "transactionId": "$transactionId",
                          "gameSessionId": "${gameSession.sessionId}",
                          "currency": "RUB",
                          "amount": $creditAmount
                      }"""
        val request = """{ "api": "credit", "data": $data }"""
        val signature = SecurityUtils.generateSignature(data)

        mockMvc.perform(
            post(baseUrl)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Sign", signature)
        )
            .andExpect(status().isPaymentRequired)
    }


    @Test
    fun `should throw NOT_FOUND is there is no such game Session id`() {
        val data = """{
                        "gameSessionId": "${UUID.randomUUID()}",
                        "currency": "BTC"
                      }"""
        val request = """{ "api": "balance", "data": $data }"""
        val signature = SecurityUtils.generateSignature(data)

        mockMvc.perform(
            post(baseUrl)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Sign", signature)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isNotFound)
    }

}
