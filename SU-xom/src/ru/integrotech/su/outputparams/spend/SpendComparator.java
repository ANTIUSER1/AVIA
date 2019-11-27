package ru.integrotech.su.outputparams.spend;

import ru.integrotech.su.common.Airline;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SpendComparator {

    private static final String SUCCESS_TEST_CODE = "OK";

    public static SpendComparator of() {
        return new SpendComparator();
    }

    private SpendComparator() {
    }

    public String compareSpendRoute(List<SpendRoute> expected, List<SpendRoute> actual) {
        StringBuilder builder = new StringBuilder();
        this.sort(actual);
        this.sort(expected);
        Iterator<SpendRoute> expectedIterator = expected.iterator();
        Iterator<SpendRoute> actualIterator = actual.iterator();
        while (expectedIterator.hasNext()) {
            SpendRoute expectedRoute = expectedIterator.next();
            while (actualIterator.hasNext()) {
                SpendRoute actualRoute = actualIterator.next();
                String compareReport = this.compareSpendRoute(expectedRoute, actualRoute);
                System.out.println(compareReport);

            }

        }

        /*
        if test is
         */

        return builder.toString();
    }

    private void sort(List<SpendRoute> list) {
        Collections.sort(list);
        this.sortInnerElements(list);
    }


    private void sortInnerElements(List<SpendRoute> spendRoutes) {
        for (SpendRoute spendRoute : spendRoutes) {
            spendRoute.sort();
        }
    }

    private String compareSpendRoute(SpendRoute expected, SpendRoute actual) {
        StringBuilder builder = new StringBuilder();
        if (expected.getOrigin().compareTo(actual.getOrigin()) != 0) {
            builder.append(String.format("Origin is not equals:  %5s %5s ",
                    expected.getOrigin().getAirport().getAirportCode(),
                    actual.getOrigin().getAirport().getAirportCode()));
        } else if (expected.getDestination().compareTo(actual.getDestination()) != 0) {
            builder.append(String.format("Destination is not equals:  %5s %5s ",
                    expected.getDestination().getAirport().getAirportCode(),
                    actual.getDestination().getAirport().getAirportCode()));
        } else if (expected.getVia() != null && actual.getVia() == null) {
            builder.append(String.format("Via is not equals:  %5s %5s ",
                    expected.getVia().getAirport().getAirportCode(),
                    "NULL"));
        } else if (expected.getVia() == null && actual.getVia() != null) {
            builder.append(String.format("Via is not equals:  %5s %5s ",
                    "NULL",
                    actual.getVia().getAirport().getAirportCode()));
        } else if (expected.getVia() != null && actual.getVia() != null) {
            if (expected.getVia().compareTo(actual.getVia()) != 0) {
                builder.append(String.format("Via is not equals:  %5s %5s ",
                        expected.getVia().getAirport().getAirportCode(),
                        actual.getVia().getAirport().getAirportCode()));
            }
        } else if (expected.isAfl() != actual.isAfl()) {
            builder.append(String.format("IsAfl is not equals:  %5s %5s ",
                                        expected.isAfl(),
                                        actual.isAfl()));
        }
        else if (!this.compareAirlines(expected.getAirlines(), actual.getAirlines()).
                    endsWith(SUCCESS_TEST_CODE)) {
            //TODO fill builder
        }  else if (!this.compareMilesCosts(expected.getMileCosts(), actual.getMileCosts()).
                    endsWith(SUCCESS_TEST_CODE)) {
            //TODO fill builder
        }
        else  {
            builder.append(String.format("%3s",
                    expected.getDestination().getAirport().getAirportCode()));

            if (expected.getVia() != null) {
                builder.append(String.format(" %3s",
                        expected.getVia().getAirport().getAirportCode()));
            }
            builder.append(String.format(" %3s - %2s",
                    expected.getDestination().getAirport().getAirportCode(),
                    SUCCESS_TEST_CODE));
        }
        return builder.toString();
    }

    private String compareAirlines(List<Airline> expected, List<Airline> actual) {
        StringBuilder builder = new StringBuilder();
        //TODO add logic
        builder.append(SUCCESS_TEST_CODE);
        return builder.toString();
    }

    private String compareMilesCosts(List<MileCost> expected, List<MileCost> actual) {
        StringBuilder builder = new StringBuilder();
        //TODO add logic
        builder.append(SUCCESS_TEST_CODE);
        return builder.toString();
    }
}