package com.godeltech.mastery.task.service.exception;

public class OperationFailedException extends RuntimeException{
    public OperationFailedException(String msg) {
        super(msg);
    }
}
