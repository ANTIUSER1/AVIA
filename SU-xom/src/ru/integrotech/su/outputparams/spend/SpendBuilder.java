package ru.integrotech.su.outputparams.spend;


import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.searcher.BonusSearcher;
import ru.integrotech.airline.utils.BonusFilters;
import ru.integrotech.su.inputparams.route.RoutesInput;
import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.outputparams.route.RoutesBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* this class takes input params (SpendInput) and returns back output params (ResultMilesSpend)
 * logic is:
 *
 * var 1: 1.ResultMilesSpend result = buildResult(SpendInput spendInput)
 *
 * var 2: 1.List<Route> routes = getRoutes(SpendInput spendInput)
 *        2.ODM rules with(routes, milesMin, milesMax) - instead of method
 *          updateFitsMilesIntervals(routes, milesMin, milesMax)
 *          in this class
 *        3.ResultMilesSpend result = buildResult(List<Route> routes, SpendInput spendInput)
 */
public class SpendBuilder {

    public static SpendBuilder of(RegisterCache cache) {
        return new SpendBuilder(cache);
    }

    private RegisterCache cache;

    private RoutesBuilder routesBuilder;

    private BonusSearcher bonusSearcher;

    private final Airline afl;


    private SpendBuilder(RegisterCache cache) {
        this.cache = cache;
        this.routesBuilder = RoutesBuilder.of(cache);
        this.bonusSearcher = BonusSearcher.of(cache);
        this.afl = this.cache.getAirline(Airline.AFL_CODE);
    }

    /*method for use in ODM*/
    public ResultMilesSpend buildResult(SpendInput spendInput) {
        List<SpendRoute> routes = this.getSpendRoutes(spendInput);
        return ResultMilesSpend.of(routes);
    }

    /*method for use in ODM*/
    public ResultMilesSpend buildResult(List<Route> routes, SpendInput spendInput) {
        this.executeFilters(routes, spendInput);
        this.bonusSummation(routes);
        List<SpendRoute> spendRoutes = this.buildSpendRoutes(routes, spendInput);
        return ResultMilesSpend.of(spendRoutes);
    }

    /*method for use in TESTS*/
    public List<SpendRoute> getSpendRoutes(SpendInput spendInput) {
        return this.getSpendRoutes(spendInput, null);
    }

    /*method for use in TESTS*/
    public List<SpendRoute> getSpendRoutes(SpendInput spendInput, String airlineCode) {

        List<Route> routes = this.getRoutes(spendInput, airlineCode);
        this.executeFilters(routes, spendInput);
        this.bonusSummation(routes);
        this.updateFitsMilesIntervals(routes, spendInput);
        return this.buildSpendRoutes(routes, spendInput);
    }

    public List<Route> getRoutes(SpendInput spendInput) {

        return this.getRoutes(spendInput, null);
    }

    public List<Route> getRoutes(SpendInput spendInput, String airlineCode) {

        RoutesInput routesInput = RoutesInput.of(spendInput, airlineCode);
        List<Route> routes = this.routesBuilder.getRoutes(routesInput);

        for (Route route : routes) {
            this.bonusSearcher.findBonuses(route, spendInput.getIsOnlyAfl());
        }

        return routes;
    }

    List<SpendRoute> buildSpendRoutes(List<Route> routes, SpendInput spendInput) {

        boolean aflOnly = spendInput.getIsOnlyAfl();
        List<SpendRoute> result = new ArrayList<>();

        for (Route route : routes) {
            if (route.isOperatesBy(this.afl)) {
                SpendRoute bonusRoad = SpendRoute.ofAfl(route);
                if (!bonusRoad.isInvalid()) {
                    result.add(bonusRoad);
                }
            }

            if (!aflOnly && route.otherAirlinesIsPresent(this.afl)) {
                SpendRoute bonusRoad = SpendRoute.ofScyteam(route);
                if (!bonusRoad.isInvalid()) {
                    result.add(bonusRoad);
                }
            }
        }

        return result;
    }

    void executeFilters(List<Route> routes, SpendInput spendInput) {
        for (Route route : routes) {
            this.executeFilters(route, spendInput);
        }
    }

