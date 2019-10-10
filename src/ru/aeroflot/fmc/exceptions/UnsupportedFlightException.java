package ru.aeroflot.fmc.exceptions;

/* throws in attempt to add some Flight in any object with violate logic */
public class UnsupportedFlightException extends Exception {
    public UnsupportedFlightException(String unsupported_flight) {

    }
}
