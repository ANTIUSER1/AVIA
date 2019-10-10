package ru.aeroflot.fmc.model.location;

import java.util.*;

/* class represents city
* the weight field used for comparison in reverse order*/
public class City implements Comparable<City>{

    public static City of(String code, String name, Country country,  int weight, double longitude, double latitude) {
        return new City(code, name, country, GeoLocation.of(longitude, latitude), weight);
    }

    private final String code;

    private final String name;

    private final Country country;

    private final GeoLocation geoLocation;

    private final int weight;

    private final Map<String, Airport> airportMap;

    private City(String code, String name, Country country, GeoLocation geoLocation, int weight) {
        this.code = code;
        this.name = name;
        this.country = country;
        this.geoLocation = geoLocation;
        if (weight == 0) weight = Integer.MAX_VALUE;
        this.weight = weight;
        this.airportMap = new HashMap<>();
    }

    public City(String code, String name, Country country, int weight) {
        this(code, name, country, null, weight);
    }

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
