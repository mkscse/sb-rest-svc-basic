package com.mks.springboot.rest.example.student;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String exception) {
        super(exception);
    }

}
