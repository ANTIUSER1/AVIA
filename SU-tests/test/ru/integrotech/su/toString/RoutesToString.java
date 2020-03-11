package ru.integrotech.su.toString;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.searcher.BonusSearcher;
import ru.integrotech.su.inputparams.route.RoutesInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.mock.MockLoader.REGISTERS_TYPE;
import ru.integrotech.su.outputparams.route.RoutesBuilder;
import ru.integrotech.su.outputparams.spend.SpendBuilder;

import java.util.Collections;
import java.util.List;

/* class for visualization all possible data
*  use to check proper performance listOf toString methods*/
public class RoutesToString {

    @BeforeClass
    public static void updateRegisters() {
        MockLoader.getInstance().updateRegisters(
                                    REGISTERS_TYPE.REAL,
                                    SpendBuilder.getRegisterNames());
    }

    private RoutesBuilder routesBuilder;

    private BonusSearcher bonusSearcher;

    @Before
    public void init() {
        this.routesBuilder = RoutesBuilder.of(MockLoader.getInstance().getRegisterCache());
        this.bonusSearcher = BonusSearcher.of(MockLoader.getInstance().getRegisterCache());
    }

    @Test
    public void printRoutes() {

        RoutesInput routesInput = RoutesInput.of(
                "Airport", //destination type
                "LED", // origin
                "Airport", //destination type
                "AER", //destination
                null //airline code
        );

        List<Route> routes = this.routesBuilder.getRoutes(routesInput);
        Collections.sort(routes);
        for (Route route : routes) {
            System.out.println(route);
        }
    }

    @Test
    public void printRoutesWithDetails() {

        RoutesInput routesInput = RoutesInput.of(
        		  "Airport", //destination type
                  "KGD", // origin
                  "aIrport", //destination type
                  "STR", //destination
                  null //airline code
        );

        List<Route> routes = this.routesBuilder.getRoutes(routesInput);
        Collections.sort(routes);

        for (Route route : routes) {
            this.bonusSearcher.findBonuses( route,false); //is Afl only
        }

        for (Route route : routes) {
            System.out.printf("%s",route);
            for (Flight flight : route.getFlights()) {
                System.out.printf("\n%s", flight.toString());
            }
            if (route.getAflBonuses() != null && route.getAflBonuses().size() > 0) {
                System.out.printf("\n  AFL bonuses: %d", route.getAflBonuses().size());
                for (Bonus bonus : route.getAflBonuses()) {
                    System.out.printf("\n    %s", bonus);
                }
            }
            if (route.getScyteamBonuses() != null && route.getScyteamBonuses().size() > 0) {
                System.out.printf("\n  Skyteam bonuses: %d", route.getScyteamBonuses().size());
                for (Bonus bonus : route.getScyteamBonuses()) {
                    System.out.printf("\n    %s", bonus);
                }
            }
            System.out.println();
            System.out.println();
        }
    }



}
