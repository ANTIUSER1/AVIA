package ru.integrotech.airline.register;


import ru.integrotech.airline.core.location.WorldRegion;

class RegionRecord {

    private String code;

    private String name;

    private double longitude;

    private double latitude;

    WorldRegion toWorldRegion() {
        return WorldRegion.of(this.code, this.name, this.longitude, this.latitude);
    }

    String getCode() {
        return code;
    }

    String getName() {
        return name;
    }

    double getLongitude() {
        return longitude;
    }

    double getLatitude() {
        return latitude;
    }

    boolean isEmptyCode() {
        return this.code == null || this.code.isEmpty();
    }

    @Override
    public String toString() {
        return "RegionRecord{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
