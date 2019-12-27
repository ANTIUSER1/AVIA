package ru.integrotech.su.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.airline.Tariff;
import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.bonus.ChargeRule;
import ru.integrotech.airline.core.bonus.Loyalty;
import ru.integrotech.airline.core.bonus.TicketDesignator;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.core.location.City;
import ru.integrotech.airline.core.location.Country;
import ru.integrotech.airline.core.location.WorldRegion;
import ru.integrotech.su.inputparams.charge.ChargeInput;
import ru.integrotech.su.inputparams.route.RoutesInput;
import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.charge.ChargeBuilder;
import ru.integrotech.su.outputparams.charge.ChargeRoute;
import ru.integrotech.su.outputparams.charge.ResultMilesCharge;
import ru.integrotech.su.outputparams.spend.*;

import java.util.*;

/* class for visualization all possible data
*  use to check proper performance listOf toString methods*/
public class ToStringTest{

    private final CommonTest common;

    public ToStringTest() {
        this.common = CommonTest.of(MockLoader.ofRealRegisters());
    }

    @Test
    public void printStartLogs() {
        this.common.getTestsCache();
    }

    @Test
    public void printWrongRoutes() {
        for (Map.Entry<String, List<City>> wrongRoute : this.common.getTestsCache().getWrongRouteMap().entrySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append("WrongRoute:   ");
            int counter = wrongRoute.getValue().size();
            for (City city : wrongRoute.getValue()) {
                counter--;
                if (counter > 0) {
                    sb.append(String.format(" %3s %-20s -->", city.getCode(), city.getName()));
                } else {
                    sb.append(String.format(" %3s %-20s", city.getCode(), city.getName()));
                }
            }
            System.out.println(sb.toString());
        }
    }

    @Test
    public void printRegions() {
        for (WorldRegion region : this.common.getTestsCache().getRegions()) {
            System.out.println(region.toString());
        }
    }

    @Test
    public void printCountries() {
        for (Country country : this.common.getTestsCache().getCountries()) {
            System.out.println(country.toString());
        }
    }

    @Test
    public void printCities() {
        for (City city : this.common.getTestsCache().getCities()) {
            System.out.println(city.toString());
        }
    }

    @Test
    public void printAirports() {
        for (Airport airport : this.common.getTestsCache().getAirports()) {
            System.out.println(airport.toString());
        }
    }

    @Test
    public void printLoyalty() {
        List<Loyalty> loyalties = new ArrayList<>(this.common.getTestsCache().getLoyalties());
        loyalties.sort(new Comparator<Loyalty>() {
            @Override
            public int compare(Loyalty o1, Loyalty o2) {
                return o2.getMiles() - o1.getMiles();
            }
        });
        for (Loyalty loyalty : loyalties){
            System.out.println(loyalty.toString());
        }
    }

    @Test
    public void printBonusRoute() {
        for (Map.Entry<String, Set<Bonus>> bonusRoute : this.common.getTestsCache().getBonusRouteMap().entrySet()) {
            System.out.printf("Bonus route: %-8s\n", bonusRoute.getKey());
            for (Bonus bonus : bonusRoute.getValue()) {
                System.out.printf("  %s\n", bonus.toString());
            }
            System.out.println();
        }
    }

    @Test
    public void printAirlines() {
        for (Airline airline : this.common.getTestsCache().getAirlines()) {
            System.out.println(airline);
            List<ServiceClass> serviceClasses = new ArrayList<>(airline.getServiceClassMap().values());

            serviceClasses.sort(new Comparator<ServiceClass>() {
                @Override
                public int compare(ServiceClass o1, ServiceClass o2) {
                    return o2.compareTo(o1);
                }
            });

            for (ServiceClass serviceClass : serviceClasses) {
                System.out.format("  %s\n", serviceClass.toString());
                for (Tariff tariff : serviceClass.getTariffMap().values()) {
                    System.out.format("     %s\n", tariff.toString());
                }
            }
            System.out.println();
        }
    }

