package ru.aeroflot.fmc.io.spend;

import ru.aeroflot.fmc.io.RoutesBuilder;
import ru.aeroflot.fmc.io.common.Location;
import ru.aeroflot.fmc.model.airline.ServiceClass;
import ru.aeroflot.fmc.model.flight.Flight;
import ru.aeroflot.fmc.model.flight.Route;
import ru.aeroflot.fmc.register.RegisterCache;
import ru.aeroflot.fmc.register.RegisterLoader;
import ru.aeroflot.fmc.searcher.BonusSearcher;
import ru.aeroflot.fmc.utils.BonusFilters;

import java.util.ArrayList;
import java.util.List;

public class SpendBuilder {

    public static SpendBuilder of(RegisterCache cache) {
        return new SpendBuilder(cache);
    }

    public static SpendBuilder of() {
        return new SpendBuilder();
    }

    private RegisterCache cache;

    private RoutesBuilder routesBuilder;

    private BonusSearcher bonusSearcher;

    private SpendBuilder(RegisterCache cache) {
        this.cache = cache;
        this.routesBuilder = RoutesBuilder.of(cache);
        this.bonusSearcher = BonusSearcher.of(cache);
    }

    private SpendBuilder() {
        RegisterLoader loader = RegisterLoader.getInstance();
        loader.lock();
        this.cache = loader.getRegisterCache();
        this.routesBuilder = RoutesBuilder.of(cache);
        this.bonusSearcher = BonusSearcher.of(cache);
        loader.release();
    }

    public List<SpendRoute> getSpendRoutes(List<Route> routes,
                                           int milesMin,
                                           int milesMax,
                                           boolean aflOnly,
                                           boolean isRoundTrip,
                                           ServiceClass.SERVICE_CLASS_TYPE serviceClassType,
                                           String award) {
        List<SpendRoute> result = new ArrayList<>();

        for (Route route : routes) {

            executeFilters(route, serviceClassType, award, milesMin, milesMax, aflOnly, isRoundTrip);
            Location via = null;

            if (route.getFlights().size() > 1) {
                via = Location.of(route.getAirports().get(ru.aeroflot.fmc.model.flight.Route.MAX_SEGMENTS_SIZE - 1));
            }

            if (aflOnly || route.isOperatesBy(ru.aeroflot.fmc.model.airline.Airline.AFL_CODE)) {
                SpendRoute bonusRoad = SpendRoute.ofAfl(route, via, milesMin, milesMax);
                if (!bonusRoad.isInvalid()) {
                    result.add(bonusRoad);
                }
            }

            if (!aflOnly && route.isOperatesWithout(ru.aeroflot.fmc.model.airline.Airline.AFL_CODE)) {
                SpendRoute bonusRoad = SpendRoute.ofScyteam(route, via, milesMin, milesMax);
                if (!bonusRoad.isInvalid()) {
                    result.add(bonusRoad);
                }
            }
        }

        return result;
    }

    public List<SpendRoute> getSpendRoutes(String from,
                                           String fromType,
                                           String to,
                                           String toType,
                                           String airlineCode,
                                           int milesMin,
                                           int milesMax,
                                           boolean aflOnly,
                                           boolean isRoundTrip,
                                           String serviceClassName,
                                           String award) {
        List<Route> routes = this.routesBuilder.getRoutes(from, fromType, to, toType, airlineCode);
        ServiceClass.SERVICE_CLASS_TYPE serviceClassType = null;
        if (serviceClassName != null) {
            serviceClassType = ServiceClass.SERVICE_CLASS_TYPE.valueOf(serviceClassName);
        }
        for (Route route : routes) {
            this.bonusSearcher.findBonuses(route, aflOnly);
        }
        return this.getSpendRoutes(routes, milesMin, milesMax,aflOnly, isRoundTrip, serviceClassType, award);
    }

    public List<SpendRoute> getSpendRoutes(SpendInput spendInput) {
        return this.getSpendRoutes(
                spendInput.getOriginCode(), // from
                spendInput.getOriginType(), // from type
                spendInput.getDestinationCode(), // to
                spendInput.getDestinationType(), // to type
                null, // airline
                spendInput.getMilesMin(), // miles min
                spendInput.getMilesMax(), // miles max
                spendInput.isOnlyAfl(),  // afl only
                spendInput.isRoundTrip(), // is round trip
                spendInput.getClassOfServiceName(), // class listOf service name
                spendInput.getAwardType() // award type
        );
    }

    public ResultMilesSpend build(SpendInput spendInput) {
        List<SpendRoute> routes = this.getSpendRoutes(spendInput);
        return ResultMilesSpend.of(routes);
    }

    private static void executeFilters(Route route,
                                       ServiceClass.SERVICE_CLASS_TYPE serviceClassType,
                                       String award,
                                       int milesMin,
                                       int milesMax,
                                       boolean aflOnly,
                                       boolean isRoundTrip) {

        if (aflOnly || route.isOperatesBy(ru.aeroflot.fmc.model.airline.Airline.AFL_CODE)) {
            BonusFilters.byInputParams(route.getAflBonuses(), serviceClassType, route.getOrigin(), award, isRoundTrip, route.isDirect());
            BonusFilters.byMilesInterval(route.getAflBonuses(), milesMin, milesMax);
            for (Flight flight : route.getFlights()) {
                BonusFilters.byInputParams(flight.getAflBonuses(), serviceClassType, flight.getOrigin(), award, isRoundTrip, false);
                BonusFilters.byMilesInterval(flight.getAflBonuses(), milesMin, milesMax);
            }
        }

        if (!aflOnly || route.isOperatesWithout(ru.aeroflot.fmc.model.airline.Airline.AFL_CODE)) {
            BonusFilters.byInputParams(route.getScyteamBonuses(), serviceClassType, route.getOrigin(), award, isRoundTrip, route.isDirect());
            BonusFilters.byMilesInterval(route.getScyteamBonuses(), milesMin, milesMax);
            for (Flight flight : route.getFlights()) {
                BonusFilters.byInputParams(flight.getScyteamBonuses(), serviceClassType, flight.getOrigin(), award, isRoundTrip, false);
                BonusFilters.byMilesInterval(flight.getScyteamBonuses(), milesMin, milesMax);
            }
        }

        BonusFilters.byCommonTypes(route);
    }



}
