package ru.aeroflot.fmc.io.common;

import java.util.Objects;

public class Airport implements Comparable<Airport> {

	public static Airport of(ru.aeroflot.fmc.model.location.Airport airport) {
		return new Airport(airport.getCode());
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
		this.airportCode = airportCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Airport [airportCode=");
		builder.append(airportCode);
		builder.append("]");
		return builder.toString();
	}
}
