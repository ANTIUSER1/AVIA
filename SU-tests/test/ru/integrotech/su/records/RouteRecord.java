package ru.integrotech.su.records;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.register.RegisterCache;

import java.util.*;

/*class use only in test for transform JSON into model.flight.ChargeRoute*/
public class RouteRecord {

    public static RouteRecord of(Route route) {
        Flight[] flights = new Flight[route.getFlights().size()];
        int counter = 0;
        for (ru.integrotech.airline.core.flight.Flight flight : route.getFlights()) {
            flights[counter++] = Flight.of(flight);
        }
        return new RouteRecord(route.getCode(), route.getDistance(), flights);
    }


    private String code;

    private int distance;

    private Flight[] flights;

    private RouteRecord(String code, int distance, Flight[] flights) {
        this.code = code;
        this.distance = distance;
        this.flights = flights;
    }

    private Set<String> getOperatorsCodes() {
        Map<String, Integer> codesCounter = new HashMap<>();
        for (Flight flight : this.flights) {
            for (Flight.FlightCarrier carrier : flight.carriers) {
                String key = carrier.carrier;
                if (codesCounter.containsKey(key)) {
                    Integer nevValue = codesCounter.get(key) + 1;
                    codesCounter.put(key, nevValue);
                } else {
                    codesCounter.put(key, 1);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : codesCounter.entrySet()) {
            if (entry.getValue() != this.flights.length) {
                codesCounter.remove(entry.getKey());
            }
        }

        return codesCounter.keySet();
    }

    public Route toRoute(RegisterCache registerCache) {
        List<ru.integrotech.airline.core.flight.Flight> flights = new ArrayList<>();
        for (Flight flightRecord : this.flights) {
           flights.add(flightRecord.toModelFlight(registerCache));
        }
        return Route.of(this.code, flights);
    }

    private static class Flight {

        private static Flight of(ru.integrotech.airline.core.flight.Flight flight) {
            String origin = flight.getOrigin().getCode();
            String destination = flight.getDestination().getCode();
            FlightCarrier[] carriers = new FlightCarrier[flight.getCarriers().values().size()];
            int counter = 0;
            for (ru.integrotech.airline.core.flight.FlightCarrier carrier : flight.getCarriers().values()) {
                carriers[counter++] = FlightCarrier.of(carrier);
            }
            return new Flight(origin, destination, flight.getDistance(), carriers);
        }

        private String origin;

        private String destination;

        private int distance;

        private FlightCarrier[] carriers;

        private Flight(String origin, String destination, int distance, FlightCarrier[] carriers) {
            this.origin = origin;
            this.destination = destination;
            this.distance = distance;
            this.carriers = carriers;
        }

        private ru.integrotech.airline.core.flight.Flight toModelFlight(RegisterCache registerCache) {
            Airport from = registerCache.getAirport(this.origin);
            Airport to = registerCache.getAirport(this.destination);
            List<ru.integrotech.airline.core.flight.FlightCarrier> flights = new ArrayList<>();
            for (FlightCarrier record : this.carriers) {
                flights.add(record.toModelFlightCarrier(registerCache));
            }
            return ru.integrotech.airline.core.flight.Flight.of(from, to, this.distance, flights);
        }

        private static class FlightCarrier {

            private String carrier;

            private String[] serviceClasses;

            private FlightCarrier(String carrier, String[] serviceClasses) {
                this.carrier = carrier;
                this.serviceClasses = serviceClasses;
            }

            private static FlightCarrier of(ru.integrotech.airline.core.flight.FlightCarrier carrier) {
               String[] extraClasses = new String[carrier.getAllowedClasses().size()];
                int counter = 0;
                for (ServiceClass.SERVICE_CLASS_TYPE TYPE : carrier.getAllowedClasses()) {
                   extraClasses[counter++] = TYPE.name();
               }
               return new FlightCarrier(carrier.getCarrier().getCode(), extraClasses);
            }

            private ru.integrotech.airline.core.flight.FlightCarrier toModelFlightCarrier(RegisterCache registerCache) {

                Airline carrier = registerCache.getAirline(this.carrier);

                Set<ServiceClass.SERVICE_CLASS_TYPE> classRestrictions = new HashSet<>();
                for (String serviceClass : this.serviceClasses) {
                    classRestrictions.add(ServiceClass.SERVICE_CLASS_TYPE.valueOf(serviceClass));
                }

                return ru.integrotech.airline.core.flight.FlightCarrier.of(carrier, classRestrictions);
            }
        }
    }
}
