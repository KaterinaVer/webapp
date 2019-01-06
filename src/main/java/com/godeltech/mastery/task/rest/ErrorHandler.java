package com.godeltech.mastery.task.rest;

import com.godeltech.mastery.task.service.exception.OperationFailedException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(OperationFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorWrapper handleOperationFailedException(OperationFailedException exception) {
        return ErrorWrapper.wrap(exception);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorWrapper handleEmptyResultDataAccessException(EmptyResultDataAccessException exception) {
        return ErrorWrapper.wrap(exception);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorWrapper handleDataAccessException(DataAccessException exception) {
        return ErrorWrapper.wrap(exception);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorWrapper handleIllegalArgumentException(IllegalArgumentException exception) {
        return ErrorWrapper.wrap(exception);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorWrapper handleIllegalStateException(IllegalStateException exception) {
        return ErrorWrapper.wrap(exception);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorWrapper handleAllExceptions(Exception exception) {
        return ErrorWrapper.wrap(exception);
    }
}
