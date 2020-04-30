package ru.integrotech.su.common;

import java.util.Objects;

/**
 * container for input-output <b>su.common.</b>Airport <br />
 * data (private String airportCode;) <br />
 * compares by airportCode
 */

public class Airport implements Comparable<Airport> {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param airportCode
	 * @return
	 */

	public static Airport of(String airportCode) {
		Airport res = new Airport();
		res.setAirportCode(airportCode);
		return res;
	}

	private String airportCode;

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
}
