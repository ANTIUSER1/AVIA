package ru.integrotech.airline.core.airline;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * class represents class of service provided by airline
 * <b>ru.integrotech.airline.core.airline.</b>ServiceClass
 *
 * Used in Charge project
 *
 */

public class ServiceClass implements Comparable<ServiceClass> {

    public static ServiceClass of(String code,
                                  int weight,
                                  String tariffCode,
                                  int tariffWeight,
                                  String subTariffCode,
                                  String bookingClass,
                                  int chargeCoeff) {

        ServiceClass result = new ServiceClass();

        Map<String, Tariff> tariffMap = new HashMap<>();
        Tariff tariff = Tariff.of(tariffCode, tariffWeight, subTariffCode, bookingClass, chargeCoeff);
        tariffMap.put(tariffCode, tariff);

        result.setType(SERVICE_CLASS_TYPE.valueOf(code));
        result.setWeight(weight);
        result.setTariffMap(tariffMap);

        return result;
    }

    private SERVICE_CLASS_TYPE type;

    private int weight;

    private Map<String, Tariff> tariffMap;

    public SERVICE_CLASS_TYPE getType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }

    public Map<String, Tariff> getTariffMap() {
        return tariffMap;
    }

    public void setType(SERVICE_CLASS_TYPE type) {
        this.type = type;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setTariffMap(Map<String, Tariff> tariffMap) {
        this.tariffMap = tariffMap;
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
