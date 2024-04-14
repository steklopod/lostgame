package tech.example.lostgame.utils

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import tech.example.lostgame.exceptions.InvalidSignatureException
import java.security.MessageDigest


object SecurityUtils {
    private const val SECRET = "I_Love_Kotlin"
    private const val ALGORITHM = "MD5"
    private val log = LoggerFactory.getLogger(SecurityUtils::class.java)
    private val mapper = jacksonObjectMapper().findAndRegisterModules()

    fun validateSignature(data: String?, hash: String?) {
        if (data == null || hash == null) {
            log.error("Sign hash is null")
            throw InvalidSignatureException()
        }
        val expectedHash = generateSignature(data)
        if (expectedHash != hash) throw InvalidSignatureException()
    }

    fun generateSignature(data: String): String {
        val message = "${normalizeJson(data)}$SECRET".toByteArray()
        val digest = MessageDigest.getInstance(ALGORITHM)
        val hashedBytes = digest.digest(message)
        return hashedBytes.joinToString("") { "%02x".format(it) } // Convert bytes to hex string
    }

    private fun normalizeJson(json: String): String {
        val tree = mapper.readValue(json, JsonNode::class.java)
        return mapper.writeValueAsString(tree)
    }

}
