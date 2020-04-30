package ru.integrotech.su.outputparams.charge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.su.common.Location;

/**
 * class for transform model.flight.SpendRoute to io format not for use in
 * program logic all methods use only for tests to check proper buildResult
 * listOf ChargeRoute
 * 
 * <hr />
 * class for transform ru.integrotech.airline.core.flight.Route to chargeRequest
 * format
 *
 * data( private Location origin; private Location destination; private Location
 * via; private List<Segment> segments;)
 */

public class ChargeRoute implements Comparable<ChargeRoute> {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param route
	 * @return
	 */
	public static ChargeRoute of(Route route, Airline airline) {
		Location via = null;
		if (route.getFlights().size() > 1) {
			via = Location.of(route.getFlights().get(0).getDestination());
		}
		List<Segment> segments = new ArrayList<>();
		if (airline != null) {
			for (Flight flight : route.getFlights(airline)) {
				segments.add(Segment.of(flight, airline));
			}
		} else {
			for (Flight flight : route.getFlights()) {
				segments.add(Segment.of(flight));
			}
		}
		ChargeRoute res = new ChargeRoute();
		res.setOrigin(Location.of(route.getOrigin()));
		res.setVia(via);
		res.setDestination(Location.of(route.getDestination()));
		res.setSegments(segments);
		return res;
	}

	private Location origin;

	private Location destination;

	private Location via;

	private List<Segment> segments;

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

	public List<Segment> getSegments() {
		return segments;
	}

	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}

	public void sort() {
		for (Segment segment : this.segments) {
			Collections.sort(segment.milesAmounts);
			for (MilesAmount milesAmount : segment.milesAmounts) {
				Collections.sort(milesAmount.getFareGroups());
				for (Fare fare : milesAmount.getFareGroups()) {
					Collections.sort(fare.getAirlineFareGroups());
				}
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ChargeRoute that = (ChargeRoute) o;
		return Objects.equals(origin, that.origin)
				&& Objects.equals(destination, that.destination)
				&& Objects.equals(via, that.via)
				&& Objects.equals(segments, that.segments);
	}

	@Override
	public int hashCode() {
		return Objects.hash(origin, destination, via, segments);
	}

	@Override
	public int compareTo(ChargeRoute o) {
		int destinationDiff = this.destination.compareTo(o.destination);
		if (destinationDiff != 0)
			return destinationDiff;
		else if (this.via == null && o.via != null)
			return 1;
		else if (this.via != null && o.via == null)
			return -1;
		else if (this.via != null && o.via != null) {
			return this.via.compareTo(o.via);
		} else
			return ChargeUtil.getMinMilesCharge(this)
					- ChargeUtil.getMinMilesCharge(o);
	}
}
