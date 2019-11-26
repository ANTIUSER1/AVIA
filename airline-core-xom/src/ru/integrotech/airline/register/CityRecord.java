package ru.integrotech.airline.register;


import ru.integrotech.airline.core.location.City;
import ru.integrotech.airline.core.location.Country;

class CityRecord {

    private String code;

    private String name;

    private double longitude;

    private double latitude;

    private String country_code;

    private int weight;

    City toCity (Country country) {
        return City.of(this.code, this.name, country, this.weight, this.longitude, this.latitude);
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

    String getCountryCode() {
        return country_code;
    }

    int getWeight() {
        return weight;
    }

    boolean isEmptyCode() {
        return this.code == null || this.code.isEmpty();
    }

    @Override
    public String toString() {
        return "CityRecord{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", country_code='" + country_code + '\'' +
                ", weight=" + weight +
                '}';
    }
}
