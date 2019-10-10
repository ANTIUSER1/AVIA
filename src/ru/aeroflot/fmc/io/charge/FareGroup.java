package ru.aeroflot.fmc.io.charge;

import ru.aeroflot.fmc.model.airline.Tariff;

import java.util.Objects;

class FareGroup {

    static FareGroup of(Tariff tariff) {
        return new FareGroup(tariff.getCode());
    }

    private String fareGroupCode;

    private FareGroup(String fareGroupCode) {
        this.fareGroupCode = fareGroupCode;
    }

    private FareGroup() {
    }

    public String getFareGroupCode() {
        return fareGroupCode;
    }

    public void setFareGroupCode(String fareGroupCode) {
        this.fareGroupCode = fareGroupCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FareGroup fareGroup = (FareGroup) o;
        return Objects.equals(fareGroupCode, fareGroup.fareGroupCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fareGroupCode);
    }
}

