package ru.aeroflot.fmc.io.common;

import java.util.Objects;

public class Region {

	public static Region of(ru.aeroflot.fmc.model.location.Airport airport) {
		return new Region(airport.getCity().getCountry().getWorldRegion()
				.getCode());
	}

	private String regionCode;

	private Region(String regionCode) {
		this.regionCode = regionCode;
	}

	private Region() {
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Region [regionCode=");
		builder.append(regionCode);
		builder.append("]");
		return builder.toString();
	}
}
