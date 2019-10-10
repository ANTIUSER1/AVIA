package ru.aeroflot.fmc.io.spend;

import ru.aeroflot.fmc.io.common.Airport;
import ru.aeroflot.fmc.model.bonus.Bonus;
import ru.aeroflot.fmc.model.flight.Route;
import ru.aeroflot.fmc.model.flight.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

class MileCost {

    static MileCost of(Flight flight, boolean isAfl, int milesMin, int milesMax) {
        Set<Bonus> bonuses;
        if (isAfl) {
            bonuses = flight.getAflBonuses();
        } else {
            bonuses = flight.getScyteamBonuses();
        }
        return new MileCost(flight.getOrigin(), flight.getDestination(), bonuses, milesMin, milesMax);
    }

    static MileCost of(Route route, boolean isAfl, int milesMin, int milesMax) {
        Set<Bonus> bonuses;
        if (isAfl) {
            bonuses = route.getAflBonuses();
        } else {
            bonuses = route.getScyteamBonuses();
        }
        return new MileCost(route.getOrigin(), route.getDestination(), bonuses, milesMin, milesMax);
    }

    private Airport airportFrom;

    private Airport airportTo;

    private List<RequiredAward> requiredAwards;

    private MileCost(ru.aeroflot.fmc.model.location.Airport origin,
                     ru.aeroflot.fmc.model.location.Airport destination,
                     Set<Bonus> bonuses,
                     int minMiles,
                     int maxMiles) {
        if (bonuses.size() != 0) {
            this.airportFrom = Airport.of(origin);
            this.airportTo = Airport.of(destination);
            this.requiredAwards = new ArrayList<>();
            for (Bonus bonus : bonuses) {
                this.requiredAwards.add(RequiredAward.of(bonus, minMiles, maxMiles));
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
