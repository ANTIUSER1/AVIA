package ru.aeroflot.fmc.model.flight;

import ru.aeroflot.fmc.model.airline.Airline;
import ru.aeroflot.fmc.model.airline.ServiceClass;

import java.util.*;

public class FlightCarrier {

    public static FlightCarrier of(Airline carrier) {
        Set<ServiceClass.SERVICE_CLASS_TYPE> extraClasses = new HashSet<>();
        return new FlightCarrier(carrier, extraClasses);
    }

    public static FlightCarrier of(Airline carrier, Set<ServiceClass.SERVICE_CLASS_TYPE> extraClasses) {
        return new FlightCarrier(carrier, extraClasses);
    }

    private final Airline carrier;

    private final Set<ServiceClass.SERVICE_CLASS_TYPE> extraClasses;

    private List<PassengerCharge> passengerCharges;

    private FlightCarrier(Airline carrier, Set<ServiceClass.SERVICE_CLASS_TYPE> extraClasses) {
        this.carrier = carrier;
        this.extraClasses = extraClasses;
    }

    public Airline getCarrier() {
        return carrier;
    }

    public Set<ServiceClass.SERVICE_CLASS_TYPE> getExtraClasses() {
        return extraClasses;
    }

    public List<PassengerCharge> getPassengerCharges() {
        return passengerCharges;
    }

    public void setPassengerCharges(List<PassengerCharge> passengerCharges) {
        this.passengerCharges = passengerCharges;
    }

    public Set<ServiceClass.SERVICE_CLASS_TYPE> getAllowedClasses() {
        Set<ServiceClass.SERVICE_CLASS_TYPE> result = new HashSet<>(this.carrier.getDefaultServiceClasses());
        result.addAll(this.extraClasses);
        return result;
    }

    public void addExtraClasses(String... classTypes) {
        for (String classType : classTypes) {
            this.extraClasses.add(ServiceClass.SERVICE_CLASS_TYPE.valueOf(classType));
        }
    }

    public void buildPassengerCharges(Flight flight, int factor, boolean isRound, Airline airline) {
        this.passengerCharges = PassengerCharge.listOf(flight, factor, isRound, airline);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Carrier: %3s, %10s", this.carrier.getCode(), this.carrier.getName()));
        List<ServiceClass.SERVICE_CLASS_TYPE> allowedClasses = new ArrayList<>(this.getAllowedClasses());
        if (allowedClasses.size() != 0) {
            Collections.sort(allowedClasses);
            sb.append(" [");
            int counter = 0;
            for (ServiceClass.SERVICE_CLASS_TYPE TYPE : allowedClasses) {
                counter++;
                if (counter < allowedClasses.size()) {
                    sb.append(String.format("%s, ", TYPE.name()));
                } else {
                    sb.append(String.format(" %s", TYPE.name()));
                }
            }
            sb.append("]");
        }
        return sb.toString();
    }
}
