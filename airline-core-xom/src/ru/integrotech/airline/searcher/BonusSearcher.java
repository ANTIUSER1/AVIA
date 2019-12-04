package ru.integrotech.airline.searcher;


import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.utils.BonusFilters;

import java.util.*;

/* class contains logic for search bonuses by given parameters*/
public class BonusSearcher {

    public static BonusSearcher of(RegisterCache registerCache) {
        return new BonusSearcher(registerCache);
    }

    private final Airline afl;

    private final RegisterCache registerCache;

    private BonusSearcher(RegisterCache registerCache) {
        this.registerCache = registerCache;
        this.afl = registerCache.getAirline(Airline.AFL_CODE);
    }

    public void findBonuses(Route route, boolean isAflOnly) {

        if (route.isOperatesBy(afl)) {
            this.findAflBonuses(route);
        }

        if (!isAflOnly && route.otherAirlinesIsPresent(this.afl)) {
            this.findScyteamBonuses(route);
        }

    }

    private void findAflBonuses(Route route) {
        Airport.ZONE zoneFrom = route.getOrigin().getAflZone();
        Airport.ZONE zoneTo = route.getDestination().getAflZone();
        Set<Bonus> bonuses = new HashSet<>();
        if (!route.isDirect()) {
            if (!this.isWrong(route)) {

                Airport.ZONE zoneVia = route.getFlights().get(0).getDestination().getAflZone();
                bonuses.addAll(this.findBonuses(String.format("%s%s%s%s", zoneFrom, zoneVia, zoneTo, "A")));
                bonuses.addAll(this.findBonuses(String.format("%s%s%s%s", zoneTo, zoneVia, zoneFrom, "A")));

                if (bonuses.isEmpty()
                        && (zoneFrom != zoneTo)) {

                    bonuses.addAll(this.findBonuses(String.format("%s%s%s", zoneFrom, zoneTo, "A")));
                    bonuses.addAll(this.findBonuses(String.format("%s%s%s", zoneTo, zoneFrom, "A")));
                }
            }
        } else {
            bonuses.addAll(this.findBonuses(String.format("%s%s%s", zoneFrom, zoneTo, "A")));
            bonuses.addAll(this.findBonuses(String.format("%s%s%s", zoneTo, zoneFrom, "A")));
        }

        Set<ServiceClass.SERVICE_CLASS_TYPE> allowedClasses = route.getAllowedClasses(afl);
        if (bonuses.isEmpty() && !route.isDirect()) {
            for (Flight flight : route.getFlights()) {
                this.findAflBonuses(flight, allowedClasses);
            }
            this.bonusSummation(route);
        } else {
            BonusFilters.byAllowedClasses(bonuses, allowedClasses);
            BonusFilters.byExpiredDate(bonuses, new Date());
            route.getAflBonuses().addAll(bonuses);
        }
    }

    private boolean isWrong (Route route) {
        return this.registerCache.isWrongRoute(route.getCityCodes());
    }

    private void findAflBonuses(Flight flight, Set<ServiceClass.SERVICE_CLASS_TYPE> allowedClasses) {
        Set<Bonus> bonuses = new HashSet<>();
        bonuses.addAll(findBonuses(flight.getAflZones()));
        bonuses.addAll(findBonuses(flight.getAflReverseZones()));
        BonusFilters.byAllowedClasses(bonuses, allowedClasses);
        BonusFilters.byExpiredDate(bonuses, new Date());
        flight.setAflBonuses(bonuses);
    }

    private void findScyteamBonuses(Route route) {
        Airport.ZONE zoneFrom = route.getOrigin().getScyteamZone();
        Airport.ZONE zoneTo = route.getDestination().getScyteamZone();
        Set<Bonus> bonuses = new HashSet<>();
        bonuses.addAll(this.findBonuses(String.format("%s%s%s", zoneFrom, zoneTo, "S")));
        bonuses.addAll(this.findBonuses(String.format("%s%s%s", zoneTo, zoneFrom, "S")));
        BonusFilters.byExpiredDate(bonuses, new Date());
        route.getScyteamBonuses().addAll(bonuses);
    }

    private List<Bonus> findBonuses(String code) {
        List<Bonus> result = new ArrayList<>();
            Set<Bonus> bonuses = this.registerCache.getBonusRoute(code);
            if (bonuses != null) {
                result.addAll(bonuses);
            }
        return result;
    }

    private void bonusSummation(Route route) {
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
        }
    }



}
