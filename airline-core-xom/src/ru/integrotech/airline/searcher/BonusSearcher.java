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

/**
 * Class contains logic for search bonuses by given parameters <br>
 *
 * Used inSpend project
 *
 */

public class BonusSearcher {

    public static BonusSearcher of(RegisterCache registerCache) {
        BonusSearcher result = new BonusSearcher();
        result.setRegisterCache(registerCache);
        result.setAfl(registerCache.getAirline(Airline.AFL_CODE));
        return result;
    }

    private Airline afl;

    private RegisterCache registerCache;

    public Airline getAfl() {
        return afl;
    }

    public void setAfl(Airline afl) {
        this.afl = afl;
    }

    public RegisterCache getRegisterCache() {
        return registerCache;
    }

    public void setRegisterCache(RegisterCache registerCache) {
        this.registerCache = registerCache;
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
        Set<ServiceClass.SERVICE_CLASS_TYPE> commonRouteClasses = route.getCommonRouteClasses(afl);
        Set<Bonus> bonuses = new HashSet<>();

        if (route.isDirect()) {
            bonuses.addAll(this.findBonuses(String.format("%s%s%s", zoneFrom, zoneTo, "A")));
            bonuses.addAll(this.findBonuses(String.format("%s%s%s", zoneTo, zoneFrom, "A")));

        } else {
            if (route.isWrong()) {
                for (Flight flight : route.getFlights()) {
                    this.findAflBonuses(flight);
                }

            } else {
                Airport.ZONE zoneVia = route.getFlights().get(0).getDestination().getAflZone();
                bonuses.addAll(this.findBonuses(String.format("%s%s%s%s", zoneFrom, zoneVia, zoneTo, "A")));
                bonuses.addAll(this.findBonuses(String.format("%s%s%s%s", zoneTo, zoneVia, zoneFrom, "A")));

                if (bonuses.isEmpty() && (zoneFrom != zoneTo)) {
                    bonuses.addAll(this.findBonuses(String.format("%s%s%s", zoneFrom, zoneTo, "A")));
                    bonuses.addAll(this.findBonuses(String.format("%s%s%s", zoneTo, zoneFrom, "A")));
                }

                if (bonuses.isEmpty()) {
                    for (Flight flight : route.getFlights()) {
                        this.findAflBonuses(flight);
                        BonusFilters.byCommonRouteClasses(flight.getAflBonuses(), commonRouteClasses);
                    }
                }
            }
        }

        if (!bonuses.isEmpty()) {
            BonusFilters.byCommonRouteClasses(bonuses, commonRouteClasses);
            BonusFilters.byExpiredDate(bonuses, new Date());
            route.getAflBonuses().addAll(bonuses);
        }
    }

    private void findAflBonuses(Flight flight) {
        Set<Bonus> bonuses = new HashSet<>();
        bonuses.addAll(findBonuses(flight.getAflZones()));
        bonuses.addAll(findBonuses(flight.getAflReverseZones()));
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





}
