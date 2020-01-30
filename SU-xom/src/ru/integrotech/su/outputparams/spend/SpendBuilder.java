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
import java.util.Iterator;
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

    /* method for use in ODM in spendRuleFlow
     * contains program and business logic */
    public ResultMilesSpend buildResult(SpendInput spendInput) {
        List<SpendRoute> routes = this.getSpendRoutes(spendInput);
        return ResultMilesSpend.of(routes);
    }

    /* method for use in ODM in spendJavaFlow
     * contains program logic only*/
    public ResultMilesSpend buildResult(List<Route> routes, SpendInput spendInput) {
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
        this.executeAllFilters(routes, spendInput);
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

    public List<SpendRoute> buildSpendRoutes(List<Route> routes, SpendInput spendInput) {

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
    

    /* this is a business logic, it should works by ODM rules 
     * or calls from ODM rules */
    public void executeAllFilters(List<Route> routes, SpendInput spendInput) {
        for (Route route : routes) {
            this.executeAllFilters(route, spendInput);
        }
    }
    
    /* this is a business logic, it should works by ODM rules 
     * or calls from ODM rules */
    public void executeFiltersByInputParams(List<Route> routes, SpendInput spendInput) {
        for (Route route : routes) {
            this.executeFiltersByInputParams(route, spendInput);
        }
    }
    
    /* this is a business logic, it should works by ODM rules 
     * or calls from ODM rules */
    public void executeFiltersByMilesInterval(List<Route> routes, SpendInput spendInput) {
        for (Route route : routes) {
             this.executeFiltersByMilesInterval(route, spendInput);
        }
    }
    
    /* this is a business logic, it should works by ODM rules 
     * or calls from ODM rules */
    public void executeFiltersByCommonTypes(List<Route> routes, SpendInput spendInput) {
        for (Route route : routes) {
        	BonusFilters.byCommonTypes(route);
        }
    }
    
   
    /* this is a business logic, it should works by ODM rules 
     * or calls from ODM rules */
    private void executeAllFilters(Route route, SpendInput spendInput) {

        int milesMin = spendInput.getMilesInterval().getMilesMin();
        int milesMax = spendInput.getMilesInterval().getMilesMax();
        boolean aflOnly = spendInput.getIsOnlyAfl();
        boolean isRoundTrip = spendInput.getIsRoundTrip();
        ServiceClass.SERVICE_CLASS_TYPE serviceClassType = this.getServiceClassType(spendInput);
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
    
    /* this is a business logic, it should works by ODM rules 
     * or calls from ODM rules */
    private void executeFiltersByInputParams(Route route, SpendInput spendInput) {

        boolean aflOnly = spendInput.getIsOnlyAfl();
        boolean isRoundTrip = spendInput.getIsRoundTrip();
        ServiceClass.SERVICE_CLASS_TYPE serviceClassType = this.getServiceClassType(spendInput);
        String award = spendInput.getAwardType();

        if (route.isOperatesBy(this.afl)) {
            BonusFilters.byInputParams(route.getAflBonuses(), serviceClassType, route.getOrigin(), award, isRoundTrip, route.isDirect());
             for (Flight flight : route.getFlights()) {
                  BonusFilters.byInputParams(flight.getAflBonuses(), serviceClassType, flight.getOrigin(), award, isRoundTrip, false);
            }
        }

        if (!aflOnly && route.otherAirlinesIsPresent(this.afl)) {
            BonusFilters.byInputParams(route.getScyteamBonuses(), serviceClassType, route.getOrigin(), award, isRoundTrip, route.isDirect());
            for (Flight flight : route.getFlights()) {
                BonusFilters.byInputParams(flight.getScyteamBonuses(), serviceClassType, flight.getOrigin(), award, isRoundTrip, false);
            }
        }
   }
    
    /* this is a business logic, it should works by ODM rules 
     * or calls from ODM rules */
    private void executeFiltersByMilesInterval(Route route, SpendInput spendInput) {

        int milesMin = spendInput.getMilesInterval().getMilesMin();
        int milesMax = spendInput.getMilesInterval().getMilesMax();
        boolean aflOnly = spendInput.getIsOnlyAfl();
        if (route.isOperatesBy(this.afl)) {
             BonusFilters.byMilesInterval(route.getAflBonuses(), milesMin, milesMax);
            for (Flight flight : route.getFlights()) {
                BonusFilters.byMilesInterval(flight.getAflBonuses(), milesMin, milesMax);
            }
        }

        if (!aflOnly && route.otherAirlinesIsPresent(this.afl)) {
            BonusFilters.byMilesInterval(route.getScyteamBonuses(), milesMin, milesMax);
            for (Flight flight : route.getFlights()) {
               BonusFilters.byMilesInterval(flight.getScyteamBonuses(), milesMin, milesMax);
            }
        }
    }
    
     
    public ServiceClass.SERVICE_CLASS_TYPE getServiceClassType(SpendInput spendInput) {
    	
    	String classOfServiceName = null;
    	ServiceClass.SERVICE_CLASS_TYPE serviceClassType = null;
    	
        if (spendInput.getClassOfService() != null) {
        	classOfServiceName = spendInput.getClassOfService().getClassOfServiceName();
        }
        
        if (classOfServiceName != null) {
            serviceClassType = ServiceClass.SERVICE_CLASS_TYPE.valueOf(classOfServiceName);
        }

        return serviceClassType;
    }

    /* this is a business logic, it should works by ODM rules 
     * or calls from ODM rules */
    public void updateFitsMilesIntervals(List<Route> routes, SpendInput spendInput) {

        int milesMin = spendInput.getMilesInterval().getMilesMin();
        int milesMax = spendInput.getMilesInterval().getMilesMax();

        for (Route route : routes) {
            this.updateFitsMilesInterval(route, milesMin, milesMax);
        }
    }

    /* this is a business logic, it should works by ODM rules 
     * or calls from ODM rules */
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

    /* this is a business logic, it should works by ODM rules 
     * or calls from ODM rules */
    private void updateFitsMilesInterval(Flight flight, int milesMin, int milesMax) {
        for (Bonus bonus : flight.getAflBonuses()) {
            bonus.setFitsMilesInterval(milesMin, milesMax);
        }

        for (Bonus bonus : flight.getScyteamBonuses()) {
            bonus.setFitsMilesInterval(milesMin, milesMax);
        }
    }

    /* this is a business logic, it should works by ODM rules 
     * or calls from ODM rules */
    public void bonusSummation(List<Route> routes) {
        for (Route route : routes) {
            this.aflBonusSummation(route);
        }
    }

    private void aflBonusSummation(Route route) {
        if (route.getAflBonuses().size() > 0 || route.getFlights().size() < 2) return;
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
