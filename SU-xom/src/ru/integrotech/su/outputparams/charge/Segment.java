package ru.integrotech.su.outputparams.charge;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.FlightCarrier;
import ru.integrotech.airline.core.info.PassengerChargeInfo;
import ru.integrotech.su.common.Location;

/**
 * container for Segment
 *
 * data( private Location origin; private Location destination;
 * List<MilesAmount> milesAmounts; )
 */
class Segment {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param flight
	 * @param airline
	 * @return
	 */
	public static Segment of(Flight flight, Airline airline) {
		Map<String, MilesAmount> milesAmounts = new LinkedHashMap<>();
		for (PassengerChargeInfo charge : flight.getCarriers().get(airline)
				.getPassengerChargeInfos()) {
			String key = String.format("%s%s", charge.getAirline(), charge.getServiceClass().getType().name());
			if (!milesAmounts.containsKey(key)) {
				milesAmounts.put(key, MilesAmount.of(charge));
			} else {
				milesAmounts.get(key).update(charge);
			}
		}
		Segment res = new Segment();
		res.setOrigin(Location.of(flight.getOrigin()));
		res.setDestination(Location.of(flight.getDestination()));
		res.setMilesAmounts(new ArrayList<>(milesAmounts.values()));
		return res;
	}

	public static Segment of(Flight flight) {
		Map<String, MilesAmount> milesAmounts = new LinkedHashMap<>();
		for (FlightCarrier carrier : flight.getCarriers().values()) {
			for (PassengerChargeInfo charge : carrier.getPassengerChargeInfos()) {
				String key = String.format("%s%s", charge.getAirline(), charge.getServiceClass().getType().name());
				if (!milesAmounts.containsKey(key)) {
					milesAmounts.put(key, MilesAmount.of(charge));
				} else {
					milesAmounts.get(key).update(charge);
				}
			}
		}

		return new Segment(Location.of(flight.getOrigin()), Location.of(flight
				.getDestination()), new ArrayList<>(milesAmounts.values()));
	}

	private Location origin;

	private Location destination;

	List<MilesAmount> milesAmounts;

	private Segment(Location origin, Location destination,
			List<MilesAmount> milesAmounts) {
		this.origin = origin;
		this.destination = destination;
		this.milesAmounts = milesAmounts;
	}

	private Segment() {
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

	public List<MilesAmount> getMilesAmounts() {
		return milesAmounts;
	}

	public void setMilesAmounts(List<MilesAmount> milesAmounts) {
		this.milesAmounts = milesAmounts;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Segment segment = (Segment) o;
		return Objects.equals(origin, segment.origin)
				&& Objects.equals(destination, segment.destination)
				&& Objects.equals(milesAmounts, segment.milesAmounts);
	}

	@Override
	public int hashCode() {
		return Objects.hash(origin, destination, milesAmounts);
	}

}
