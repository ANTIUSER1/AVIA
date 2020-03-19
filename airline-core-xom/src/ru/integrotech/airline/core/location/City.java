package ru.integrotech.airline.core.location;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * Represents the City
 *
 *  Сan be used in all projects
 *
 */

public class City implements Comparable<City>{

    public static City of(String code,
                          String name,
                          Country country,
                          int weight,
                          double longitude,
                          double latitude) {
        City result = new City();
        result.setCode(code);
        result.setName(name);
        result.setCountry(country);
        result.setGeoLocation(GeoLocation.of(longitude, latitude));
        result.setWeight(weight);
        result.setAirportMap(new HashMap<>());
        return result;
    }

    private String code;

    private String name;

    private Country country;

    private GeoLocation geoLocation;

    private int weight;

    private Map<String, Airport> airportMap;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public int getWeight() {
        return weight;
    }

    public Map<String, Airport> getAirportMap() {
        return airportMap;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setAirportMap(Map<String, Airport> airportMap) {
        this.airportMap = airportMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(code, city.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public int compareTo(City o) {
        int weightDiff = o.weight - this.weight;
        if (weightDiff > 0) return weightDiff;
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return String.format("City: %s  %-20.20s  %-20.20s  %s",
                this.code,
                this.name,
                this.country.getName(),
                this.geoLocation);
    }
}
