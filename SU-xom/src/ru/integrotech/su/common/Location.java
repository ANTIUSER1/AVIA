package ru.integrotech.su.common;

import java.util.Objects;

/*class use only for buildResult io results*/
public class Location implements Comparable<Location> {

    public static Location of(ru.integrotech.airline.core.location.Airport airport) {
        return new Location(Airport.of(airport.getCode()),
                            City.of(airport),
                            Country.of(airport.getCity().getCountry().getCode()),
                            Region.of(airport.getCity().getCountry().getWorldRegion().getCode()));
    }

    public static Location ofAirport(String airportCode) {
        Location location = new Location();
        location.setAirport(Airport.of(airportCode));
        return location;
    }

    public static Location ofCity(String cityCode) {
        Location location = new Location();
        location.setCity(City.of(cityCode));
        return location;
    }

    public static Location ofCountry(String countryCode) {
        Location location = new Location();
        location.setCountry(Country.of(countryCode));
        return location;
    }

    public static Location ofRegion(String regionCode) {
        Location location = new Location();
        location.setRegion(Region.of(regionCode));
        return location;
    }

    private Airport airport;

    private City city;

    private Country country;

    private Region region;

    private Location(Airport airport, City city, Country country, Region region) {
        this.airport = airport;
        this.city = city;
        this.country = country;
        this.region = region;
    }

    private Location() {
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    int getWeight() {
        return this.city.getWeight();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(airport, location.airport) &&
                Objects.equals(city, location.city) &&
                Objects.equals(country, location.country) &&
                Objects.equals(region, location.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airport, city, country, region);
    }

    @Override
    public int compareTo(Location o) {
        int result;
        if (o == null ) {
            result = 1;
        } else if (this.airport.getAirportCode() == null && o.airport.getAirportCode() == null) result = 0;
        else if (this.airport.getAirportCode() == null) result = -1;
        else result = this.airport.getAirportCode().compareTo(o.airport.getAirportCode());
        return result;
    }






}
