package ru.integrotech.su.toString;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.airline.Tariff;
import ru.integrotech.airline.core.bonus.*;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.core.location.City;
import ru.integrotech.airline.core.location.Country;
import ru.integrotech.airline.core.location.WorldRegion;
import ru.integrotech.airline.register.RegisterLoader;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.test.CommonTest;

import java.util.*;

/* class for visualization all possible data
*  use to check proper performance listOf toString methods*/
public class RegistersToString {

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
                    "mileAccrualRule"};

    private CommonTest common;

    @BeforeClass
    public static void updateRegisters() {
        RegisterLoader.updateInstance(ALL_REGISTER_NAMES);
    }

    public RegistersToString() {
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
    public void printAirport() {
        System.out.println(this.common.getTestsCache().getRegisters().getAirport("IST").toString());
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
    public void printChargeRules() {
        for (MilesRule milesRule : this.common.getTestsCache().getRegisters().getMilesRules()) {
            System.out.println(milesRule);
        }
    }

    @Test
    public void printTicketDesignators() {
        for (TicketDesignator designator : this.common.getTestsCache().getRegisters().getTicketDesignators()) {
            System.out.println(designator);
        }
    }

    @Test
    public void printFlightPairs() {
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

}
