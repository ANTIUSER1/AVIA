package ru.integrotech.su.outputparams.spend;

import ru.integrotech.su.common.Location;

import java.util.*;

/*class for transform model.flight.ChargeRoute to io format
 * not for use in program logic
 * all methods use only for tests to check proper buildResult listOf SpendLkRoute*/
public class SpendLkRoute implements Comparable<SpendLkRoute> {

    public static SpendLkRoute of(SpendRoute spendRoute) {
        return new SpendLkRoute(spendRoute.getOrigin(), spendRoute.getDestination(), new ArrayList<>());
    }

    private Location origin;

    private Location destination;

    private List<RequiredAward> requiredAwards;

    public SpendLkRoute(Location origin, Location destination, List<RequiredAward> requiredAwards) {
        this.origin = origin;
        this.destination = destination;
        this.requiredAwards = requiredAwards;
    }

    private SpendLkRoute() {
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

    public List<RequiredAward> getRequiredAwards() {
        return requiredAwards;
    }

    public void setRequiredAwards(List<RequiredAward> requiredAwards) {
        this.requiredAwards = requiredAwards;
    }

    public void sort() {
        Collections.sort(this.requiredAwards);
    }

    void updateFitsMilesIntervals(int milesMin, int milesMax) {
        for (RequiredAward award : this.requiredAwards) {
            int value = award.getValue();
            award.setFitsMilesInterval(value >= milesMin && value <= milesMax);
        }
    }

    @Override
    public int compareTo(SpendLkRoute o) {
        return this.destination.getCity().getWeight() - o.destination.getCity().getWeight();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpendLkRoute spendLkRoute = (SpendLkRoute) o;
        return Objects.equals(origin, spendLkRoute.origin) &&
                Objects.equals(destination, spendLkRoute.destination) &&
                Objects.equals(requiredAwards, spendLkRoute.requiredAwards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination, requiredAwards);
    }
}

