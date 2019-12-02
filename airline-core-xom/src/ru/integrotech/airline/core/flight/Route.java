package ru.integrotech.airline.core.flight;



import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.core.location.City;

import java.util.*;

/*class represents route - chain listOf airports from origin to
destination with all acceptable flights listOf all airlines */
public class Route implements Comparable<Route> {

    public static final int MAX_SEGMENTS_SIZE = 2; // Current requirement

    public static Route of(Collection<Flight> flights) {
        return new Route(new ArrayList<>(flights));
    }

    public static Route of(String code, List<Flight> flights) {
        return new Route(code, flights);
    }

    public static String createCode(List<Flight> flights) {
        StringBuilder sb = new StringBuilder();
        for (Flight flight : flights) {
            sb.append(flight.getOrigin().getCode()).append(" ");
        }
        sb.append(flights.get(flights.size() - 1).getDestination().getCode());
        return sb.toString();
    }

    private final String code;

    private final List<Flight> flights;

    private Set<Bonus> aflBonuses;

    private Set<Bonus> scyteamBonuses;

    private Route(List<Flight> flights) {
        this.code = Route.createCode(flights);
        this.flights = new ArrayList<>();
        this.flights.addAll(flights);
        this.aflBonuses = new HashSet<>();
        this.scyteamBonuses = new HashSet<>();
    }

    private Route(String code, List<Flight> flights) {
        this.code = code;
        this.flights = new ArrayList<>();
        if (!flights.isEmpty()) {
            this.flights.addAll(flights);
        }
        this.aflBonuses = new HashSet<>();
        this.scyteamBonuses = new HashSet<>();
    }

