package com.example.cse364project.exception;

public class RatingNotFoundException extends RuntimeException{
    public RatingNotFoundException(String msg) {
        super(msg);
    }
}
