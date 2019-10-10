package ru.aeroflot.fmc.io.spend;

import ru.aeroflot.fmc.model.airline.ServiceClass;

import java.util.*;

public class SpendLkBuilder {

    public static SpendLkBuilder of(SpendBuilder spendBuilder) {
        return new SpendLkBuilder(spendBuilder);
    }

    public static SpendLkBuilder of() {
        return new SpendLkBuilder();
    }

    private SpendBuilder spendBuilder;

    private SpendLkBuilder(SpendBuilder spendBuilder) {
        this.spendBuilder = spendBuilder;
    }

    private SpendLkBuilder() {
        this.spendBuilder = SpendBuilder.of();
    }

    public List<SpendLkRoute> getSpendLkRoutes(List<ru.aeroflot.fmc.model.flight.Route> routes,
                                               int milesMin,
                                               int milesMax,
                                               boolean isRoundTrip,
                                               ServiceClass.SERVICE_CLASS_TYPE serviceClassType,
                                               String award) {

        List<SpendRoute> spendRoutes = this.spendBuilder.getSpendRoutes(routes,
                milesMin,
                milesMax,
                true,
                isRoundTrip,
                serviceClassType,
                award);
        return getSpendLkRoutes(spendRoutes, milesMin, milesMax);
    }

    private List<SpendLkRoute> getSpendLkRoutes(List<SpendRoute> spendRoutes, int milesMin, int milesMax) {

        Map<String, SpendLkRoute> resultMap = new HashMap<>();
        this.replaceUO(spendRoutes);
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

        for (SpendLkRoute spendLkRoute : result) {
            spendLkRoute.updateFitsMilesIntervals(milesMin, milesMax);
        }

        Collections.sort(result);

        return result;
    }

    public List<SpendLkRoute> getSpendLkRoutes(String from,
                                                  String fromType,
                                                  String to,
                                                  String toType,
                                                  String airlineCode,
                                                  int milesMin,
                                                  int milesMax,
                                                  boolean isRoundTrip,
                                                  String serviceClassName,
                                                  String award) {

        List<SpendRoute> spendRoutes = this.spendBuilder.getSpendRoutes(from,
                                                                        fromType,
                                                                        to,
                                                                        toType,
                                                                        airlineCode,
                                                                        milesMin,
                                                                        milesMax,
                                                                        true,
                                                                        isRoundTrip,
                                                                        serviceClassName,
                                                                        award);
        return this.getSpendLkRoutes(spendRoutes, milesMin, milesMax);
    }

    public List<SpendLkRoute> getSpendLkRoutes(SpendInput spendInput) {
        List<SpendRoute> spendRoutes = this.spendBuilder.getSpendRoutes(spendInput);
        return this.getSpendLkRoutes(spendRoutes, spendInput.getMilesMin(), spendInput.getMilesMax());
    }

    public ResultMilesSpendLk build(SpendInput spendInput) {
        List<SpendLkRoute> routes = this.getSpendLkRoutes(spendInput);
        return ResultMilesSpendLk.of(routes);
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

    private void updateAwardMap(Map<String, RequiredAward> awardMap,
                                       Map<String, RequiredAward> updateMap) {
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

    private void replaceUO(List<SpendRoute> spendRoutes) {
        for (SpendRoute spendRoute : spendRoutes) {
            for (MileCost mileCost : spendRoute.getMileCosts()) {
                for (RequiredAward award : mileCost.getRequiredAwards()) {
                    if (award.getAwardType().equals(RequiredAward.UO_TYPE)) {
                        award.setAwardType(RequiredAward.UC_TYPE);
                    }
                }
            }
        }
    }
}
