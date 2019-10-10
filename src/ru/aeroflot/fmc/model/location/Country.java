package ru.aeroflot.fmc.model.location;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* class represents country*/
public class Country implements Comparable<Country> {

    public static Country of(String code, String name, WorldRegion worldRegion, String capitalCode, double longitude, double latitude) {
        return new Country(code, name, GeoLocation.of(longitude, latitude), worldRegion, capitalCode);
    }

    public static Country of(String code, String name, WorldRegion worldRegion, String capitalCode) {
        return new Country(code, name, null, worldRegion, capitalCode);
    }

    private final String code;

    private final String name;

    private final GeoLocation geoLocation;

    private final WorldRegion worldRegion;

    private City capital;

    private final String capitalCode;

    private final Map<String, Airport> airportMap;

    private Country(String code, String name, GeoLocation geoLocation, WorldRegion worldRegion, String capitalCode) {
        this.code = code;
        this.name = name;
        this.geoLocation = geoLocation;
        this.worldRegion = worldRegion;
        this.capitalCode = capitalCode;
        airportMap = new HashMap<>();
    }


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
