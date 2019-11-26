package ru.integrotech.airline.core.flight;



import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.location.Airport;

import java.util.*;

/* class contains all flights between two airports (origin and
destination)  and all aflBonuses acceptable for those flights*/
public class Flight {

    public static Flight of(Airport origin, Airport destination, int distance, Airline airline) {
        Flight result = new Flight(origin, destination, distance);
        result.getCarriers().put(airline, FlightCarrier.of(airline));
        return result;
    }

    public static Flight of(Airport origin, Airport destination, int distance, List<FlightCarrier> carriers) {
        Map<Airline, FlightCarrier> carrierMap = new HashMap<>();
        for (FlightCarrier carrier : carriers) {
            carrierMap.put(carrier.getCarrier(), carrier);
        }
        return new Flight(origin, destination, distance, carrierMap);
    }

    public static Flight of(Flight flight) {
        return new Flight(flight.origin, flight.destination, flight.distance, flight.carriers);
    }

    private static String createCode(Airport origin, Airport destination ) {
        return String.format("%s %s", origin.getCode(), destination.getCode());
    }

    private final String code;

    private final Airport origin;

    private final Airport destination;

    private final int distance;

    private final Map<Airline, FlightCarrier> carriers;

    private Set<Bonus> aflBonuses;

    private Set<Bonus> scyteamBonuses;

    private Flight(Airport origin, Airport destination, int distance) {
        this.code = Flight.createCode(origin, destination);
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        this.carriers = new HashMap<>();
        this.aflBonuses = new HashSet<>();
        this.scyteamBonuses = new HashSet<>();
    }

    private Flight(Airport origin, Airport destination, int distance, Map<Airline, FlightCarrier> carrierMap) {
        this.code = Flight.createCode(origin, destination);
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        this.carriers = carrierMap;
        this.aflBonuses = new HashSet<>();
        this.scyteamBonuses = new HashSet<>();
    }

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
