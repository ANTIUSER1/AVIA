package ru.integrotech.su.exceptions;

import ru.integrotech.airline.exceptions.ErrorSupporter;

/*
 * Throws in case of not proper request parameter */
public class UnsupportedParamException extends Exception {
	public UnsupportedParamException(String message) {
		super(ErrorSupporter.DEFAULT_ERROR_MSG + message);
	}
}
