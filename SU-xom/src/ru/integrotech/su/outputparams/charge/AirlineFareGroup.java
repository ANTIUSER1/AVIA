package ru.integrotech.su.outputparams.charge;


import ru.integrotech.airline.core.flight.PassengerCharge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

class AirlineFareGroup implements Comparable<AirlineFareGroup> {
 
    static AirlineFareGroup of(PassengerCharge charge) {
        List<String> prefixes = new ArrayList<>();
        prefixes.add(charge.getSubTariff().getFareCode());
        return new AirlineFareGroup(prefixes,
                                    charge.getMilesCharged(),
                                    charge.getMilesQualifying(),
                                    charge.getMilesBonus());
    }

    private List<String> farePrefixes;

    private int milesCharged;

    private int milesQualifying;

    private int milesBonus;

    private AirlineFareGroup(List<String> farePrefixes, int milesCharged, int milesQualifying, int milesBonus) {
        this.farePrefixes = farePrefixes;
        this.milesCharged = milesCharged;
        this.milesQualifying = milesQualifying;
        this.milesBonus = milesBonus;
    }

    private AirlineFareGroup() {
    }

    public List<String> getFarePrefixes() {
        return farePrefixes;
    }

    public void setFarePrefixes(List<String> farePrefixes) {
        this.farePrefixes = farePrefixes;
    }

    public int getMilesCharged() {
        return milesCharged;
    }

    public void setMilesCharged(int milesCharged) {
        this.milesCharged = milesCharged;
    }

    public int getMilesQualifying() {
        return milesQualifying;
    }

    public void setMilesQualifying(int milesQualifying) {
        this.milesQualifying = milesQualifying;
    }

    public int getMilesBonus() {
        return milesBonus;
    }

    public void setMilesBonus(int milesBonus) {
        this.milesBonus = milesBonus;
    }

    void update(PassengerCharge charge) {
        this.farePrefixes.add(charge.getSubTariff().getFareCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirlineFareGroup that = (AirlineFareGroup) o;
        return milesCharged == that.milesCharged &&
                milesQualifying == that.milesQualifying &&
                milesBonus == that.milesBonus &&
                Objects.equals(farePrefixes, that.farePrefixes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(farePrefixes, milesCharged, milesQualifying, milesBonus);
    }

    @Override
    public int compareTo(AirlineFareGroup o) {
        return this.milesCharged - o.milesCharged;
    }
}