    private void executeFilters(Route route, SpendInput spendInput) {

        int milesMin = spendInput.getMilesInterval().getMilesMin();
        int milesMax = spendInput.getMilesInterval().getMilesMax();
        boolean aflOnly = spendInput.getIsOnlyAfl();
        boolean isRoundTrip = spendInput.getIsRoundTrip();
        ServiceClass.SERVICE_CLASS_TYPE serviceClassType = this.getServiceClassType(this.getClassOfServiceName(spendInput));
        String award = spendInput.getAwardType();

        if (route.isOperatesBy(this.afl)) {
            BonusFilters.byInputParams(route.getAflBonuses(), serviceClassType, route.getOrigin(), award, isRoundTrip, route.isDirect());
            BonusFilters.byMilesInterval(route.getAflBonuses(), milesMin, milesMax);
            for (Flight flight : route.getFlights()) {
                BonusFilters.byInputParams(flight.getAflBonuses(), serviceClassType, flight.getOrigin(), award, isRoundTrip, false);
                BonusFilters.byMilesInterval(flight.getAflBonuses(), milesMin, milesMax);
            }
        }

        if (!aflOnly && route.otherAirlinesIsPresent(this.afl)) {
            BonusFilters.byInputParams(route.getScyteamBonuses(), serviceClassType, route.getOrigin(), award, isRoundTrip, route.isDirect());
            BonusFilters.byMilesInterval(route.getScyteamBonuses(), milesMin, milesMax);
            for (Flight flight : route.getFlights()) {
                BonusFilters.byInputParams(flight.getScyteamBonuses(), serviceClassType, flight.getOrigin(), award, isRoundTrip, false);
                BonusFilters.byMilesInterval(flight.getScyteamBonuses(), milesMin, milesMax);
            }
        }

        BonusFilters.byCommonTypes(route);
    }

    private ServiceClass.SERVICE_CLASS_TYPE getServiceClassType(String serviceClassName) {
        ServiceClass.SERVICE_CLASS_TYPE serviceClassType = null;

        if (serviceClassName != null) {
            serviceClassType = ServiceClass.SERVICE_CLASS_TYPE.valueOf(serviceClassName);
        }

        return serviceClassType;
    }

    public void updateFitsMilesIntervals(List<Route> routes, SpendInput spendInput) {

        int milesMin = spendInput.getMilesInterval().getMilesMin();
        int milesMax = spendInput.getMilesInterval().getMilesMax();

        for (Route route : routes) {
            this.updateFitsMilesInterval(route, milesMin, milesMax);
        }
    }

    private void updateFitsMilesInterval(Route route, int milesMin, int milesMax) {
        for (Bonus bonus : route.getAflBonuses()) {
            bonus.setFitsMilesInterval(milesMin, milesMax);
        }

        for (Bonus bonus : route.getScyteamBonuses()) {
            bonus.setFitsMilesInterval(milesMin, milesMax);
        }

        for (Flight flight : route.getFlights()) {
            this.updateFitsMilesInterval(flight, milesMin, milesMax);
        }
    }

    private void updateFitsMilesInterval(Flight flight, int milesMin, int milesMax) {
        for (Bonus bonus : flight.getAflBonuses()) {
            bonus.setFitsMilesInterval(milesMin, milesMax);
        }

        for (Bonus bonus : flight.getScyteamBonuses()) {
            bonus.setFitsMilesInterval(milesMin, milesMax);
        }
    }

    private String getClassOfServiceName(SpendInput spendInput) {
        String result = null;
        if (spendInput.getClassOfService() != null) {
            result = spendInput.getClassOfService().getClassOfServiceName();
        }
        return result;
    }

    public void bonusSummation(List<Route> routes) {
        for (Route route : routes) {
            this.aflBonusSummation(route);
        }
    }

    private void aflBonusSummation(Route route) {
        if (route.getFlights().size() < 2) return;
        Set<Bonus> bonuses1 = route.getFlights().get(0).getAflBonuses();
        Set<Bonus> bonuses2 = route.getFlights().get(1).getAflBonuses();
        Set<Bonus> newBonuses = new HashSet<>();
        if (bonuses1.size() == bonuses2.size()) {
            for (Bonus bonus1 : bonuses1) {
                for (Bonus bonus2 : bonuses2) {
                    if (bonus1.equalsIgnoreValue(bonus2)) {
                        Bonus newBonus = Bonus.of(bonus1.getType().name(),
                                bonus1.getServiceClass(),
                                bonus1.getUpgradeServiceClass(),
                                bonus1.getValue() + bonus2.getValue(),
                                bonus1.isLight(),
                                bonus1.getValidFrom(),
                                bonus1.getValidTo());
                        newBonuses.add(newBonus);
                    }
                }
            }
        }
        if (newBonuses.size() == bonuses1.size()) {
            route.setAflBonuses(newBonuses);
            bonuses1.clear();
            bonuses2.clear();
            route.setBonusSummation(true);
        }
    }

}
