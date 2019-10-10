package ru.aeroflot.fmc.io;

import ru.aeroflot.fmc.model.airline.Airline;
import ru.aeroflot.fmc.model.flight.Route;
import ru.aeroflot.fmc.model.location.Airport;
import ru.aeroflot.fmc.register.RegisterCache;
import ru.aeroflot.fmc.searcher.RouteSearcher;
import ru.aeroflot.fmc.utils.InputParamsTransformer;

import java.util.List;
import java.util.Set;

public class RoutesBuilder {

    public static RoutesBuilder of(RegisterCache cache) {
        return new RoutesBuilder(cache);
    }

    private RegisterCache cache;

    private InputParamsTransformer inputParamsTransformer;

    private RouteSearcher routeSearcher;

    private RoutesBuilder(RegisterCache cache) {
        this.cache = cache;
        this.inputParamsTransformer = InputParamsTransformer.of(cache);
        this.routeSearcher = RouteSearcher.of();
    }

    public List<Route> getRoutes(String from,
                                 String fromType,
                                 String to,
                                 String toType,
                                 String airlineCode) {

        Set<Airport> departures = this.inputParamsTransformer.getEndpoints(from, fromType);
        Set<Airport> arrivals = this.inputParamsTransformer.getEndpoints(to, toType);
        boolean exactLocation = this.inputParamsTransformer.exactLocation(to, toType);
        Airline airline = this.inputParamsTransformer.getAirline(airlineCode);
        return this.routeSearcher.searchRoutes(departures, arrivals, airline, exactLocation);
    }
}
