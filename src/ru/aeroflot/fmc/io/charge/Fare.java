package ru.aeroflot.fmc.io.charge;

import ru.aeroflot.fmc.model.airline.SubTariff;
import ru.aeroflot.fmc.model.airline.Tariff;
import ru.aeroflot.fmc.model.flight.PassengerCharge;

import java.util.*;

class Fare {

    static Fare of(PassengerCharge charge) {
        List<AirlineFareGroup> airlineFareGroups = new ArrayList<>();
        airlineFareGroups.add(AirlineFareGroup.of(charge));
        return  new Fare(FareGroup.of(charge.getTariff()), airlineFareGroups);
    }

    private FareGroup fareGroup;

    private List<AirlineFareGroup> airlineFareGroups;

    private Fare(FareGroup fareGroup, List<AirlineFareGroup> airlineFareGroups) {
        this.fareGroup = fareGroup;
        this.airlineFareGroups = airlineFareGroups;
    }

    public Fare() {
    }

    public FareGroup getFareGroup() {
        return fareGroup;
    }

    public void setFareGroup(FareGroup fareGroup) {
        this.fareGroup = fareGroup;
    }

    public List<AirlineFareGroup> getAirlineFareGroups() {
        return airlineFareGroups;
    }

    public void setAirlineFareGroups(List<AirlineFareGroup> airlineFareGroups) {
        this.airlineFareGroups = airlineFareGroups;
    }

    void update(PassengerCharge charge) {
        int i = 0;

        while (i < this.airlineFareGroups.size() && (this.airlineFareGroups.get(i).getMilesCharged() != charge.getMilesCharged())
                ) {
            i++;
        }

        if (i == this.airlineFareGroups.size()) {
            this.airlineFareGroups.add(AirlineFareGroup.of(charge));
        } else {
            this.airlineFareGroups.get(i).update(charge);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fare fare = (Fare) o;
        return Objects.equals(fareGroup, fare.fareGroup) &&
                Objects.equals(airlineFareGroups, fare.airlineFareGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fareGroup, airlineFareGroups);
    }
}

