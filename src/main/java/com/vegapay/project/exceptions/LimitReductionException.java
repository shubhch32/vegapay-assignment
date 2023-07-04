package com.vegapay.project.exceptions;

public class LimitReductionException extends Exception {
    public LimitReductionException() {
    }

    public LimitReductionException(String message) {
        super(message);
    }
}
