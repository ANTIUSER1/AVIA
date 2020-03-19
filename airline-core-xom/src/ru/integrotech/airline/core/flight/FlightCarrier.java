package ru.integrotech.airline.core.flight;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.info.PassengerChargeInfo;

import java.util.*;

/**
 * Describes airline completing exact flight
 *
 * Used in all projects
 *
 */

public class FlightCarrier {

    public static FlightCarrier of(Airline airline) {
        FlightCarrier result = new FlightCarrier();
        result.setCarrier(airline);
        result.setExtraClasses(new HashSet<>());
        return result;
    }

    public static FlightCarrier of(Airline airline, Set<ServiceClass.SERVICE_CLASS_TYPE> extraClasses) {
        FlightCarrier result = new FlightCarrier();
        result.setCarrier(airline);
        result.setExtraClasses(extraClasses);
        return result;
    }

    private Airline carrier;

    private Set<ServiceClass.SERVICE_CLASS_TYPE> extraClasses;

    private List<PassengerChargeInfo> passengerChargeInfos;

    public Airline getCarrier() {
        return carrier;
    }

    public Set<ServiceClass.SERVICE_CLASS_TYPE> getExtraClasses() {
        return extraClasses;
    }

    public List<PassengerChargeInfo> getPassengerChargeInfos() {
        return passengerChargeInfos;
    }

    public void setPassengerChargeInfos(List<PassengerChargeInfo> passengerChargeInfos) {
        this.passengerChargeInfos = passengerChargeInfos;
    }

    public Set<ServiceClass.SERVICE_CLASS_TYPE> getAllowedClasses() {
        Set<ServiceClass.SERVICE_CLASS_TYPE> result = new HashSet<>(this.carrier.getDefaultServiceClasses());
        result.addAll(this.extraClasses);
        return result;
    }

    public void setCarrier(Airline carrier) {
        this.carrier = carrier;
    }

    public void setExtraClasses(Set<ServiceClass.SERVICE_CLASS_TYPE> extraClasses) {
        this.extraClasses = extraClasses;
    }

    public void addExtraClasses(String... classTypes) {
        for (String classType : classTypes) {
            this.extraClasses.add(ServiceClass.SERVICE_CLASS_TYPE.valueOf(classType));
        }
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
