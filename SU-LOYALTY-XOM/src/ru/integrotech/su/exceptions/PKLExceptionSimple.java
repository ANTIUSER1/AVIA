package ru.integrotech.su.exceptions;

import ru.integrotech.airline.exceptions.ErrorSupporter;

public class PKLExceptionSimple extends Exception{
	public PKLExceptionSimple(String message) {
		super(ErrorSupporter.DEFAULT_ERROR_MSG + message);
	}
}
