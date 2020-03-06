package ru.integrotech.su.outputparams.route;


import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.searcher.RouteSearcher;
import ru.integrotech.su.inputparams.InputParamsTransformer;
import ru.integrotech.su.inputparams.route.RoutesInput;

import java.util.List;
import java.util.Set;

public class RoutesBuilder {

    public static String[] getRegisterNames() {
        return REGISTER_NAMES;
    }

    private static final String[] REGISTER_NAMES = new String[]
                    {"airline",
                    "region",
                    "country",
                    "city",
                    "airport",
                    "pair",
                    "serviceClassLimit",
                    "bonusRoute"};

    public static RoutesBuilder of(RegisterCache cache) {
        return new RoutesBuilder(cache);
    }

    private RegisterCache cache;

    private InputParamsTransformer paramsTransformer;

    private RouteSearcher routeSearcher;

    private RoutesBuilder(RegisterCache cache) {
        this.cache = cache;
        this.paramsTransformer = InputParamsTransformer.of(cache);
        this.routeSearcher = RouteSearcher.of();
    }

    public List<Route> getRoutes(RoutesInput routesInput) {
        Set<Airport> origins = this.paramsTransformer.getOrigins(routesInput);
        Set<Airport> destinations = this.paramsTransformer.getDestinations(routesInput);
        boolean exactLocation = this.paramsTransformer.exactLocation(routesInput);
        Airline airline = this.paramsTransformer.getAirline(routesInput);
        List<Route> result = this.routeSearcher.searchRoutes(origins, destinations, airline, exactLocation);
        for (Route route : result) {
            if (!route.isDirect()) {
                route.setWrong(this.cache.getWrongRouteMap().containsKey(route.getCityCodes()));
            }
        }
        return result;
    }
}
