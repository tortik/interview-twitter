package com.hsbc.interviewtwitter.common.exception;



public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
