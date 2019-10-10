package ru.aeroflot.fmc.io.common;

import java.util.Objects;

public class Airline implements Comparable<Airline> {

	public static Airline of(ru.aeroflot.fmc.model.airline.Airline airline) {
		return new Airline(airline.getCode());
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
		this.airlineCode = airlineCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Airline [airlineCode=");
		builder.append(airlineCode);
		builder.append("]");
		return builder.toString();
	}
}
