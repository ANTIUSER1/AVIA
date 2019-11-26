package ru.integrotech.su.outputparams.spend;


import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.su.common.Airport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

class MileCost {

    static MileCost of(Flight flight, boolean isAfl) {
        Set<Bonus> bonuses;
        if (isAfl) {
            bonuses = flight.getAflBonuses();
        } else {
            bonuses = flight.getScyteamBonuses();
        }
        return new MileCost(flight.getOrigin(), flight.getDestination(), bonuses);
    }

    static MileCost of(Route route, boolean isAfl) {
        Set<Bonus> bonuses;
        if (isAfl) {
            bonuses = route.getAflBonuses();
        } else {
            bonuses = route.getScyteamBonuses();
        }
        return new MileCost(route.getOrigin(), route.getDestination(), bonuses);
    }

    private Airport airportFrom;

    private Airport airportTo;

    private List<RequiredAward> requiredAwards;

    private MileCost(ru.integrotech.airline.core.location.Airport origin,
                     ru.integrotech.airline.core.location.Airport destination,
                     Set<Bonus> bonuses) {
        if (bonuses.size() != 0) {
            this.airportFrom = Airport.of(origin.getCode());
            this.airportTo = Airport.of(destination.getCode());
            this.requiredAwards = new ArrayList<>();
            for (Bonus bonus : bonuses) {
                this.requiredAwards.add(RequiredAward.of(bonus));
            }
        }
    }

    private MileCost() {
    }

    public Airport getAirportFrom() {
        return airportFrom;
    }

    public void setAirportFrom(Airport airportFrom) {
        this.airportFrom = airportFrom;
    }

    public Airport getAirportTo() {
        return airportTo;
    }

    public void setAirportTo(Airport airportTo) {
        this.airportTo = airportTo;
    }

    public List<RequiredAward> getRequiredAwards() {
        return requiredAwards;
    }

    public void setRequiredAwards(List<RequiredAward> requiredAwards) {
        this.requiredAwards = requiredAwards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MileCost mileCost = (MileCost) o;
        return Objects.equals(airportFrom, mileCost.airportFrom) &&
                Objects.equals(airportTo, mileCost.airportTo) &&
                Objects.equals(requiredAwards, mileCost.requiredAwards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airportFrom, airportTo, requiredAwards);
    }

}
