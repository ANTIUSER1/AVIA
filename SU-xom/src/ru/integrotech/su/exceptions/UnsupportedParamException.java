package ru.integrotech.su.exceptions;

/*
* Throws in case of not proper request parameter */
public class UnsupportedParamException extends Exception {
    public UnsupportedParamException(String message) {
        super(message);
    }
}
