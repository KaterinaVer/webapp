package com.godeltech.mastery.task.rest.errorHandlers;

import org.springframework.http.HttpStatus;

public class ErrorWrapper {

    private HttpStatus status;
    private String message;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorWrapper(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
