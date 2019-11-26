package ru.integrotech.su.outputparams.spend;


import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.su.inputparams.spend.SpendInput;

import java.util.*;

/* this class takes input params (SpendInput) and returns back output params (ResultMilesSpendLk)
 * logic is:
 *
 * var 1: 1.ResultMilesSpendLk result = buildResult(SpendInput spendInput)
 *
 * var 2: 1.List<Route> routes = getRoutes(SpendInput spendInput)
 *        2.ODM rules with(routes, milesMin, milesMax) - instead of method
 *          updateFitsMilesIntervals(routes, milesMin, milesMax)
 *          in SpendBuilder class
 *        3.ResultMilesSpendLk result = buildResult(List<Route> routes, SpendInput spendInput)
 */

public class SpendLkBuilder {

    public static SpendLkBuilder of(SpendBuilder spendBuilder) {
        return new SpendLkBuilder(spendBuilder);
    }

    private SpendBuilder spendBuilder;

    private SpendLkBuilder(SpendBuilder spendBuilder) {
        this.spendBuilder = spendBuilder;
    }

    /*method for use in ODM*/
    public ResultMilesSpendLk buildResult(SpendInput spendInput) {
        List<SpendLkRoute> routes = this.getSpendLkRoutes(spendInput);
        return ResultMilesSpendLk.of(routes);
    }

    /*method for use in ODM*/
    public ResultMilesSpendLk buildResult(List<Route> routes, SpendInput spendInput) {
        this.spendBuilder.executeFilters(routes, spendInput);
        List<SpendRoute> spendRoutes = this.spendBuilder.buildSpendRoutes(routes, spendInput);
        List<SpendLkRoute> spendLkRoutes = this.buildSpendLkRoutes(spendRoutes, spendInput);
        return ResultMilesSpendLk.of(spendLkRoutes);
    }

    /*method for use in TESTS*/
    public List<SpendLkRoute> getSpendLkRoutes(SpendInput spendInput) {
        return this.getSpendLkRoutes(spendInput, null);
    }

    /*method for use in TESTS*/
    public List<SpendLkRoute> getSpendLkRoutes(SpendInput spendInput, String airlineCode) {

        spendInput.setOnlyAfl(true);

        List<Route> routes = this.getRoutes(spendInput);
        this.spendBuilder.executeFilters(routes, spendInput);
        this.spendBuilder.updateFitsMilesIntervals(routes, spendInput);
        this.replaceUO(routes);

        List<SpendRoute> spendRoutes = this.spendBuilder.buildSpendRoutes(routes, spendInput);

        return this.buildSpendLkRoutes(spendRoutes, spendInput);
    }

    public List<Route> getRoutes(SpendInput spendInput) {
        return this.spendBuilder.getRoutes(spendInput, null);
    }

    private List<SpendLkRoute> buildSpendLkRoutes(List<SpendRoute> spendRoutes, SpendInput spendInput) {

        Map<String, SpendLkRoute> resultMap = new HashMap<>();
        Map<String, Map<String, RequiredAward>> routeMap = this.createRouteMap(spendRoutes);

        for (SpendRoute spendRoute : spendRoutes) {
            if (!resultMap.containsKey(spendRoute.getKey())) {
                resultMap.put(spendRoute.getKey(), SpendLkRoute.of(spendRoute));
            }
        }

        for (Map.Entry<String, SpendLkRoute> entry : resultMap.entrySet()) {
            List<RequiredAward> awardList = new ArrayList<>(routeMap.get(entry.getKey()).values());
            Collections.sort(awardList);
            entry.getValue().getRequiredAwards().addAll(awardList);
        }

        List<SpendLkRoute> result = new ArrayList<>(resultMap.values());

        int milesMin = spendInput.getMilesInterval().getMilesMin();
        int milesMax = spendInput.getMilesInterval().getMilesMax();

        for (SpendLkRoute spendLkRoute : result) {
            spendLkRoute.updateFitsMilesIntervals(milesMin, milesMax);
        }

        Collections.sort(result);

        return result;
    }

    private Map<String, Map<String, RequiredAward>> createRouteMap(List<SpendRoute> spendRoutes) {
        Map<String, Map<String, RequiredAward>> result = new HashMap<>();
        for (SpendRoute spendRoute : spendRoutes) {
            if (result.containsKey(spendRoute.getKey())) {
                Map<String, RequiredAward> awardMap = result.get(spendRoute.getKey());
                Map<String, RequiredAward> updateMap = this.createAwardMap(spendRoute);
                this.updateAwardMap(awardMap, updateMap);
            } else {
                result.put(spendRoute.getKey(), this.createAwardMap(spendRoute));
            }
        }
        return result;
    }

    private Map<String, RequiredAward> createAwardMap(SpendRoute spendRoute) {
        Map<String, RequiredAward> result = new HashMap<>();
        for (MileCost mileCost : spendRoute.getMileCosts()) {
            Map<String, RequiredAward> transitionalResult = new HashMap<>();
            for (RequiredAward award : mileCost.getRequiredAwards()) {
                String key = award.awardKey();
                if (transitionalResult.containsKey(key)) {
                    RequiredAward savedAward = transitionalResult.get(key);
                    if (award.compareTo(savedAward) < 0) {
                        transitionalResult.put(key, award);
                    }
                } else {
                    transitionalResult.put(key, award);
                }
            }
            for (Map.Entry<String, RequiredAward> entry : transitionalResult.entrySet()) {
                String key = entry.getKey();
                RequiredAward award = entry.getValue();
                if (result.containsKey(key)) {
                    result.get(key).setValue(result.get(key).getValue() + award.getValue());
                } else {
                    result.put(key, award);
                }
            }
        }
        return result;
    }

    private void updateAwardMap(Map<String, RequiredAward> awardMap, Map<String, RequiredAward> updateMap) {
        for (String key : updateMap.keySet()) {
            if (awardMap.containsKey(key)) {
                RequiredAward savedAward = awardMap.get(key);
                RequiredAward newAward = updateMap.get(key);
                if (newAward.compareTo(savedAward) < 0) {
                    awardMap.put(key, newAward);
                }
            } else {
                RequiredAward newAward = updateMap.get(key);
                awardMap.put(key, newAward);
            }
        }
    }

    private void replaceUO(List<Route> routes) {
        for (Route route : routes) {
            this.replaceUO(route.getAflBonuses());
            this.replaceUO(route.getScyteamBonuses());
            for (Flight flight : route.getFlights()) {
                this.replaceUO(flight.getAflBonuses());
                this.replaceUO(flight.getScyteamBonuses());
            }
        }
    }

    private void replaceUO(Set<Bonus> bonuses) {
        for (Bonus bonus : bonuses) {
            if (bonus.getType().equals(Bonus.BONUS_TYPE.UO)) {
                bonus.setType(Bonus.BONUS_TYPE.UC);
            }
        }
    }
}
