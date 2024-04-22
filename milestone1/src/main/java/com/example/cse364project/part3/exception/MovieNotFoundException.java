package com.example.cse364project.part3.exception;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(String msg) {
        super(msg);
    }
}
