package ru.integrotech.su.inputparams.spend;

/**
 * container for input location data ( locationType, locationCode )
 *
 * data (private String locationType; private String locationCode;)
 */
public class LocationInput {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param locationType
	 * @param locationCode
	 * @return
	 */
	public static LocationInput of(String locationType, String locationCode) {
		LocationInput result = new LocationInput();
		result.setLocationType(locationType);
		result.setLocationCode(locationCode);
		return result;
	}

	private String locationType;

	private String locationCode;

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {

		if (locationType != null) {
			locationType = locationType.toLowerCase();
		}

		this.locationType = locationType;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {

		if (locationCode != null) {
			locationCode = locationCode.toUpperCase();
		}

		this.locationCode = locationCode;
	}
}
