package com.example.dpgmedia.exception

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.ServletException

@ControllerAdvice
@RestController
class TopLevelExceptionHandler {
    private val LOG = LogManager.getLogger(TopLevelExceptionHandler::class)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [ServletException::class])
    fun handleServletException(e: ServletException): ErrorResponse {
        return wrap(ErrorId.BAD_REQUEST.errorId, e, e.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [HttpClientErrorException::class])
    fun handleServletException(e: HttpClientErrorException): ErrorResponse {
        return wrap(ErrorId.BAD_REQUEST.errorId, e, e.message)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [NoHandlerFoundException::class])
    fun handleNotFound(e: NoHandlerFoundException): ErrorResponse {
        return wrap(ErrorId.NOT_FOUND.errorId, e, e.message)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [RuntimeException::class])
    fun handleBaseException(e: RuntimeException): ErrorResponse {
        return wrap(ErrorId.DEFAULT.errorId, e, e.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handleBaseException(e: IllegalArgumentException): ErrorResponse {
        return wrap(ErrorId.BAD_REQUEST.errorId, e, e.message)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [Throwable::class])
    fun handleOthers(e: Throwable): ErrorResponse {
        return wrap(ErrorId.DEFAULT.errorId, e, e.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonMappingException::class)
    fun handleJsonMappingException(e: JsonMappingException): ErrorResponse {
        return wrap(ErrorId.BAD_REQUEST.errorId, e, e.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonParseException::class)
    fun handleJsonMappingException(e: JsonParseException): ErrorResponse {
        return wrap(ErrorId.BAD_REQUEST.errorId, e, e.message)
    }

    private fun wrap(errorId: Int, e: Throwable, errorMessage: String?): ErrorResponse {
        LOG.error(e.message, e)
        return ErrorResponse(errorId, errorMessage!!)
    }
}