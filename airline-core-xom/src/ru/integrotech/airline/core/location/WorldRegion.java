package ru.integrotech.airline.core.location;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* class represents the world region (Europe, Asia, America etc. */
public class WorldRegion implements Comparable<WorldRegion> {

    public static WorldRegion of(String code, String name, double longitude, double latitude) {
        return new WorldRegion(code, name, GeoLocation.of(longitude, latitude));
    }

    private final String code;

    private final String name;

    private final GeoLocation geoLocation;

    private final Map<String, Airport> airportMap;


    private WorldRegion(String code, String name, GeoLocation geoLocation) {
        this.code = code;
        this.name = name;
        this.geoLocation = geoLocation;
        airportMap = new HashMap<>();
    }

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
