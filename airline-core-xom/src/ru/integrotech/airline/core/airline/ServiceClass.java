package ru.integrotech.airline.core.airline;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* class represents class listOf service listOf airline*/
public class ServiceClass implements Comparable<ServiceClass> {

    public static ServiceClass of(String code,
                                  int weight,
                                  String tariffCode,
                                  int tariffWeight,
                                  String subTariffCode,
                                  String bookingClass,
                                  int chargeCoeff) {
        ServiceClass result = new ServiceClass(SERVICE_CLASS_TYPE.valueOf(code), weight);
        Tariff tariff = Tariff.of(tariffCode, tariffWeight, subTariffCode, bookingClass, chargeCoeff);
        result.tariffMap.put(tariffCode, tariff);
        return result;
    }

    private final SERVICE_CLASS_TYPE type;

    private final int weight;

    private final Map<String, Tariff> tariffMap;

    private ServiceClass(SERVICE_CLASS_TYPE type, int weight) {
        this.type = type;
        this.weight = weight;
        this.tariffMap = new HashMap<>();
    }

    public SERVICE_CLASS_TYPE getType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }

    public Map<String, Tariff> getTariffMap() {
        return tariffMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceClass that = (ServiceClass) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }


    @Override
    public int compareTo(ServiceClass o) {
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return String.format("Class listOf service:  %-10.10s", this.type);
    }

    /*Service class must have determined type */
    public enum SERVICE_CLASS_TYPE {first, business, prestige, comfort, premium, economy}
}
