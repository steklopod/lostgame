package tech.example.lostgame.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.PAYMENT_REQUIRED
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import tech.example.lostgame.exceptions.ErrorCode.ALREADY_PROCESSED
import tech.example.lostgame.exceptions.ErrorCode.INSUFFICIENT_BALANCE
import tech.example.lostgame.exceptions.ErrorCode.INTERNAL_ERROR
import tech.example.lostgame.exceptions.ErrorCode.INVALID_SIGN
import tech.example.lostgame.exceptions.ErrorCode.NOT_FOUND
import java.util.*

@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun handleOtherException(ex: Exception): ErrorCode {
        log.error("Other error ", ex)
        return INTERNAL_ERROR
    }

    @ExceptionHandler(AlreadyExecutedException::class)
    @ResponseStatus(CONFLICT)
    fun handleAlreadyExecutedException(ex: AlreadyExecutedException): ErrorCode {
        log.error("ALREADY_PROCESSED for transaction Id = ${ex.transactionId}", ex)
        return ALREADY_PROCESSED
    }

    @ExceptionHandler(InsufficientBalanceException::class)
    @ResponseStatus(PAYMENT_REQUIRED)
    fun handleInsufficientBalanceException(ex: InsufficientBalanceException): ErrorCode {
        log.error("INSUFFICIENT_BALANCE for transaction Id = ${ex.transactionId}", ex)
        return INSUFFICIENT_BALANCE
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(ex: NotFoundException): ErrorCode {
        log.error("NOT_FOUND for gameSessionId = ${ex.gameSessionId}", ex)
        return NOT_FOUND
    }
    @ExceptionHandler(InvalidSignatureException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleInvalidSignatureException(ex: InvalidSignatureException): ErrorCode {
        log.error("INVALID_SIGN", ex)
        return INVALID_SIGN
    }

    companion object {
        private val log = LoggerFactory.getLogger(ErrorHandler::class.java)
    }
}

data class NotFoundException(val gameSessionId: UUID) : RuntimeException()
data class AlreadyExecutedException(val transactionId: UUID) : RuntimeException()
data class InsufficientBalanceException(val transactionId: UUID) : RuntimeException()
class InvalidSignatureException : RuntimeException()


enum class ErrorCode {
    INTERNAL_ERROR,
    ALREADY_PROCESSED,
    INSUFFICIENT_BALANCE,
    NOT_FOUND,
    INVALID_SIGN
    ;
}
