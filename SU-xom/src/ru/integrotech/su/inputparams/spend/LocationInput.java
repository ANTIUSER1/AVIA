package ru.integrotech.su.inputparams.spend;

public class LocationInput {

    public static LocationInput of(String locationType, String locationCode) {
        LocationInput result = new LocationInput();
        result.locationType = locationType;
        result.locationCode = locationCode;
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
        this.locationType = locationType;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
}

