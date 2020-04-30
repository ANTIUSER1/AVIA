package ru.integrotech.su.inputparams.route;

import ru.integrotech.su.common.Location;
import ru.integrotech.su.common.LocationType;
import ru.integrotech.su.inputparams.spend.SpendInput;

/**
 * container for input routes data
 *
 * data ( private String originType; private String originCode; private String
 * destinationType; private String destinationCode; private String airlineCode;)
 */

public class RoutesInput {

	
	/**
	 * Static constructor
	 *
	 * At first creates the instance, refering the private free-arguments
	 * constructor, then sets up the instance's field
	 *
	 * @param spendInput
	 * @param airlineCode
	 * @return
	 */
	public static RoutesInput of(SpendInput spendInput, String airlineCode) {
		RoutesInput routesInput = new RoutesInput();

		if (airlineCode != null) {
			airlineCode = airlineCode.toUpperCase();
		}
		routesInput.setOriginType(spendInput.getOrigin().getLocationType());
		routesInput.setOriginCode(spendInput.getOrigin().getLocationCode());
		routesInput.setDestinationType(spendInput.getDestination()
				.getLocationType());
		routesInput.setDestinationCode(spendInput.getDestination()
				.getLocationCode());
		routesInput.setAirlineCode(airlineCode);
		return routesInput;
	}

	/**
	 * Static constructor
	 *
	 * At first creates the instance, refering the private free-arguments
	 * constructor, then sets up the instance's field
	 *
	 *
	 * @param originType
	 * @param originCode
	 * @param destinationType
	 * @param destinationCode
	 * @param airlineCode
	 * @return
	 */
	public static RoutesInput of(String originType, String originCode,
			String destinationType, String destinationCode, String airlineCode) {

		RoutesInput routesInput = new RoutesInput();

		if (originType != null) {
			originType = originType.toLowerCase();
		}

		if (originCode != null) {
			originCode = originCode.toUpperCase();
		}

		if (destinationType != null) {
			destinationType = destinationType.toLowerCase();
		}

		if (destinationCode != null) {
			destinationCode = destinationCode.toUpperCase();
		}

		if (airlineCode != null) {
			airlineCode = airlineCode.toUpperCase();
		}

		routesInput.setOriginType(originType);
		routesInput.setOriginCode(originCode);
		routesInput.setDestinationType(destinationType);
		routesInput.setDestinationCode(destinationCode);
		routesInput.setAirlineCode(airlineCode);
		return routesInput;
	}

	
	private static String getCode(Location location) {
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

	private static String getType(Location location) {

		String result = null;

		if (location != null) {
			if (location.getAirport() != null) {
				result = LocationType.airport.name();
			} else if (location.getCity() != null) {
				result = LocationType.city.name();
			} else if (location.getCountry() != null) {
				result = LocationType.country.name();
			} else if (location.getRegion() != null) {
				result = LocationType.region.name();
			}
		}

		return result;
	}

	private String originType;

	private String originCode;

	private String destinationType;

	private String destinationCode;

	private String airlineCode;

	public String getOriginCode() {
		return originCode;
	}

	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	public String getOriginType() {
		return originType;
	}

	public void setOriginType(String originType) {
		this.originType = originType;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	public String getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(String toType) {
		this.destinationType = toType;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
}
