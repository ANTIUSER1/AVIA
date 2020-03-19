package ru.integrotech.su.common;

import java.util.Objects;

/**
 * container for input-output <b>su.common.</b>City
 *
 * data (private String cityCode; private int weight;)
 */
public class City {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param airport
	 *            of <b>ru.integrotech.airline.core.location.Airport</b>
	 * @return
	 */
	public static City of(ru.integrotech.airline.core.location.Airport airport) {
		City res = new City();
		res.setCityCode(airport.getCity().getCode());
		res.setWeight(airport.getCity().getWeight());
		return res;
	}

	/**
	 * Static constructor Firstly creates new City, refering to private
	 * constructor, then adds City's properties, generated from parameters
	 *
	 * @param cityCode
	 *            of String
	 * @return
	 */

	public static City of(String cityCode) {
		City city = new City();
		city.setCityCode(cityCode);
		return city;
	}

	private String cityCode;

	private int weight;

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {

		if (cityCode != null) {
			cityCode = cityCode.toUpperCase();
		}

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
}
