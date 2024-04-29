package com.example.cse364project.exception;

public class ActorNotFoundException extends RuntimeException{
    public ActorNotFoundException(String msg) {
        super(msg);
    }
}
