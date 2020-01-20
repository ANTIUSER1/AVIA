package ru.integrotech.su.inputparams.spend;

public class LocationInput {

    public static LocationInput of(String locationType, String locationCode) {
        LocationInput result = new LocationInput();
        result.setLocationType(locationType);
        result.setLocationCode(locationCode);
        return result;
    }

    private String locationType;

    private String locationCode;

    private LocationInput() {
    }

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

