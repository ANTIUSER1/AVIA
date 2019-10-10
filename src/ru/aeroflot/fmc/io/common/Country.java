package ru.aeroflot.fmc.io.common;

import java.util.Objects;

public class Country {

	public static Country of(ru.aeroflot.fmc.model.location.Airport airport) {
		return new Country(airport.getCity().getCountry().getCode());
	}

	private String countryCode;

	private Country(String countryCode) {
		this.countryCode = countryCode;
	}

	private Country() {
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Country country = (Country) o;
		return Objects.equals(countryCode, country.countryCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(countryCode);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Country [countryCode=");
		builder.append(countryCode);
		builder.append("]");
		return builder.toString();
	}
}
