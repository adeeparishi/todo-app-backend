package com.rishi.todoapp.exception;


import java.io.Serial;

public class UnExpectedException extends RuntimeException {


    public UnExpectedException(String msg) {
        super(msg);
    }

}

