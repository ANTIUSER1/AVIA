package ru.integrotech.su.common;

import java.util.Objects;

/**
 * container for input-output <b>su.common.</b>Region
 *
 * data (private String regionCode;)
 */
public class Region {
	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param regionCode
	 * @return
	 */
	public static Region of(String regionCode) {
		Region res = new Region();
		res.setRegionCode(regionCode);
		return res;
	}

	private String regionCode;

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {

		if (regionCode != null) {
			regionCode = regionCode.toUpperCase();
		}

		this.regionCode = regionCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Region region = (Region) o;
		return Objects.equals(regionCode, region.regionCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(regionCode);
	}
}
