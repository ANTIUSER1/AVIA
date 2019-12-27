package ru.integrotech.su.outputparams.spend;


import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.su.common.Airline;
import ru.integrotech.su.common.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/*class for transform model.flight.SpendLkRoute to io format
 * not for use in program logic
 * all methods use only for tests to check proper buildResult listOf SpendRoute*/
public class SpendRoute implements Comparable<SpendRoute> {

    static SpendRoute ofAfl(Route route) {
        List<MileCost> mileCosts = new ArrayList<>();
        if (route.getAflBonuses() != null && route.getAflBonuses().size() > 0) {
            mileCosts.add(MileCost.of(route, true));
        } else {
            for (Flight flight : route.getFlights()) {
                if (flight.getAflBonuses() != null && flight.getAflBonuses().size() > 0) {
                    mileCosts.add(MileCost.of(flight, true));
                }
            }
        }

        boolean isSingle;
        if (route.isBonusSummation()) {
            isSingle = false;
        } else {
            isSingle = mileCosts.size() == 1;
        }
        return new SpendRoute(Location.of(route.getOrigin()), Location.of(route.getDestination()), getVia(route), true, isSingle, null, mileCosts);
    }

    static SpendRoute ofScyteam(Route route) {
        List<Airline> airlines = new ArrayList<>();
        List<MileCost> mileCosts = new ArrayList<>();

        for (ru.integrotech.airline.core.airline.Airline airline : route.getAirlines()) {
            airlines.add(Airline.of(airline.getCode()));
        }
        if (route.getScyteamBonuses() != null && route.getScyteamBonuses().size() > 0) {
            mileCosts.add(MileCost.of(route, false));
        } else {
            for (Flight flight : route.getFlights()) {
                if (flight.getScyteamBonuses() != null && flight.getScyteamBonuses().size() > 0) {
                    mileCosts.add(MileCost.of(flight, false));
                }
            }
        }

        boolean isSingle;
        if (route.isBonusSummation()) {
            isSingle = false;
        } else {
            isSingle = mileCosts.size() == 1;
        }
        return new SpendRoute(Location.of(route.getOrigin()), Location.of(route.getDestination()), getVia(route), false, isSingle, airlines, mileCosts);
    }

    private static Location getVia(Route route) {
        Location via = null;
        if (route.getFlights().size() > 1) {
            via = Location.of(route.getAirports().get(ru.integrotech.airline.core.flight.Route.MAX_SEGMENTS_SIZE - 1));
        }

        return via;
    }

    private Location origin;

    private Location destination;

    private Location via;

    private boolean isAfl;

    private boolean single;

    private List<Airline> airlines;

    private List<MileCost> mileCosts;

    private SpendRoute(Location origin, Location destination, Location via, boolean isAfl, boolean isSingle,  List<Airline> airlines, List<MileCost> mileCosts) {
        this.origin = origin;
        this.destination = destination;
        this.via = via;
        this.isAfl = isAfl;
        this.single = isSingle;
        this.airlines = airlines;
        this.mileCosts = mileCosts;
    }

    private SpendRoute() {
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public Location getVia() {
        return via;
    }

    public void setVia(Location via) {
        this.via = via;
    }

    public boolean getIsAfl() {
        return this.isAfl;
    }

    public void setIsAfl(boolean isAfl) {
        this.isAfl = isAfl;
    }

    public List<Airline> getAirlines() {
        return airlines;
    }

    public void setAirlines(List<Airline> airlines) {
        this.airlines = airlines;
    }

    public List<MileCost> getMileCosts() {
        return mileCosts;
    }

    public void setMileCosts(List<MileCost> mileCosts) {
        this.mileCosts = mileCosts;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    String getCityKey() {
        return String.format("%s%s", this.origin.getCity().getCityCode(), this.destination.getCity().getCityCode());
    }

    public void sort() {
        if (this.airlines != null) {
            Collections.sort(this.airlines);
        }
        for (MileCost mileCost : this.mileCosts) {
            Collections.sort(mileCost.getRequiredAward());
        }
    }

    /*the absence listOf bonuses at least in one segment means that route is incorrect*/
    boolean isInvalid() {
        if (this.origin.getCity().equals(this.destination.getCity())) return true;
        if (this.mileCosts == null || this.mileCosts.isEmpty()) return true;
        for (MileCost mileCost : this.mileCosts) {
            if (mileCost.getRequiredAward() == null || mileCost.getRequiredAward().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpendRoute that = (SpendRoute) o;
        return isAfl == that.isAfl &&
                single == that.single &&
                Objects.equals(origin, that.origin) &&
                Objects.equals(destination, that.destination) &&
                Objects.equals(via, that.via) &&
                Objects.equals(airlines, that.airlines) &&
                Objects.equals(mileCosts, that.mileCosts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination, via, isAfl, single, airlines, mileCosts);
    }

    @Override
    public int compareTo(SpendRoute o) {
        int result = 0;
        if (this.origin.compareTo(o.origin) != 0) {
            result = this.origin.compareTo(o.origin);
        } else if ( this.destination.getCity().getWeight() != o.destination.getCity().getWeight()){
            result = this.destination.getCity().getWeight() - o.destination.getCity().getWeight();
        } else if (this.via == null && o.via == null) {
            result = 0;
        } else if (this.via == null) {
            result = -1;
        } else {
            result = this.via.compareTo(o.via);
        }
        return result;
    }


}

