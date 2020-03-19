package ru.integrotech.airline.core.flight;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.location.Airport;

import java.util.*;

/**
 * Describes flight between two points - origin
 * and destination according exact ticket
 *
 * Can be used in all projects
 *
 */

public class Flight {

    public static Flight of(Airport origin, Airport destination, int distance, Airline airline) {

        Map<Airline, FlightCarrier> carriersMap = new HashMap<>();
        carriersMap.put(airline, FlightCarrier.of(airline));

        Flight result = new Flight();
        result.setCode(Flight.createCode(origin, destination));
        result.setOrigin(origin);
        result.setDestination(destination);
        result.setDistance(distance);
        result.setCarriers(carriersMap);
        result.setAflBonuses(new HashSet<>());
        result.setScyteamBonuses(new HashSet<>());
        return result;
    }

    public static Flight of(Airport origin, Airport destination, int distance, List<FlightCarrier> carriers) {
        Map<Airline, FlightCarrier> carriersMap = new HashMap<>();
        for (FlightCarrier carrier : carriers) {
            carriersMap.put(carrier.getCarrier(), carrier);
        }

        Flight result = new Flight();
        result.setCode(Flight.createCode(origin, destination));
        result.setOrigin(origin);
        result.setDestination(destination);
        result.setDistance(distance);
        result.setCarriers(carriersMap);
        result.setAflBonuses(new HashSet<>());
        result.setScyteamBonuses(new HashSet<>());
        return result;
    }

    public static Flight copy(Flight flight) {
        Flight result = new Flight();
        result.setCode(flight.getCode());
        result.setOrigin(flight.getOrigin());
        result.setDestination(flight.getDestination());
        result.setDistance(flight.getDistance());
        result.setCarriers(flight.getCarriers());
        result.setAflBonuses(new HashSet<>());
        result.setScyteamBonuses(new HashSet<>());
        return result;
    }

    private static String createCode(Airport origin, Airport destination ) {
        return String.format("%s %s", origin.getCode(), destination.getCode());
    }


    private String code;

    private Airport origin;

    private Airport destination;

    private int distance;

    private Map<Airline, FlightCarrier> carriers;

    private Set<Bonus> aflBonuses;

    private Set<Bonus> scyteamBonuses;

    public String getCode() {
        return code;
    }

    public Airport getOrigin() {
        return origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public Map<Airline, FlightCarrier> getCarriers() {
        return carriers;
    }

    public Set<Bonus> getAflBonuses() {
        return aflBonuses;
    }

    public int getDistance() {
        return distance;
    }

    public void setAflBonuses(Set<Bonus> aflBonuses) {
        this.aflBonuses = aflBonuses;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setCarriers(Map<Airline, FlightCarrier> carriers) {
        this.carriers = carriers;
    }

    public Set<ServiceClass.SERVICE_CLASS_TYPE> getAllowedClasses(Airline airline) {
        return this.carriers.get(airline).getAllowedClasses();
    }

    public Set<Bonus> getScyteamBonuses() {
        return scyteamBonuses;
    }

    public void setScyteamBonuses(Set<Bonus> scyteamBonuses) {
        this.scyteamBonuses = scyteamBonuses;
    }

    public String getAflZones() {
        return String.format("%s%s%s",  this.origin.getAflZone(),
                                        this.destination.getAflZone(),
                                        "A" );
    }

    public String getAflReverseZones() {
        return String.format("%s%s%s",  this.destination.getAflZone(),
                                        this.origin.getAflZone(),
                                        "A" );
    }

    public String getScyteamZones() {
        return String.format("%s%s%s",  this.origin.getScyteamZone(),
                                        this.destination.getScyteamZone(),
                                        "S" );
    }

    public String getScyteamReverseZones() {
        return String.format("%s%s%s",  this.destination.getScyteamZone(),
                                        this.origin.getScyteamZone(),
                                        "S" );
    }

    public Collection<FlightCarrier> getListCarriers() {
        return this.carriers.values();
    }
    
    public void removeAflBonus(Bonus bonus) {
    	this.aflBonuses.remove(bonus);
    }
    
    public void removeScyteamBonus(Bonus bonus) {
    	this.scyteamBonuses.remove(bonus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(code, flight.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("  Flight: %s %,8d mi     afl: %5s, %5s     skyteam: %5s, %5s ",
                this.code,
                this.distance,
                this.getAflZones(),
                this.getAflReverseZones(),
                this.getScyteamZones(),
                this.getScyteamReverseZones()));
        sb.append(String.format("\n    Carriers: %d", this.carriers.keySet().size()));
        for (FlightCarrier carrier : this.carriers.values()) {
            sb.append(String.format("\n      %s", carrier.toString()));
        }
        if (this.aflBonuses != null) {
            sb.append(String.format("\n    AFL bonuses: %d", this.aflBonuses.size()));
            for (Bonus bonus : this.aflBonuses) {
                sb.append(String.format("\n      %s", bonus.toString()));
            }
        }
        if (this.scyteamBonuses != null) {
            sb.append(String.format("\n    Scyteam bonuses: %d", this.scyteamBonuses.size()));
            for (Bonus bonus : this.scyteamBonuses) {
                sb.append(String.format("\n      %s", bonus.toString()));
            }
            sb.append("\n");
        }
        return sb.toString();
    }


}
