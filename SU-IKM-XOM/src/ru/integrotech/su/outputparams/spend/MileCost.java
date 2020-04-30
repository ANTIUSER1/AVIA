package ru.integrotech.su.outputparams.spend;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.core.location.Airport;

/**
 * container for MilesCost data
 *
 * data( private AirportWithZone airportFrom; private AirportWithZone airportTo;
 * private int distance; private List<RequiredAward> requiredAward;)
 */
class MileCost {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param flight
	 * @param isAfl
	 * @return
	 */
	static MileCost of(Flight flight, boolean isAfl) {
		MileCost res = new MileCost();
		Set<Bonus> bonuses;
		if (isAfl) {
			bonuses = flight.getAflBonuses();
		} else {
			bonuses = flight.getScyteamBonuses();
		}
		if (bonuses.size() != 0) {
			Airport origin = flight.getOrigin();
			Airport destination = flight.getDestination();

			res.setAirportFrom(AirportWithZone.of(origin, isAfl));
			res.setAirportTo(AirportWithZone.of(destination, isAfl));
			res.setDistance(flight.getDistance());
			res.setRequiredAward(new ArrayList<>());
			for (Bonus bonus : bonuses) {
				res.getRequiredAward().add(RequiredAward.of(bonus));
			}
		}
		return res;
	}

	/**
	 * Static constructor
	 *
	 * @param route
	 * @param isAfl
	 * @return
	 */
	static MileCost of(Route route, boolean isAfl) {
		MileCost res = new MileCost();
		Set<Bonus> bonuses;
		if (isAfl) {
			bonuses = route.getAflBonuses();
		} else {
			bonuses = route.getScyteamBonuses();
		}
		if (bonuses.size() != 0) {
			Airport origin = route.getOrigin();
			Airport destination = route.getDestination();

			res.setAirportFrom(AirportWithZone.of(origin, isAfl));
			res.setAirportTo(AirportWithZone.of(destination, isAfl));
			res.setDistance(route.getDistance());
			res.setRequiredAward(new ArrayList<>());
			for (Bonus bonus : bonuses) {
				res.getRequiredAward().add(RequiredAward.of(bonus));
			}
		}
		return res;
	}

	private AirportWithZone airportFrom;

	private AirportWithZone airportTo;

	private int distance;

	private List<RequiredAward> requiredAward;

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

	public List<RequiredAward> getRequiredAward() {
		return requiredAward;
	}

	public void setRequiredAward(List<RequiredAward> requiredAwards) {
		this.requiredAward = requiredAwards;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MileCost mileCost = (MileCost) o;
		return Objects.equals(airportFrom, mileCost.airportFrom)
				&& Objects.equals(airportTo, mileCost.airportTo)
				&& Objects.equals(requiredAward, mileCost.requiredAward);
	}

	@Override
	public int hashCode() {
		return Objects.hash(airportFrom, airportTo, requiredAward);
	}

}
