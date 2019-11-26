package ru.integrotech.su.outputparams.charge;

import java.util.*;

public class ChargeUtil {

    public static Set<String> getClassOfServiceCodes(ChargeRoute route) {
        Set<String> result = new LinkedHashSet<>();
        for (Segment segment : route.getSegments()) {
            for (MilesAmount milesAmount : segment.getMilesAmounts()) {
                result.add(milesAmount.getClassOfService().getClassOfServiceCode());
            }
        }
        return result;
    }

    public static int getMinMilesCharge(ChargeRoute route) {
        int result = Integer.MAX_VALUE;
        for (Segment segment : route.getSegments()) {
            for (MilesAmount milesAmount : segment.getMilesAmounts()) {
                for (Fare fare : milesAmount.getFareGroups()) {
                    for (AirlineFareGroup airlineFareGroup : fare.getAirlineFareGroups()) {
                        if (airlineFareGroup.getMilesCharged() < result) {
                            result = airlineFareGroup.getMilesCharged();
                        }
                    }
                }
            }
        }
        return result;
    }

    public static Map<String, List<String>> getTariffMap(ChargeRoute route) {
        Map<String, List<String>> result = new LinkedHashMap<>();
        for (Segment segment : route.getSegments()) {
            for (MilesAmount milesAmount : segment.getMilesAmounts()) {
                String classOfServiceCode = milesAmount.getClassOfService().getClassOfServiceCode();
                result.put(classOfServiceCode, new ArrayList<>());
                for (Fare fare : milesAmount.getFareGroups()) {
                    result.get(classOfServiceCode).add(fare.getFareGroup().getFareGroupCode());
                }
            }
        }
        return result;
    }

    public static List<List<String>> getAllPrefixes(ChargeRoute route) {
        List<List<String>> result = new ArrayList<>();
        for (Segment segment : route.getSegments()) {
            for (MilesAmount milesAmount : segment.getMilesAmounts()) {
                for (Fare fare : milesAmount.getFareGroups()) {
                    for (AirlineFareGroup airlineFareGroup : fare.getAirlineFareGroups()) {
                        result.add(airlineFareGroup.getFarePrefixes());
                    }
                }
            }
        }
        return result;
    }


}
