package ru.integrotech.airline.core.location;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.FlightCarrier;

import java.util.*;

/**
 * Represents Airport
 *
 * Сan be used in all projects
 *
 */

public class Airport implements Comparable<Airport> {

    public static Airport of(String code,
                             String name,
                             City city,
                             String aflZone,
                             String scyteamZone,
                             boolean isUcAvailable,
                             double longitude,
                             double latitude) {

        ZONE aflZoneInit = null;
        if (aflZone != null && !aflZone.isEmpty())  aflZoneInit =  ZONE.valueOf(aflZone);
        ZONE scyteamZoneInit = null;
        if (scyteamZone != null && !scyteamZone.isEmpty())  scyteamZoneInit =  ZONE.valueOf(scyteamZone);

        Airport result = new Airport();
        result.setCode(code);
        result.setName(name);
        result.setCity(city);
        result.setGeoLocation(GeoLocation.of(longitude, latitude));
        result.setAflZone(aflZoneInit);
        result.setScyteamZone(scyteamZoneInit);
        result.setUcAvailable(isUcAvailable);
        result.setOutcomeFlights(new HashMap<>());
        return result;
    }

    private String code;

    private String name;

    private City city;

    private GeoLocation geoLocation;

    private ZONE aflZone;

    private ZONE scyteamZone;

    private boolean isUcAvailable;

    private Map<Airport, Flight> outcomeFlights;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public City getCity() {
        return city;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public ZONE getAflZone() {
        return aflZone;
    }

    public ZONE getScyteamZone() {
        return scyteamZone;
    }

    public boolean isUcAvailable() {
        return isUcAvailable;
    }

    public Map<Airport, Flight> getOutcomeFlights() {
        return outcomeFlights;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public void setAflZone(ZONE aflZone) {
        this.aflZone = aflZone;
    }

    public void setScyteamZone(ZONE scyteamZone) {
        this.scyteamZone = scyteamZone;
    }

    public void setUcAvailable(boolean ucAvailable) {
        isUcAvailable = ucAvailable;
    }

    public void setOutcomeFlights(Map<Airport, Flight> outcomeFlights) {
        this.outcomeFlights = outcomeFlights;
    }

    public void addOutcomeFlight(Airport destination, Airline airline, int distance) {
        if (!this.outcomeFlights.containsKey(destination)) {
            this.outcomeFlights.put(destination, Flight.of(this, destination, distance, airline));
        }
        this.outcomeFlights.get(destination).getCarriers().put(airline, FlightCarrier.of(airline));
    }

    public Flight getOutcomeFlight(Airport destination, Airline airline) {
        Flight result = null;
        if (this.outcomeFlights != null && destination != null) {
            Flight flight = this.outcomeFlights.get(destination);
            if (flight != null && flight.getCarriers().containsKey(airline)) {
                result = flight;
            }
        }
        return result;
    }

    public Set<Flight> getOutcomeFlights(Airline airline) {
        Set<Flight> result = new HashSet<>();
        if (airline != null) {
            for (Flight flight : this.outcomeFlights.values()) {
                if (flight.getCarriers().containsKey(airline)) {
                    result.add(flight);
                }
            }
        } else {
            result.addAll(this.outcomeFlights.values());
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Objects.equals(code, airport.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public int compareTo(Airport o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        String aflZone = this.aflZone != null ? String.format("afl %s", this.aflZone) : "";
        String scyteamZone = this.scyteamZone != null ? String.format("skyteam %s", this.scyteamZone) : "";
        String isUcAvailable = this.isUcAvailable ? ", upgrade avail." : "";
        return String.format("Airport: %s  %-45.45s  %-15.15s  %7s  %10s %15s %s",
                this.code,
                this.name,
                this.city.getName(),
                aflZone,
                scyteamZone,
                isUcAvailable,
                this.geoLocation);
    }

    /*airport must to be belong to certain zone*/
    public enum ZONE {A1, A2, AF, AO, E1, E2, E3, E4, LA, MA, ME, NA, R1, R2, R3, R4, SA}

}
