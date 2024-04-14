package tech.example.lostgame.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
internal class SecurityControllerTest(
    @Autowired private val mockMvc: MockMvc,
) {
    private val baseUrl = "/open-api-games/v1/games-processor"

    @Test
    fun `should throw Forbidden when invalid signature`() {
        val data = """{
                         "gameSessionId": "c3eb0de1-65ba-434f-b2aa-8912b76870ae",
                         "currency": "BTC"
                      }"""
        val request = """{ "api": "balance", "data": $data }"""

        mockMvc.perform(
            post(baseUrl)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Sign", "e056ea5c4172c84287e140f9709154e7")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isForbidden)
    }

    @Test
    fun `should throw Forbidden when no signature`() {
        val data = """{
                         "gameSessionId": "c3eb0de1-65ba-434f-b2aa-8912b76870ae",
                         "currency": "BTC"
                      }"""
        val request = """{ "api": "balance", "data": $data }"""

        mockMvc.perform(
            post(baseUrl)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isForbidden)
    }

}
