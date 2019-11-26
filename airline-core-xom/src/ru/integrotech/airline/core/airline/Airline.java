package ru.integrotech.airline.core.airline;

import java.util.*;

/* class represents airline */
public class Airline implements Comparable<Airline> {

    public static final String AFL_CODE = "SU";

    public static Airline of(String code, String name, int minMilesCharge, String minMilesLimit) {
        Airline airline = new  Airline(code, name, minMilesCharge, minMilesLimit);
        if (code.equals(AFL_CODE)) {
            airline.defaultServiceClasses.add(ServiceClass.SERVICE_CLASS_TYPE.business);
            airline.defaultServiceClasses.add(ServiceClass.SERVICE_CLASS_TYPE.economy);
        }
        return airline;
    }

    private final String code;

    private final String name;

    private final int minMilesCharge;

    private final String minMilesLimit;

    private final Map<ServiceClass.SERVICE_CLASS_TYPE, ServiceClass> serviceClassMap;

    /*service classes represents in all airline's flights*/
    private Set<ServiceClass.SERVICE_CLASS_TYPE> defaultServiceClasses;

    private Airline(String code, String name, int minMilesCharge, String minMilesLimit) {
        this.code = code;
        this.name = name;
        this.minMilesCharge = minMilesCharge;
        this.minMilesLimit = minMilesLimit;
        this.serviceClassMap = new HashMap<>();
        this.defaultServiceClasses = new HashSet<>();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getMinMilesCharge() {
        return minMilesCharge;
    }

    public String getMinMilesLimit() {
        return minMilesLimit;
    }

    public Map<ServiceClass.SERVICE_CLASS_TYPE, ServiceClass> getServiceClassMap() {
        return serviceClassMap;
    }

    public Set<ServiceClass.SERVICE_CLASS_TYPE> getDefaultServiceClasses() {
        return defaultServiceClasses;
    }

    public void setDefaultServiceClasses(Set<ServiceClass.SERVICE_CLASS_TYPE> defaultServiceClasses) {
        this.defaultServiceClasses = defaultServiceClasses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airline airline = (Airline) o;
        return Objects.equals(code, airline.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public int compareTo(Airline o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return String.format("Airline: %3s, %25.25s, %,5d mi, %3.3s",
                this.getCode(),
                this.getName(),
                this.minMilesCharge,
                this.minMilesLimit);
    }
}
