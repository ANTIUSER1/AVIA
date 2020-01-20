package ru.integrotech.su.common;

import java.util.Objects;

public class Airport implements Comparable<Airport> {

    public static Airport of(String airportCode) {
        Airport result = new Airport();
        result.setAirportCode(airportCode);
        return result;
    }

    private String airportCode;

    private Airport(String airportCode) {
        this.airportCode = airportCode;
    }

    private Airport() {
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {

        if (airportCode != null) {
            airportCode = airportCode.toUpperCase();
        }

        this.airportCode = airportCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Objects.equals(airportCode, airport.airportCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airportCode);
    }

    @Override
    public int compareTo(Airport o) {
        return this.airportCode.compareTo(o.airportCode);
    }
}

