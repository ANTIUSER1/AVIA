package ru.integrotech.airline.searcher;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.core.location.Airport;

import java.util.*;

/**
 * class contains logic for search routes by given parameters
 *
 * Can be used in all projects
 *
 */

public class RouteSearcher {

	public static RouteSearcher of() {
		return new RouteSearcher();
	}

	public List<Route> searchRoutes(Set<Airport> origins,
			Set<Airport> destinations, Airline airline, boolean exactLocation)
			throws Exception {

		Map<String, Route> result = new HashMap<>();

		if (origins.size() != 0 || destinations.size() != 0) {

			for (Airport origin : origins) {
				FlightGraphReader flightGraphReader = new FlightGraphReader();
				flightGraphReader.foundRoutes = result;
				flightGraphReader.origin = origin;
				flightGraphReader.destinations = destinations;
				flightGraphReader.airline = airline;
				flightGraphReader.currentPath = new Stack<>();
				flightGraphReader.visited = new HashSet<>();
				flightGraphReader.maxSegments = Route.MAX_SEGMENTS_SIZE;
				flightGraphReader.stopAtDestination = exactLocation;
				searchPath(flightGraphReader, origin);
			}
		}

		return new ArrayList<>(result.values());
	}

	private void searchPath(FlightGraphReader graphReader,
			Airport currentAirport) throws Exception {

		boolean destinationReached = !currentAirport.equals(graphReader.origin)
				&& (graphReader.destinations.contains(currentAirport) || graphReader.destinations
						.size() == 0);

		if (destinationReached) {
			String code = Route.createCode(graphReader.currentPath);
			graphReader.foundRoutes.put(code,
					Route.of(getFlightsClone(graphReader.currentPath)));
		}

		if (!(destinationReached && graphReader.stopAtDestination)
				&& (graphReader.currentPath.size() < graphReader.maxSegments)) {

			Set<Flight> foundFlights = currentAirport
					.getOutcomeFlights(graphReader.airline);

			for (Flight flight : foundFlights) {
				if (!graphReader.visited.contains(flight.getDestination())) {
					graphReader.currentPath.push(flight);
					searchPath(graphReader, flight.getDestination());
					graphReader.currentPath.pop();
				}
			}
		}
		graphReader.visited.remove(currentAirport);
	}

	private List<Flight> getFlightsClone(Collection<Flight> path) {
		List<Flight> result = new ArrayList<>();
		for (Flight flight : path) {
			result.add(Flight.copy(flight));
		}
		return result;
	}

	private static final class FlightGraphReader {
		Map<String, Route> foundRoutes;
		Airport origin;
		Collection<Airport> destinations;
		Set<Airport> visited;
		Stack<Flight> currentPath;
		Airline airline;
		int maxSegments;
		boolean stopAtDestination;
	}
}
