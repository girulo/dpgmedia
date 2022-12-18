package com.example.dpgmedia.exception;

import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@ControllerAdvice
@RestController
public class TopLevelExceptionHandler {

    private final Logger LOG = LogManager.getLogger(TopLevelExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ServletException.class)
    public ErrorResponse handleServletException(final ServletException e) {
        return wrap(ErrorId.BAD_REQUEST.getErrorId(), e, e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ErrorResponse handleNotFound(final NoHandlerFoundException e) {
        return wrap(ErrorId.NOT_FOUND.getErrorId(), e, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = RuntimeException.class)
    public ErrorResponse handleBaseException(final RuntimeException e) {
        return wrap(ErrorId.DEFAULT.getErrorId(), e, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ErrorResponse handleBaseException(final IllegalArgumentException e) {
        return wrap(ErrorId.BAD_REQUEST.getErrorId(), e, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Throwable.class)
    public ErrorResponse handleOthers(final Throwable e) {
        return wrap(ErrorId.DEFAULT.getErrorId(), e, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonMappingException.class)
    public ErrorResponse handleJsonMappingException(final JsonMappingException e) {
        return wrap(ErrorId.BAD_REQUEST.getErrorId(), e, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonParseException.class)
    public ErrorResponse handleJsonMappingException(final JsonParseException e) {
        return wrap(ErrorId.BAD_REQUEST.getErrorId(), e, e.getMessage());
    }

    private ErrorResponse wrap(final int errorId, final Throwable e, final String errorMessage) {
        LOG.error(e.getMessage(), e);
        return new ErrorResponse(errorId, errorMessage);
    }
}
