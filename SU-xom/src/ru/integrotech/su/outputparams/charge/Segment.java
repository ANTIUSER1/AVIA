package ru.integrotech.su.outputparams.charge;


import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.FlightCarrier;
import ru.integrotech.airline.core.flight.PassengerCharge;
import ru.integrotech.su.common.Location;

import java.util.*;

class Segment {

    public static Segment of(Flight flight, Airline airline) {
        Map<ServiceClass, MilesAmount> milesAmounts = new LinkedHashMap<>();
        for (PassengerCharge charge : flight.getCarriers().get(airline).getPassengerCharges()) {
             if (!milesAmounts.containsKey(charge.getServiceClass())) {
                 milesAmounts.put(charge.getServiceClass(), MilesAmount.of(charge));
             } else {
                 milesAmounts.get(charge.getServiceClass()).update(charge);
             }
        }

        return new Segment(Location.of(flight.getOrigin()),
                Location.of(flight.getDestination()),
                new ArrayList<>(milesAmounts.values()));
    }

    public static Segment of(Flight flight) {
        Map<ServiceClass, MilesAmount> milesAmounts = new LinkedHashMap<>();
        for (FlightCarrier carrier : flight.getCarriers().values()) {
             for (PassengerCharge charge : carrier.getPassengerCharges())
                 if (!milesAmounts.containsKey(charge.getServiceClass())) {
                     milesAmounts.put(charge.getServiceClass(), MilesAmount.of(charge));
                 } else {
                     milesAmounts.get(charge.getServiceClass()).update(charge);
                 }
        }

        return new Segment(Location.of(flight.getOrigin()),
                Location.of(flight.getDestination()),
                new ArrayList<>(milesAmounts.values()));
    }

    private Location origin;

    private Location destination;

    List<MilesAmount> milesAmounts;

    private Segment(Location origin, Location destination, List<MilesAmount> milesAmounts) {
        this.origin = origin;
        this.destination = destination;
        this.milesAmounts = milesAmounts;
    }

    private Segment() {
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public List<MilesAmount> getMilesAmounts() {
        return milesAmounts;
    }

    public void setMilesAmounts(List<MilesAmount> milesAmounts) {
        this.milesAmounts = milesAmounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return Objects.equals(origin, segment.origin) &&
                Objects.equals(destination, segment.destination) &&
                Objects.equals(milesAmounts, segment.milesAmounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination, milesAmounts);
    }
    
}

