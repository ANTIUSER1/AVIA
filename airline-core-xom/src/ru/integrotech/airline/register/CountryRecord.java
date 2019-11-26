package ru.integrotech.airline.register;


import ru.integrotech.airline.core.location.Country;
import ru.integrotech.airline.core.location.WorldRegion;

class CountryRecord {

    private String code;

    private String name;

    private String longitude;

    private String latitude;

    private String capital;

    private String region_code;

    Country toCountry(WorldRegion worldRegion) {
        if ((this.longitude.isEmpty()) && (this.latitude.isEmpty())) {
            return Country.of(this.code, this.name, worldRegion, capital);
        } else {
            double longitude = Double.valueOf(this.longitude);
            double latitude = Double.valueOf(this.latitude);
            return Country.of(this.code, this.name, worldRegion, capital, longitude, latitude);
        }
    }

    String getCode() {
        return code;
    }

    String getName() {
        return name;
    }

    String getLongitude() {
        return longitude;
    }

    String getLatitude() {
        return latitude;
    }

    String getCapital() {
        return capital;
    }

    String getRegionCode() {
        return region_code;
    }

    boolean isEmptyCode() {
        return this.code == null || this.code.isEmpty();
    }

    boolean isEmptyRegionCode() {
        return this.region_code == null || this.region_code.isEmpty();
    }


    @Override
    public String toString() {
        return "CountryRecord{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", capital='" + capital + '\'' +
                ", region_code='" + region_code + '\'' +
                '}';
    }
}
