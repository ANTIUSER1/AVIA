package ru.aeroflot.fmc.io.common;

import java.util.Objects;

public class City {

	public static City of(ru.aeroflot.fmc.model.location.Airport airport) {
		return new City(airport.getCity().getCode(), airport.getCity()
				.getWeight());
	}

	private String cityCode;

	private int weight;

	private City(String cityCode, int weight) {
		this.cityCode = cityCode;
		this.weight = weight;
	}

	private City() {
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		City city = (City) o;
		return Objects.equals(cityCode, city.cityCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cityCode);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("City [cityCode=");
		builder.append(cityCode);
		builder.append(", weight=");
		builder.append(weight);
		builder.append("]");
		return builder.toString();
	}
}
