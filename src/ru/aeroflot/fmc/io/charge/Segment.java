package ru.aeroflot.fmc.io.charge;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.aeroflot.fmc.io.common.Location;
import ru.aeroflot.fmc.model.airline.Airline;
import ru.aeroflot.fmc.model.airline.ServiceClass;
import ru.aeroflot.fmc.model.flight.Flight;
import ru.aeroflot.fmc.model.flight.PassengerCharge;

class Segment {

	public static Segment of(Flight flight, Airline airline) {
		Map<ServiceClass, MilesAmount> milesAmounts = new LinkedHashMap<>();

		/*
		 * System.out.println((flight.getCarriers().get(airline)
		 * .getPassengerCharges() == null) + "  FFFFFFFFFFF!!  ");
		 * 
		 * System.out.println(flight.getCarriers().get(airline));
		 */

		for (PassengerCharge charge : flight.getCarriers().get(airline)
				.getPassengerCharges()) {
			if (!milesAmounts.containsKey(charge.getServiceClass())) {
				milesAmounts.put(charge.getServiceClass(),
						MilesAmount.of(charge));
			} else {
				milesAmounts.get(charge.getServiceClass()).update(charge);
			}
		}
		Segment sgm = new Segment(Location.of(flight.getOrigin()),
				Location.of(flight.getDestination()), new ArrayList<>(
						milesAmounts.values()));
		// System.out.println("      SGM:  " + sgm);
		return sgm;
		// new Segment(Location.of(flight.getOrigin()), Location.of(flight
		// .getDestination()), new ArrayList<>(milesAmounts.values()));
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Segment [origin=");
		builder.append(origin + System.lineSeparator());
		builder.append(",     destination=");
		builder.append(destination + System.lineSeparator());
		builder.append(", milesAmounts=");
		builder.append(milesAmounts);
		builder.append("]");
		return builder.toString();
	}

}
