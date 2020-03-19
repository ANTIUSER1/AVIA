package ru.integrotech.su.outputparams.spend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import ru.integrotech.su.common.Location;

/**
 * class for transform model.flight.ChargeRoute to io format not for use in
 * program logic all methods use only for tests to check proper buildResult
 * listOf SpendLkRoute
 * 
 * 
 * <br />
 * class for transform ru.integrotech.airline.core.flight.Route to
 * spendLKRequest format
 *
 * data(private Location origin; private Location destination; private
 * List<RequiredAward> requiredAwards;)
 * */
public class SpendLkRoute implements Comparable<SpendLkRoute> {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param spendRoute
	 * @return
	 */
	public static SpendLkRoute of(SpendRoute spendRoute) {
		SpendLkRoute res = new SpendLkRoute();
		res.setOrigin(spendRoute.getOrigin());
		res.setDestination(spendRoute.getDestination());
		res.setRequiredAwards(new ArrayList<RequiredAward>());
		return res;
	}

	private Location origin;

	private Location destination;

	private List<RequiredAward> requiredAwards;

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
		return this.destination.getCity().getWeight()
				- o.destination.getCity().getWeight();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SpendLkRoute spendLkRoute = (SpendLkRoute) o;
		return Objects.equals(origin, spendLkRoute.origin)
				&& Objects.equals(destination, spendLkRoute.destination)
				&& Objects.equals(requiredAwards, spendLkRoute.requiredAwards);
	}

	@Override
	public int hashCode() {
		return Objects.hash(origin, destination, requiredAwards);
	}
}
