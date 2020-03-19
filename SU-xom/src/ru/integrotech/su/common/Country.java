package ru.integrotech.su.common;

import java.util.Objects;

/**
 * container for input-output <b>su.common.</b>Country
 *
 * data (private String countryCode;)
 */
public class Country {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param countryCode
	 * @return
	 */
	public static Country of(String countryCode) {
		Country res = new Country();
		res.setCountryCode(countryCode);
		return res;
	}

	private String countryCode;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {

		if (countryCode != null) {
			countryCode = countryCode.toUpperCase();
		}

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
}
