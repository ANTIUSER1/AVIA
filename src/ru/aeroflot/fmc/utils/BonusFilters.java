package ru.aeroflot.fmc.utils;

import ru.aeroflot.fmc.model.airline.ServiceClass;
import ru.aeroflot.fmc.model.bonus.Bonus;
import ru.aeroflot.fmc.model.flight.Flight;
import ru.aeroflot.fmc.model.flight.Route;
import ru.aeroflot.fmc.model.location.Airport;

import java.util.*;

/*class contains static methods for change some objects that contains collections listOf Bonus objects
* all methods are void and change the objects send by parameters*/
public class BonusFilters {

    public static void byInputParams(Set<Bonus> bonuses,
                                      ServiceClass.SERVICE_CLASS_TYPE serviceClassType,
                                      Airport airport,
                                      String award,
                                      boolean isRoundTrip,
                                      boolean routeIsDirect) {

        if (award.equalsIgnoreCase(Bonus.AWARD_TYPE.TICKET.name())) {
            bonuses.removeIf(ar -> ar.getType().equals(Bonus.BONUS_TYPE.U)
                            || ar.getType().equals(Bonus.BONUS_TYPE.UC)
                            || ar.getType().equals(Bonus.BONUS_TYPE.UO));
        } else if (award.equalsIgnoreCase(Bonus.AWARD_TYPE.UPGRADE.name())) {
            bonuses.removeIf(ar -> ar.getType().equals(Bonus.BONUS_TYPE.OW)
                            || ar.getType().equals(Bonus.BONUS_TYPE.RT));
        }

        bonuses.removeIf(ar -> (ar.getType().equals(Bonus.BONUS_TYPE.UC) && !routeIsDirect)
                || (ar.getType().equals(Bonus.BONUS_TYPE.UC) && !airport.isUcAvailable()));

        if (isRoundTrip) {
            bonuses.removeIf(ar -> ar.getType().equals(Bonus.BONUS_TYPE.OW)
                            || ar.getType().equals(Bonus.BONUS_TYPE.UO)
                            || ar.getType().equals(Bonus.BONUS_TYPE.UC));
        } else {
            bonuses.removeIf(ar -> ar.getType().equals(Bonus.BONUS_TYPE.RT)
                            || ar.getType().equals(Bonus.BONUS_TYPE.U));
        }

        if (serviceClassType != null) {
            bonuses.removeIf(ar -> !ar.getServiceClass().equals(serviceClassType));
        }
    }

    public static void byAllowedClasses(Set<Bonus> bonuses, Set<ServiceClass.SERVICE_CLASS_TYPE> allowedClasses) {
        if (!allowedClasses.isEmpty()) {
            bonuses.removeIf(bonus -> !allowedClasses.contains(bonus.getServiceClass()));
            bonuses.removeIf(bonus -> bonus.getUpgradeServiceClass() != null && !allowedClasses.contains(bonus.getUpgradeServiceClass()));
        }
    }

    public static void byCommonTypes(Route route) {
        byCommonAflTypes(route.getFlights());
        byCommonScyteamTypes(route.getFlights());
    }

    private static void byCommonAflTypes(List<Flight> flights) {
        if (flights == null || flights.size() == 1) return;
        Set<String> commonTypes = new HashSet<>();

        for (Bonus bonus : flights.get(0).getAflBonuses()) {
            commonTypes.add(bonus.getDescription());
        }

        for (int i = 1; i < flights.size(); i++) {
            Set<String> types = new HashSet<>();
            for (Bonus bonus : flights.get(i).getAflBonuses()) {
                types.add(bonus.getDescription());
            }
            commonTypes.removeIf(commonType -> !types.contains(commonType));
        }

        for (Flight flight : flights) {
            flight.getAflBonuses().removeIf(bonus -> !commonTypes.contains(bonus.getDescription()));
        }
    }

    private static void byCommonScyteamTypes(List<Flight> flights) {
        if (flights == null || flights.size() == 1) return;
        Set<String> commonTypes = new HashSet<>();

        for (Bonus bonus : flights.get(0).getScyteamBonuses()) {
            commonTypes.add(bonus.getDescription());
        }

        for (int i = 1; i < flights.size(); i++) {
            Set<String> types = new HashSet<>();
            for (Bonus bonus : flights.get(i).getScyteamBonuses()) {
                types.add(bonus.getDescription());
            }
            commonTypes.removeIf(commonType -> !types.contains(commonType));
        }

        for (Flight flight : flights) {
            flight.getScyteamBonuses().removeIf(bonus -> !commonTypes.contains(bonus.getDescription()));
        }
    }


    public static void byMilesInterval(Set<Bonus> bonuses, int minMiles, int maxMiles) {
        if (minMiles == -1) minMiles = 0;
        if (maxMiles == -1) maxMiles = Integer.MAX_VALUE;
        int counter = 0;
        for (Bonus bonus : bonuses) {
            if (bonus.getValue() >= minMiles && bonus.getValue() <= maxMiles) {
                counter ++;
            }
        }
        if (counter == 0) {
            bonuses.clear();
        }
    }

    public static void byExpiredDate(Set<Bonus> bonuses, Date date) {
        bonuses.removeIf(oldBonus -> (oldBonus.getValidFrom() != null && date.before(oldBonus.getValidFrom()))
                || (oldBonus.getValidTo() != null && date.after(oldBonus.getValidTo())));
    }

}
