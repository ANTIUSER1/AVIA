package ru.integrotech.airline.exceptions;

/**
 * Throws in attempt to add some Flight in any object with violate logic <br>
 * Can be used in all projects
 */

public class UnsupportedFlightException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnsupportedFlightException(String unsupported_flight) {
		super(ErrorSupporter.DEFAULT_ERROR_MSG + unsupported_flight);
	}
}
