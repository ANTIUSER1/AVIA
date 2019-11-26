package ru.integrotech.airline.exceptions;

/* throws in attempt to add some Flight in any object with violate logic */
public class UnsupportedFlightException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnsupportedFlightException(String unsupported_flight) {
    	super(unsupported_flight);
    }
}
