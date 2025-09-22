package com.rishi.todoapp.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String msg) {
        super(msg);
    }
}
