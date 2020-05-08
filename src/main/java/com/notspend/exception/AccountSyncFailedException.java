package com.notspend.exception;

public class AccountSyncFailedException extends Exception {
    public AccountSyncFailedException(String errorMessage){
        super(errorMessage);
    }

    public AccountSyncFailedException(String errorMessage, Throwable e){
        super(errorMessage, e);
    }
}
