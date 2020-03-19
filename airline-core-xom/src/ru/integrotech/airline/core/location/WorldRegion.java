package ru.integrotech.airline.core.location;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * class represents the world region (Europe, Asia, America etc.
 *
 * Сan be used in all projects
 *
 */

public class WorldRegion implements Comparable<WorldRegion> {

    public static WorldRegion of(String code,
                                 String name,
                                 double longitude,
                                 double latitude) {

        WorldRegion result = new WorldRegion();
        result.setCode(code);
        result.setName(name);
        result.setGeoLocation(GeoLocation.of(longitude, latitude));
        result.setAirportMap(new HashMap<>());
        return result;
    }

    private String code;

    private String name;

    private GeoLocation geoLocation;

    private Map<String, Airport> airportMap;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
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

    public void setAirportMap(Map<String, Airport> airportMap) {
        this.airportMap = airportMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldRegion that = (WorldRegion) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public int compareTo(WorldRegion o) {
        return this.code.compareTo(o.code);
    }

    @Override
    public String toString() {
        return String.format("World region: %-3s   %-25.25s  %s",
                this.code,
                this.name,
                this.geoLocation);
    }
}
