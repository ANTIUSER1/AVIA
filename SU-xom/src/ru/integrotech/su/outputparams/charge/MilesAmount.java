package ru.integrotech.su.outputparams.charge;


import ru.integrotech.airline.core.flight.PassengerCharge;
import ru.integrotech.su.common.Airline;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class MilesAmount {

    public static MilesAmount of(PassengerCharge charge) {
        List<Fare> fareGroups = new ArrayList<>();
        fareGroups.add(Fare.of(charge));
        return new  MilesAmount(Airline.of(charge.getAirline().getCode()),
                    ClassOfService.of(charge.getServiceClass()),
                    charge.getDistance(),
                    fareGroups);
    }

    private Airline airline;

    private ClassOfService classOfService;

    private int distance;

    private List<Fare> fareGroups;

    private MilesAmount(Airline airline, ClassOfService classOfService, int distance, List<Fare> fareGroups) {
        this.airline = airline;
        this.classOfService = classOfService;
        this.distance = distance;
        this.fareGroups = fareGroups;
    }

    private MilesAmount() {
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public ClassOfService getClassOfService() {
        return classOfService;
    }

    public void setClassOfService(ClassOfService classOfService) {
        this.classOfService = classOfService;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public List<Fare> getFareGroups() {
        return fareGroups;
    }

    public void setFareGroups(List<Fare> fareGroups) {
        this.fareGroups = fareGroups;
    }

    void update(PassengerCharge charge) {
        int i = 0;

        while (i < this.fareGroups.size() && !this.fareGroups.get(i).getFareGroup().getFareGroupCode().equals(charge.getTariff().getCode())) {
            i++;
        }

        if (i == this.fareGroups.size()) {
            this.fareGroups.add(Fare.of(charge));
        } else {
            this.fareGroups.get(i).update(charge);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MilesAmount that = (MilesAmount) o;
        return distance == that.distance &&
                Objects.equals(airline, that.airline) &&
                Objects.equals(classOfService, that.classOfService) &&
                Objects.equals(fareGroups, that.fareGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airline, classOfService, distance, fareGroups);
    }




}
