package ru.integrotech.su.inputparams.charge;

import static ru.integrotech.su.common.LocationType.airport;
import static ru.integrotech.su.common.LocationType.city;
import static ru.integrotech.su.common.LocationType.country;
import static ru.integrotech.su.common.LocationType.region;
import static ru.integrotech.su.common.LocationType.valueOf;
import ru.integrotech.su.common.Airline;
import ru.integrotech.su.common.Location;

/**
 * container for chargeRequest (request body for Charge project)
 *
 * data ( private Airline airline; private Location origin; private Location
 * destination; private TierLevel tierLevel; private boolean isRoundTrip; )
 */
public class ChargeInput {

	/**
	 * 
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param originType
	 * @param originCode
	 * @param destType
	 * @param destCode
	 * @param airlineCode
	 * @param tierLevelCode
	 * @param isRoundTrip
	 * @return
	 */
	public static ChargeInput of(String originType, String originCode,
			String destType, String destCode, String airlineCode,
			String tierLevelCode, boolean isRoundTrip) {

		ChargeInput chargeInput = new ChargeInput();
		chargeInput.setAirline(Airline.of(airlineCode));
		chargeInput.setOrigin(createLocation(originType, originCode));
		chargeInput.setDestination(createLocation(destType, destCode));
		chargeInput.setTierLevel(TierLevel.of(tierLevelCode));
		chargeInput.setIsRoundTrip(isRoundTrip);

		return chargeInput;
	}

	private static Location createLocation(String destType, String destCode) {

		Location result = null;

		if (destType == null) {
			return result;
		} else {
			destType = destType.toLowerCase();
		}

		if (valueOf(destType) == airport) {
			result = Location.ofAirport(destCode);
		} else if (valueOf(destType) == city) {
			result = Location.ofCity(destCode);
		} else if (valueOf(destType) == country) {
			result = Location.ofCountry(destCode);
		} else if (valueOf(destType) == region) {
			result = Location.ofRegion(destCode);
		}

		return result;
	}

	private Airline airline;

	private Location origin;

	private Location destination;

	private TierLevel tierLevel;

	private boolean isRoundTrip;

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

	public boolean getIsRoundTrip() {
		return isRoundTrip;
	}

	public void setIsRoundTrip(boolean roundTrip) {
		isRoundTrip = roundTrip;
	}

}
