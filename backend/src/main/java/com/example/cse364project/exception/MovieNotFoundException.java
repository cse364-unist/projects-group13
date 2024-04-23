package com.example.cse364project.exception;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(String msg) {
        super(msg);
    }
}