    @Test
    public void printFlights() {
        StringBuilder sb = new StringBuilder();
        for (Airport origin : this.common.getTestsCache().getRegisters().getAirports()) {
            sb.append(String.format("Origin: %s, %s\n", origin.getCode(), origin.getName()));
            for (Map.Entry<Airport, Flight> destination : origin.getOutcomeFlights().entrySet()) {
                sb.append(String.format("Destination: %s, %s\n", destination.getKey().getCode(), destination.getKey().getName()));
                sb.append(destination.getValue().toString());
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
    
    @Test
    public void printChargeRules() {
        for (ChargeRule chargeRule : this.common.getTestsCache().getRegisters().getChargeRules()) {
            System.out.println(chargeRule);
        }
    }

    @Test
    public void printTicketDesignators() {
        for (TicketDesignator designator : this.common.getTestsCache().getRegisters().getTicketDesignators()) {
            System.out.println(designator);
        }
    }

    @Test
    public void printCharge() {
        ChargeInput chargeInput = ChargeInput.of("city", //from
                                                 "MOW", // from type
                                                 "city", // to
                                                 "PRG", // to type
                                                 "SU", // airline code
                                                 "basic", //loyalty
                                                 false); //is round
        List<ChargeRoute> result = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }

    @Test
    public void printSpend() {
        SpendInput spendInput = SpendInput.of(
                                    "airport",// from
                                    "SVO", // from type
                                    "airport", // to
                                    "LAX", // to type
                                    -1, // miles min
                                    100000, // miles max
                                    "economy", // class listOf service name
                                    "all", // award type
                                    false, // afl only
                                    false // is round trip
                                    );
        String airlineCode = "SU";
        List<SpendRoute> result = this.common.getSpendBuilder().getSpendRoutes(spendInput, airlineCode);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }

    @Test
    public void printSpendLk() {
        SpendInput spendInput = SpendInput.of(
                "airport",// from
                "SVO", // from type
                "airport", // to
                "VVO", // to type
                -1, // miles min
                100000, // miles max
                "economy", // class listOf service name
                "all", // award type
                false, // afl only
                false // is round trip
        );
        String airlineCode = "SU";
        List<SpendLkRoute> result = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput, airlineCode);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }

    @Test
    public void printRoutes() {

        RoutesInput routesInput = RoutesInput.of(
                "airport", //destination type
                "SVO", // origin
                "airport", //destination type
                "TYD", //destination
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
        		  "airport", //destination type
                  "SVO", // origin
                  "airport", //destination type
                  "TYD", //destination
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

    @Test
    public void ODM_CHARGE_TEST_00() {
        ChargeInput chargeInput = ChargeInput.of(
                "airport", // from type
                "SVO", // from
                "airport", // to type
                "VVO", // to
                "SU", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        ChargeBuilder builder = this.common.getChargeBuilder();
        List<Route> routes = builder.getRoutes(chargeInput);
        builder.getFactor(chargeInput);
        /* ODM rules works here*/
        ResultMilesCharge result = builder.buildResult(routes, chargeInput);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }

    @Test
    public void ODM_SPEND_TEST_00() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "VVO", // to
                -1, // miles min
                100000, // miles max
                "economy", // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        SpendBuilder builder = this.common.getSpendBuilder();
        List<Route> routes = builder.getRoutes(spendInput);
        /* ODM rules works here*/
        ResultMilesSpend result = builder.buildResult(routes, spendInput);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }

    @Test
    public void ODM_SPEND_LK_TEST_00() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "VVO", // to
                -1, // miles min
                100000, // miles max
                "economy", // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        SpendLkBuilder builder = this.common.getSpendLkBuilder();
        List<Route> routes = builder.getRoutes(spendInput);
        /* ODM rules works here*/
        ResultMilesSpendLk result = builder.buildResult(routes, spendInput);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }


}
