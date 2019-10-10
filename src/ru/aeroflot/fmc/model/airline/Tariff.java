package ru.aeroflot.fmc.model.airline;

import java.util.*;

/* class represents tariff listOf class listOf service */
public class Tariff implements Comparable<Tariff> {

    public static Tariff of(String code,  int weight, String subTariffCode, String bookingClass, int chargeCoeff) {
        Tariff tariff = new Tariff(code, weight);
        Set<SubTariff> subTariffs = new HashSet<>();
        subTariffs.add(SubTariff.of(subTariffCode, bookingClass, chargeCoeff));
        tariff.subTariffsMap.put(chargeCoeff,subTariffs);
        return tariff;
    }

    private final String code;

    private final int weight;

    private final HashMap<Integer, Set<SubTariff>> subTariffsMap;

    private Tariff(String code, int weight) {
        this.code = code;
        this.weight = weight;
        this.subTariffsMap = new HashMap<>();
    }

    public String getCode() {
        return code;
    }

    public int getWeight() {
        return weight;
    }

    public HashMap<Integer, Set<SubTariff>> getSubTariffsMap() {
        return subTariffsMap;
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
