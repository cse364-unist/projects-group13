package com.example.cse364project.part3.exception;

public class RatingNotFoundException extends RuntimeException{
    public RatingNotFoundException(String msg) {
        super(msg);
    }
}
