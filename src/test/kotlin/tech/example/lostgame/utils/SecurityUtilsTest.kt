package tech.example.lostgame.utils

import org.junit.jupiter.api.Test

class SecurityUtilsTest {
    @Test
    fun `Generate signature test`() {
        val json = """{
                        "gameSessionId": "c3eb0de1-65ba-434f-b2aa-8912b76870ae",
                        "currency": "BTC"
                      }"""

        val hash: String = SecurityUtils.generateSignature(json)

        println(hash)

        SecurityUtils.validateSignature(data = json, hash = hash)
    }
}
