package ru.integrotech.su.outputparams.spend;


import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.core.location.Airport;

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
        return new MileCost(flight.getOrigin(), flight.getDestination(), flight.getDistance(), bonuses, isAfl);
    }

    static MileCost of(Route route, boolean isAfl) {
        Set<Bonus> bonuses;
        if (isAfl) {
            bonuses = route.getAflBonuses();
        } else {
            bonuses = route.getScyteamBonuses();
        }
        return new MileCost(route.getOrigin(), route.getDestination(), route.getDistance(), bonuses, isAfl);
    }

    private AirportWithZone airportFrom;

    private AirportWithZone airportTo;

    private int distance;

    private List<RequiredAward> requiredAwards;

    private MileCost(Airport origin, Airport destination, int distance, Set<Bonus> bonuses, boolean isAfl) {
        if (bonuses.size() != 0) {
            this.airportFrom = AirportWithZone.of(origin, isAfl);
            this.airportTo = AirportWithZone.of(destination, isAfl);
            this.distance = distance;
            this.requiredAwards = new ArrayList<>();
            for (Bonus bonus : bonuses) {
                this.requiredAwards.add(RequiredAward.of(bonus));
            }
        }
    }

    private MileCost() {
    }

    public AirportWithZone getAirportFrom() {
        return airportFrom;
    }

    public void setAirportFrom(AirportWithZone airportFrom) {
        this.airportFrom = airportFrom;
    }

    public AirportWithZone getAirportTo() {
        return airportTo;
    }

    public void setAirportTo(AirportWithZone airportTo) {
        this.airportTo = airportTo;
    }
    
    public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
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
