package ru.integrotech.airline.register;

import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.core.location.City;

/**
 * class for read data in JSON format from remote register
 *
 *  Can be used in all projects
 *
 */

class AirportRecord {

    private String code;

    private String name;

    private String city_code;

    private String zone_afl;

    private String zone_st;

    private double longitude;

    private double latitude;

    private int is_uc_available;

    Airport toAirport(City city) {

        boolean isUcAvailable = this.is_uc_available == 1;

        return Airport.of(this.code,
                this.name,
                city,
                this.zone_afl,
                this.zone_st,
                isUcAvailable,
                this.longitude,
                this.latitude);
    }

    String getCode() {
        return code;
    }

    String getName() {
        return name;
    }

    String getCityCode() {
        return city_code;
    }

    String getZoneAfl() {
        return zone_afl;
    }

    String getZoneSt() {
        return zone_st;
    }

    double getLongitude() {
        return longitude;
    }

    double getLatitude() {
        return latitude;
    }

    int getIsUcAvailable() {
        return is_uc_available;
    }

    boolean isEmptyCode() {
        return this.code == null || this.code.isEmpty();
    }

    @Override
    public String toString() {
        return "AirportRecord{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", city_code='" + city_code + '\'' +
                ", zone_afl='" + zone_afl + '\'' +
                ", zone_st='" + zone_st + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", is_uc_available=" + is_uc_available +
                '}';
    }
}
