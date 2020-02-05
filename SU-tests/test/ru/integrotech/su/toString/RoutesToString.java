package ru.integrotech.su.toString;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.register.RegisterLoader;
import ru.integrotech.su.inputparams.route.RoutesInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.route.RoutesBuilder;
import ru.integrotech.su.outputparams.spend.SpendBuilder;
import ru.integrotech.su.test.CommonTest;

import java.util.Collections;
import java.util.List;

/* class for visualization all possible data
*  use to check proper performance listOf toString methods*/
public class RoutesToString {

    private static final String[] ALL_REGISTER_NAMES = new String[]
                    {"airline",
                    "region",
                    "country",
                    "city",
                    "airport",
                    "pair",
                    "serviceClassLimit",
                    "tariff",
                    "bonusRoute",
                    "award",
                    "wrongRoute",
                    "tierLevel",
                    "milesRule",
                    "ticketDesignators"};

    private CommonTest common;

    @BeforeClass
    public static void updateRegisters() {
        RegisterLoader.updateInstance(SpendBuilder.getRegisterNames());
    }

    public RoutesToString() {
        this.common = CommonTest.of(MockLoader.ofRealRegisters());
    }

    @Test
    public void printRoutes() {

        RoutesInput routesInput = RoutesInput.of(
                "Airport", //destination type
                "LED", // origin
                "Airport", //destination type
                "NUE", //destination
                null //airline code
        );

        List<Route> routes = this.common.getRoutesBuilder().getRoutes(routesInput);
        Collections.sort(routes);
        for (Route route : routes) {
            System.out.println(route);
        }
    }

    @Test
    public void printRoutesWithDetails() {

        RoutesInput routesInput = RoutesInput.of(
        		  "Airport", //destination type
                  "LED", // origin
                  "aIrport", //destination type
                  "NUE", //destination
                  null //airline code
        );

        List<Route> routes = this.common.getRoutesBuilder().getRoutes(routesInput);
        Collections.sort(routes);

        for (Route route : routes) {
            this.common.getBonusSearcher().findBonuses( route,
                                                false); //is Afl only
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
