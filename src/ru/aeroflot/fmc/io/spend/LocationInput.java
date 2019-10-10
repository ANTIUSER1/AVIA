package ru.aeroflot.fmc.io.spend;

class LocationInput {

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

