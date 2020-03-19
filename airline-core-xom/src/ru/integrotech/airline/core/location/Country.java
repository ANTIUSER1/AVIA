package ru.integrotech.airline.core.location;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * class represents country
 *
 *  Сan be used in all projects
 *
 */

public class Country implements Comparable<Country> {

    public static Country of(String code,
                             String name,
                             WorldRegion worldRegion,
                             String capitalCode,
                             double longitude,
                             double latitude) {

        Country result = new Country();
        result.setCode(code);
        result.setName(name);
        result.setGeoLocation(GeoLocation.of(longitude, latitude));
        result.setWorldRegion(worldRegion);
        result.setCapitalCode(capitalCode);
        result.setAirportMap(new HashMap<>());
        return result;
    }

    public static Country of(String code, String name, WorldRegion worldRegion, String capitalCode) {
        Country result = new Country();
        result.setCode(code);
        result.setName(name);
        result.setWorldRegion(worldRegion);
        result.setCapitalCode(capitalCode);
        result.setAirportMap(new HashMap<>());
        return result;
    }

    private String code;

    private String name;

    private GeoLocation geoLocation;

    private WorldRegion worldRegion;

    private City capital;

    private String capitalCode;

    private Map<String, Airport> airportMap;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public WorldRegion getWorldRegion() {
        return worldRegion;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public City getCapital() {
        return capital;
    }

    public void setCapital(City capital) {
        this.capital = capital;
    }

    public String getCapitalCode() {
        return capitalCode;
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

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public void setWorldRegion(WorldRegion worldRegion) {
        this.worldRegion = worldRegion;
    }

    public void setCapitalCode(String capitalCode) {
        this.capitalCode = capitalCode;
    }

    public void setAirportMap(Map<String, Airport> airportMap) {
        this.airportMap = airportMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(code, country.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public int compareTo(Country o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        String wRegion = this.worldRegion != null ? this.worldRegion.getName() : "";
        return String.format("Country: %s  %-20.20s  %-18.18s  %-22.22s  %s",
                this.code,
                this.name,
                this.capital != null ? this.capital.getName() : "",
                wRegion,
                this.geoLocation);
    }

}
