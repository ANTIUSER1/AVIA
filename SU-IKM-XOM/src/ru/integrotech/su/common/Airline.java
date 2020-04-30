package ru.integrotech.su.common;

import java.util.Objects;

/**
 * container for common input-output <b>su.common.</b>Airline <br />
 * data : (private String airlineCode;) <br />
 * compares by airlineCode
 * 
 */

public class Airline implements Comparable<Airline> {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 *
	 * @param airlineCode
	 * @return
	 */

	public static Airline of(String airlineCode) {
		Airline res = new Airline();
		res.setAirlineCode(airlineCode);
		return res;
	}

	private String airlineCode;

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
}
