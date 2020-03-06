package ru.integrotech.su.toString;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.airline.Tariff;
import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.bonus.Loyalty;
import ru.integrotech.airline.core.bonus.MilesRule;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.core.location.City;
import ru.integrotech.airline.core.location.Country;
import ru.integrotech.airline.core.location.WorldRegion;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.su.mock.MockLoader;

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
                    "mileAccrualRule",
                    "localLoyaltyLevelCode"};


    @BeforeClass
    public static void updateRegisters() {
        MockLoader.getInstance().updateRegisters(
                                    MockLoader.REGISTERS_TYPE.REAL,
                                    ALL_REGISTER_NAMES);
    }

    private RegisterCache registers;

    @Before
    public void init() {
        this.registers = MockLoader.getInstance().getRegisterCache();
    }

    @Test
    public void printStartLogs() {
        MockLoader.getInstance().getRegisterCache();
    }

    @Test
    public void printWrongRoutes() {

        for (Map.Entry<String, List<City>> wrongRoute : this.registers.getWrongRouteMap().entrySet()) {
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
        for (WorldRegion region : this.registers.getRegions()) {
            System.out.println(region.toString());
        }
    }

    @Test
    public void printCountries() {
        for (Country country : this.registers.getCountries()) {
            System.out.println(country.toString());
        }
    }

    @Test
    public void printCities() {
        for (City city : this.registers.getCities()) {
            System.out.println(city.toString());
        }
    }

    @Test
    public void printAirport() {
        System.out.println(this.registers.getAirport("IST").toString());
    }

    @Test
    public void printAirports() {
        for (Airport airport : this.registers.getAirports()) {
            System.out.println(airport.toString());
        }
    }

    @Test
    public void printLoyalty() {
        List<Loyalty> loyalties = new ArrayList<>(this.registers.getLoyalties());
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
        for (Map.Entry<String, Set<Bonus>> bonusRoute : this.registers.getBonusRouteMap().entrySet()) {
            System.out.printf("Bonus route: %-8s\n", bonusRoute.getKey());
            for (Bonus bonus : bonusRoute.getValue()) {
                System.out.printf("  %s\n", bonus.toString());
            }
            System.out.println();
        }
    }

    @Test
    public void printAirlines() {
        for (Airline airline : this.registers.getAirlines()) {
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
    public void printMilesRules() {
        for (MilesRule milesRule : this.registers.getMilesRules()) {
            System.out.println(milesRule);
        }
    }

    @Test
    public void printFlightPairs() {
        StringBuilder sb = new StringBuilder();
        for (Airport origin : this.registers.getAirports()) {
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
    public void printLoyaltyLevelCode() {
        Map<String, Integer> register = this.registers.getLoyaltyLevelCodeMap();
        for (Map.Entry<String, Integer> entry : register.entrySet()) {
            System.out.printf("%-10.10s  %s\n", entry.getKey(), entry.getValue());
        }
    }

}
