package ru.aeroflot.fmc.searcher;

import ru.aeroflot.fmc.model.airline.Airline;
import ru.aeroflot.fmc.model.bonus.Bonus;
import ru.aeroflot.fmc.model.flight.Route;
import ru.aeroflot.fmc.model.flight.Flight;
import ru.aeroflot.fmc.register.RegisterCache;
import ru.aeroflot.fmc.utils.BonusFilters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
        this.findSimpleBonus(route, isAflOnly);
        if (route.getAflBonuses().isEmpty() && route.getScyteamBonuses().isEmpty()) {
            findComplexBonus(route, isAflOnly);
        }
    }

    private void findSimpleBonus(Route route, boolean isAflOnly) {

        if (route.isOperatesBy(afl) && !this.registerCache.isWrongRoute(route.getCityCodes())) {
            route.getAflBonuses().addAll(findBonuses(route.getAflZones()));
            route.getAflBonuses().addAll(findBonuses(route.getAflReverseZones()));
            for (Flight flight : route.getFlights(afl)) {
                BonusFilters.byAllowedClasses(route.getAflBonuses(), flight.getAllowedClasses(afl));
            }
        }

        if (!isAflOnly && route.isOperatesWithout(afl)){
            route.getScyteamBonuses().addAll(findBonuses(route.getScyteamZones()));
            route.getScyteamBonuses().addAll(findBonuses(route.getScyteamReverseZones()));
            //TODO add BonusFilters.byAllowedClasses for scyteam
        }

        BonusFilters.byExpiredDate(route.getAflBonuses(), new Date());

    }

    private void findComplexBonus(Route route, boolean isAflOnly) {
        for (Flight flight : route.getFlights()) {

            if (route.isOperatesBy(afl)) {
                flight.getAflBonuses().addAll(findBonuses(flight.getAflZones()));
                flight.getAflBonuses().addAll(findBonuses(flight.getAflReverseZones()));
                BonusFilters.byAllowedClasses(flight.getAflBonuses(), flight.getAllowedClasses(afl));
            }

            if (!isAflOnly && route.isOperatesWithout(afl)) {
                flight.getScyteamBonuses().addAll(findBonuses(flight.getScyteamZones()));
                flight.getScyteamBonuses().addAll(findBonuses(flight.getScyteamReverseZones()));
                //TODO add BonusFilters.byAllowedClasses for scyteam
            }

            BonusFilters.byExpiredDate(flight.getAflBonuses(), new Date());
        }
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
