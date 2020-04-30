package ru.integrotech.su.common;

import java.util.Objects;

/**
 * container for input-output <b>su.common.</b>Location
 * 
 *
 * data ( private Airport airport; private City city; private Country country;
 * private Region region;) <br />
 * compares by AirportCode values
 */

public class Location implements Comparable<Location> {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param airport
	 *            of <b> ru.integrotech.airline.core.location.Airport</b>
	 * @return
	 */
	public static Location of(
			ru.integrotech.airline.core.location.Airport airport) {
		Location res = new Location();

		res.setAirport(Airport.of(airport.getCode()));
		res.setCity(City.of(airport));
		res.setCountry(Country.of(airport.getCity().getCountry().getCode()));
		res.setRegion(Region.of(airport.getCity().getCountry().getWorldRegion()
				.getCode()));
		return res;
	}

	/**
	 * constructor, then sets location's properties, generated from parameters <br />
	 * (Using airportCode? firstly creates <b>
	 * ru.integrotech.airline.core.location.Airport</b> instance)
	 * 
	 * @param airportCode
	 *            of String
	 * @return
	 */
	public static Location ofAirport(String airportCode) {
		Location res = new Location();
		res.setAirport(Airport.of(airportCode));
		return res;
	}

	public static Location ofCity(String cityCode) {
		Location res = new Location();
		res.setCity(City.of(cityCode));
		return res;
	}

	public static Location ofCountry(String countryCode) {
		Location res = new Location();
		res.setCountry(Country.of(countryCode));
		return res;
	}

	public static Location ofRegion(String regionCode) {
		Location res = new Location();
		res.setRegion(Region.of(regionCode));
		return res;
	}

	private Airport airport;

	private City city;

	private Country country;

	private Region region;

	public Airport getAirport() {
		return airport;
	}

	public void setAirport(Airport airport) {
		this.airport = airport;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	int getWeight() {
		return this.city.getWeight();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Location location = (Location) o;
		return Objects.equals(airport, location.airport)
				&& Objects.equals(city, location.city)
				&& Objects.equals(country, location.country)
				&& Objects.equals(region, location.region);
	}

	@Override
	public int hashCode() {
		return Objects.hash(airport, city, country, region);
	}

	@Override
	public int compareTo(Location o) {
		int result;
		if (o == null) {
			result = 1;
		} else if (this.airport.getAirportCode() == null
				&& o.airport.getAirportCode() == null)
			result = 0;
		else if (this.airport.getAirportCode() == null)
			result = -1;
		else
			result = this.airport.getAirportCode().compareTo(
					o.airport.getAirportCode());
		return result;
	}

}
