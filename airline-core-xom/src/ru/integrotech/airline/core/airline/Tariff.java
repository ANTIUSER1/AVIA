package ru.integrotech.airline.core.airline;

import java.util.*;

/**
 * class represents tariff of service class  of airline
 *
 * Used in Charge project
 *
 */

public class Tariff implements Comparable<Tariff> {

    public static Tariff of(String code,  int weight, String subTariffCode, String bookingClass, int chargeCoeff) {

        Tariff result = new Tariff();

        Set<SubTariff> subTariffs = new HashSet<>();
        subTariffs.add(SubTariff.of(subTariffCode, bookingClass, chargeCoeff));
        HashMap<Integer, Set<SubTariff>> subTariffsMap = new HashMap<>();
        subTariffsMap.put(chargeCoeff,subTariffs);

        result.setCode(code);
        result.setWeight(weight);
        result.setSubTariffsMap(subTariffsMap);

        return result;
    }

    private String code;

    private int weight;

    private HashMap<Integer, Set<SubTariff>> subTariffsMap;

    public String getCode() {
        return code;
    }

    public int getWeight() {
        return weight;
    }

    public HashMap<Integer, Set<SubTariff>> getSubTariffsMap() {
        return subTariffsMap;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setSubTariffsMap(HashMap<Integer, Set<SubTariff>> subTariffsMap) {
        this.subTariffsMap = subTariffsMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return Objects.equals(code, tariff.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }


    @Override
    public int compareTo(Tariff o) {
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Tariff:  %-17.17s", this.code));
        int counter = this.subTariffsMap.entrySet().size();
        for (Map.Entry<Integer, Set<SubTariff>> subTariffEntry : this.subTariffsMap.entrySet()) {
            counter--;
            List<String> prefixes = new ArrayList<>();

            for (SubTariff subTariff : subTariffEntry.getValue()) {
                prefixes.add(String.format("%s%s", subTariff.getBookingClass(), subTariff.getFareCode()));
            }

            sb.append(String.format("%4s%3d : %s", "{",subTariffEntry.getKey(), prefixes));
            sb.append("}");
            if (counter > 0) sb.append(";");
        }
        return sb.toString();
    }
}
