package ru.integrotech.su.common;

import java.util.Objects;

public class Airline implements Comparable<Airline>{

    public static Airline of(String airlineCode) {
       Airline result = new Airline();
       result.setAirlineCode(airlineCode);
       return result;
    }

    private String airlineCode;

    private Airline(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    private Airline() {
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {

        if (airlineCode != null) {
            airlineCode = airlineCode.toUpperCase();
        }

        this.airlineCode = airlineCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airline airline = (Airline) o;
        return Objects.equals(airlineCode, airline.airlineCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airlineCode);
    }

    @Override
    public int compareTo(Airline o) {
        return this.airlineCode.compareTo(o.airlineCode);
    }
}

