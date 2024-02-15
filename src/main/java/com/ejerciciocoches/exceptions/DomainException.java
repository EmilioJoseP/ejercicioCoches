package com.ejerciciocoches.exceptions;

public class DomainException extends RuntimeException {
    public DomainException(String msg) {
        super(msg);
    }
}