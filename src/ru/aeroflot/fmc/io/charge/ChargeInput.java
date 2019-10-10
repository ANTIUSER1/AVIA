package ru.aeroflot.fmc.io.charge;

import ru.aeroflot.fmc.io.common.Airline;
import ru.aeroflot.fmc.io.common.Location;

/*class describes input params for charge*/
public class ChargeInput {

	private Airline airline;

	private Location origin;

	private Location destination;

	private TierLevel tierLevel;

	private boolean isRoundTrip;

	public static ChargeInput of() {
		return new ChargeInput();
	}

	private ChargeInput() {
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public Location getOrigin() {
		return origin;
	}

	public void setOrigin(Location origin) {
		this.origin = origin;
	}

	public Location getDestination() {
		return destination;
	}

	public void setDestination(Location destination) {
		this.destination = destination;
	}

	public TierLevel getTierLevel() {
		return tierLevel;
	}

	public void setTierLevel(TierLevel tierLevel) {
		this.tierLevel = tierLevel;
	}

	public boolean isRoundTrip() {
		return isRoundTrip;
	}

	public void setRoundTrip(boolean roundTrip) {
		isRoundTrip = roundTrip;
	}

	String getAirlineCode() {
		String result = null;
		if (this.airline != null) {
			result = this.airline.getAirlineCode();
		}
		return result;
	}

	String getOriginCode() {
		return getCode(this.origin);
	}

	String getDestinationCode() {
		return getCode(this.destination);
	}

	String getOriginType() {
		return getType(this.origin);
	}

	String getDestinationType() {
		return getType(this.destination);
	}

	String getTierLevelCode() {
		String result = null;
		if (this.tierLevel != null) {
			result = this.tierLevel.getTierLevelCode();
		}
		return result;
	}

	private String getCode(Location location) {
		String result = null;
		if (location != null) {
			if (location.getAirport() != null) {
				result = location.getAirport().getAirportCode();
			} else if (location.getCity() != null) {
				result = location.getCity().getCityCode();
			} else if (location.getCountry() != null) {
				result = location.getCountry().getCountryCode();
			} else if (location.getRegion() != null) {
				result = location.getRegion().getRegionCode();
			}
		}
		return result;
	}

	private String getType(Location location) {
		String result = null;
		if (location != null) {
			if (location.getAirport() != null) {
				result = "airport";
			} else if (location.getCity() != null) {
				result = "city";
			} else if (location.getCountry() != null) {
				result = "country";
			} else if (location.getRegion() != null) {
				result = "region";
			}
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChargeInput [airline=");
		builder.append(airline);
		builder.append(",  origin=");
		builder.append(origin + System.lineSeparator());
		builder.append(",    destination=");
		builder.append(destination);
		builder.append(",  tierLevel=");
		builder.append(tierLevel);
		builder.append(",    isRoundTrip=");
		builder.append(isRoundTrip + System.lineSeparator());
		builder.append("]");
		return builder.toString();
	}

}