    public String getCode() {
        return code;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public Set<Bonus> getAflBonuses() {
        return aflBonuses;
    }

    public void setAflBonuses(Set<Bonus> aflBonuses) {
        this.aflBonuses = aflBonuses;
    }

    public Set<Bonus> getScyteamBonuses() {
        return scyteamBonuses;
    }

    public void setScyteamBonuses(Set<Bonus> scyteamBonuses) {
        this.scyteamBonuses = scyteamBonuses;
    }

    public List<Flight> getFlights(Airline airline) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : this.flights) {
            if (flight.getCarriers().containsKey(airline)) {
                result.add(flight);
            }
        }
        return result;
    }

    public Airport getOrigin() {
        return this.flights.get(0).getOrigin();
    }

    public Airport getDestination() {
        return this.flights.get(this.flights.size() - 1).getDestination();
    }

    public int getDistance() {
        int result = 0;
        for (Flight flight : this.flights) {
            result += flight.getDistance();
        }
        return result;
    }

    public Set<Airline> getAirlines() {
        Set<Airline> result = new HashSet<>();
        for (Flight flight : this.flights) {
             result.addAll(flight.getCarriers().keySet());
        }
        return result;
    }

    public Set<String> getAirlineCodes() {
        Set<String> result = new HashSet<>();
        for (Flight flight : this.flights) {
            for (Airline airline : flight.getCarriers().keySet()) {
                result.add(airline.getCode());
            }
        }
        return result;
    }

    public List<String> getAirportCodes() {
        List<String> result = new ArrayList<>();
        for (Flight flight : this.flights) {
            result.add(flight.getOrigin().getCode());
        }
        result.add(this.getDestination().getCode());
        return result;
    }

    public List<Airport> getAirports() {
        List<Airport> result = new ArrayList<>();
        for (Flight flight : this.flights) {
            result.add(flight.getOrigin());
        }
        result.add(this.getDestination());
        return result;
    }

    /*if route can be completed fully by given airline return true*/
    public boolean isOperatesBy(Airline airline) {
        return this.getFlights(airline).size() == this.flights.size();
    }

    /*if route can be completed fully by given airline return true*/
    public boolean isOperatesBy(String airlineCode) {
        segment: for (Flight flight : this.flights) {
            for (Airline airline : flight.getCarriers().keySet()) {
                if (airline.getCode().equals(airlineCode)) continue segment;
            }
            return false;
        }
        return true;
    }

    /*if route can be completed fully only with others airlines with given airline return true*/
    public boolean otherAirlinesIsPresent() {
        for (Flight flight : this.flights) {
            if (flight.getCarriers().values().size() > 1) return true;
        }
        return false;
    }

    /*if route can be completed fully only with others airlines with given airline return true*/
    public boolean otherAirlinesIsPresent(String airlineCode) {
        return this.countFlights(airlineCode) < this.flights.size();
    }

    private int countFlights(String airlineCode) {
        int counter = 0;
        Set<Airline> airlines;
        for (Flight flight : this.flights) {
            airlines = flight.getCarriers().keySet();
            if (airlines.size() > 1) {
                counter++;
            } else {
                for (Airline airline : airlines) {
                    if (!airline.getCode().equals(airlineCode)) counter++;
                }
            }
        }
        return counter;
    }

    private int countFlights(Airline airline) {
        int counter = 0;
        for (Flight flight : this.flights) {
            if (flight.getCarriers().get(airline) != null) counter++;
        }
        return counter;
    }

    public String getAflZones() {
        StringBuilder sb = new StringBuilder();
        for (Flight flight : this.flights) {
            sb.append(flight.getOrigin().getAflZone());
        }
        sb.append(this.getDestination().getAflZone());
        sb.append("A");
        return sb.toString();
    }

    public String getAflReverseZones() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getDestination().getAflZone());
        for (int i = this.flights.size() - 1; i >= 0; i--) {
            sb.append(this.flights.get(i).getOrigin().getAflZone());
        }
        sb.append("A");
        return sb.toString();
    }

    public String getScyteamZones() {
        StringBuilder sb = new StringBuilder();
        for (Flight flight : this.flights) {
            sb.append(flight.getOrigin().getScyteamZone());
        }
        sb.append(this.getDestination().getScyteamZone());
        sb.append("S");
        return sb.toString();
    }

    public String getScyteamReverseZones() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getDestination().getScyteamZone());
        for (int i = this.flights.size() - 1; i >= 0; i--) {
            sb.append(this.flights.get(i).getOrigin().getScyteamZone());
        }
        sb.append("S");
        return sb.toString();
    }

    public String getCityCodes() {
        StringBuilder sb = new StringBuilder();
        for (Flight flight : this.flights) {
            sb.append(flight.getOrigin().getCity().getCode());
        }
        sb.append(this.getDestination().getCity().getCode());
        return sb.toString();
    }

    public List<City> getCities() {
        List<City> result = new ArrayList<>();
        for (Flight flight : this.flights) {
            result.add(flight.getOrigin().getCity());
        }
        result.add(this.getDestination().getCity());
        return result;
    }

    public boolean isDirect() {
        return this.flights.size() == 1;
    }

    public List<ServiceClass.SERVICE_CLASS_TYPE> getAllowedClasses(Airline airline) {
        List<Flight> flights = this.getFlights(airline);
        Set<ServiceClass.SERVICE_CLASS_TYPE> commonTypes = new HashSet<>(flights.get(0).getAllowedClasses(airline));
        for (int i = 1; i < flights.size(); i++) {
            Set<ServiceClass.SERVICE_CLASS_TYPE> types = new HashSet<>(flights.get(i).getAllowedClasses(airline));
            commonTypes.removeIf(commonType -> !types.contains(commonType));
        }
        return new ArrayList<>(commonTypes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(code, route.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public int compareTo(Route o) {
        int destinationDiff = this.getDestination().getCity().getWeight() - o.getDestination().getCity().getWeight();
        if (destinationDiff != 0) return destinationDiff;
        return this.getDistance() - o.getDistance();
    }

    @Override
    public String toString() {
        return String.format("Route:  %-13s %,8d mi     afl: %7s, %7s     skyteam: %7s, %7s %18s ",
                this.code,
                this.getDistance(),
                this.getAflZones(),
                this.getAflReverseZones(),
                this.getScyteamZones(),
                this.getScyteamReverseZones(),
                this.getAirlineCodes());
    }

}
