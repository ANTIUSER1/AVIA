package ru.integrotech.su.outputparams.spend;

import java.util.Objects;

import ru.integrotech.airline.core.location.Airport;

/**
 * container for MilesCost data
 *
 * data( private AirportWithZone airportFrom; private AirportWithZone airportTo;
 * private int distance; private List<RequiredAward> requiredAward;)
 */
class AirportWithZone {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param airport
	 * @param isAfl
	 * @return
	 */
	public static AirportWithZone of(Airport airport, boolean isAfl) {
		AirportWithZone airportWithZone = new AirportWithZone();
		airportWithZone.setAirportCode(airport.getCode());
		if (isAfl) {
			airportWithZone.setZoneCode(airport.getAflZone().name());
		} else {
			airportWithZone.setZoneCode(airport.getScyteamZone().name());
		}
		return airportWithZone;
	}

	private String airportCode;

	private String zoneCode;

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AirportWithZone that = (AirportWithZone) o;
		return Objects.equals(airportCode, that.airportCode)
				&& Objects.equals(zoneCode, that.zoneCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(airportCode, zoneCode);
	}
}
