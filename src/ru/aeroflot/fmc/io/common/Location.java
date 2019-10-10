package ru.aeroflot.fmc.io.common;

import java.util.Objects;

/*class use only for build io results*/
public class Location implements Comparable<Location> {
	public static Location of(ru.aeroflot.fmc.model.location.Airport airport) {
		return new Location(Airport.of(airport), City.of(airport),
				Country.of(airport), Region.of(airport));
	}

	private Airport airport;

	private City city;

	private Country country;

	private Region region;

	private Location(Airport airport, City city, Country country, Region region) {
		this.airport = airport;
		this.city = city;
		this.country = country;
		this.region = region;
	}

	private Location() {
	}

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Location [airport=");
		builder.append(airport + System.lineSeparator());
		builder.append(", city=");
		builder.append(city + System.lineSeparator());
		builder.append(", country=");
		builder.append(country + System.lineSeparator());
		builder.append(", region=");
		builder.append(region);
		builder.append("]");
		return builder.toString();
	}

}
