package ru.integrotech.su.unifyTests;

import com.google.gson.JsonElement;
import org.junit.Assert;
import org.junit.Test;
import ru.integrotech.su.common.Location;
import ru.integrotech.su.inputparams.charge.ChargeInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.charge.ChargeRoute;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class OutsideChargeTest extends UnifyBaseTest {

    private static final String ROOT_TEST_DIRECTORY_PATH = "outChargeTestDirectory";
    private static final String SUCCESS = "OK\n";
    private static final String INCORRECT = "INCORRECT\n";
    private static final String NOT_FOUND = "not found\n";
    private static final String EXTRA = "extra\n";

    public OutsideChargeTest() {
        super(MockLoader.ofMockRegisters(), ChargeRoute.class);
    }

    @Override
    protected boolean isCorrectCase(String pathToCaseFolder) throws IOException {
        JsonElement jsonElement = this.common.getLoader().loadJson(pathToCaseFolder, REQUEST_FILE_NAME);
        ChargeInput chargeInput = this.common.getTestsCache().loadChargeInputParams(jsonElement);

        jsonElement = this.common.getLoader().loadJson(pathToCaseFolder, ACTUAL_RESPONSE_FILE_NAME);
        List<ChargeRoute> actualChargeRoutes = this.common.getTestsCache().loadChargeRoutes(jsonElement);

        jsonElement = this.common.getLoader().loadJson(pathToCaseFolder, EXPECTED_RESPONSE_FILE_NAME);
        List<ChargeRoute> expectedChargeRoutes = this.common.getTestsCache().loadChargeRoutes(jsonElement);

        String testHeader = this.buildReportHeader(chargeInput);
        String testBody = this.compareChargeRoute(expectedChargeRoutes, actualChargeRoutes);
        boolean result = testBody.contains(SUCCESS)
                && ( !testBody.contains(INCORRECT)
                &&!testBody.contains(NOT_FOUND)
                &&!testBody.contains(EXTRA));
        printTestResults(result, actualChargeRoutes, pathToCaseFolder);
        String testReport = String.format("%s%s", testHeader, testBody);
        printReport(testReport, pathToCaseFolder);
        return result;
    }

    @Test
    public void test() {
        Assert.assertTrue(executeTest(ROOT_TEST_DIRECTORY_PATH));
    }

    @Override
    protected <T> void printTestResults(boolean testIsOK, List<T> actualRoutes, String pathToCaseFolder) throws FileNotFoundException, UnsupportedEncodingException {
        if (testIsOK) {
            System.out.printf("Case in directory %s is OK\n", pathToCaseFolder);
        } else {
            System.out.printf("Case in directory %s is INCORRECT\n", pathToCaseFolder);
        }
    }

    private String printLocation(Location location) {
        StringBuilder builder = new StringBuilder();
        if (location == null) {
            builder.append(String.format("|  %-20s %-10s   |\n",
                    "location type:", "null"));
            builder.append(String.format("|  %-20s %-10s   |\n",
                    "location code:", "null"));
        } else if (location.getAirport() != null) {
            builder.append(String.format("|  %-20s %-10s   |\n",
                    "location type:", "airport"));
            builder.append(String.format("|  %-20s %-10s   |\n",
                    "location code:", location.getAirport().getAirportCode()));
        } else  if (location.getCity() != null) {
            builder.append(String.format("|  %-20s %-10s   |\n",
                    "location type:", "city"));
            builder.append(String.format("|  %-20s %-10s   |\n",
                    "location code:", location.getCity().getCityCode()));
        } else  if (location.getCountry() != null) {
            builder.append(String.format("|  %-20s %-10s   |\n",
                    "location type:", "country"));
            builder.append(String.format("|  %-20s %-10s   |\n",
                    "location code:", location.getCountry().getCountryCode()));
        } else  if (location.getRegion() != null) {
            builder.append(String.format("|  %-20s %-10s   |\n",
                    "location type:", "region"));
            builder.append(String.format("|  %-20s %-10s   |\n",
                    "location code:", location.getRegion().getRegionCode()));
        }

        return builder.toString();
    }

    private String buildReportHeader(ChargeInput chargeInput) {
        StringBuilder builder = new StringBuilder();
        builder.append("----------- CHARGE TEST -------------\n");
        builder.append("|  Parameters:                       |\n");

        //airline block
        builder.append("|------------------------------------|\n");
        builder.append(String.format("|  %-20s %-10s   |\n",
                "airline:", chargeInput.getAirline().getAirlineCode()));

        //origin block
        builder.append("|------------------------------------|\n");
        builder.append("|  Origin:                           |\n");
        builder.append(this.printLocation(chargeInput.getOrigin()));

        //destination block
        builder.append("|------------------------------------|\n");
        builder.append("|  Destination:                      |\n");
        builder.append(this.printLocation(chargeInput.getDestination()));

        //other values block
        builder.append("|------------------------------------|\n");
        builder.append(String.format("|  %-20s %-10s   |\n",
                "tier level code:", chargeInput.getTierLevel().getTierLevelCode()));
        builder.append(String.format("|  %-20s %-10s   |\n",
                "is round trip:", chargeInput.getIsRoundTrip()));
        builder.append(" ------------------------------------\n");
        builder.append("\n");

        return builder.toString();
    }


    private String compareChargeRoute(List<ChargeRoute> expected, List<ChargeRoute> actual) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%-26s     %s\n", " --- R O U T E ---", "RESULT"));
        builder.append("\n");
        this.sort(actual);
        this.sort(expected);
        ArrayList<ChargeRoute> actualArr = new ArrayList<>(actual);
        ArrayList<ChargeRoute> expectedlArr = new ArrayList<>(expected);
        int successCounter = 0;
        int incorrectCounter = 0;
        Iterator<ChargeRoute> expectedIterator = expectedlArr.iterator();
        expected: while (expectedIterator.hasNext()) {
            ChargeRoute expectedRoute = expectedIterator.next();
            Iterator<ChargeRoute> actualIterator = actualArr.iterator();
            while (actualIterator.hasNext()) {
                ChargeRoute actualRoute = actualIterator.next();
                if (this.isShallowEquals(expectedRoute, actualRoute)) {
                    if (this.isDeepEquals(expectedRoute, actualRoute)) {
                        builder.append(String.format("   %-25s     %s",
                                this.getRoteCode(actualRoute),
                                SUCCESS));
                        successCounter++;
                    } else {
                        builder.append(String.format("   %-22s     %s",
                                this.getRoteCode(actualRoute),
                                INCORRECT));
                        incorrectCounter++;
                    }
                    expectedIterator.remove();
                    actualIterator.remove();
                    continue expected;
                }
            }
        }

        for (ChargeRoute route : expectedlArr) {
            builder.append(String.format("   %-22s     %s",
                    this.getRoteCode(route),
                    NOT_FOUND));
        }

        for (ChargeRoute route : actualArr) {
            builder.append(String.format("   %-23s     %s",
                    this.getRoteCode(route),
                    EXTRA));
        }

        builder.append("\n");
        builder.append(" ------------- RESULTS --------------\n");

        if ((expectedlArr.size() == 0)
                && (actualArr.size() == 0)
                && incorrectCounter == 0){
            builder.append("|            Tests is OK             |\n");
        } else {
            builder.append(String.format("|  %-20s %,10d   |\n",
                    "expected routes:", expected.size()));
            builder.append(String.format("|  %-20s %,10d   |\n",
                    "actual routes:", actual.size()));

            builder.append(String.format("|  %-20s %,10d   |\n",
                    "OK:", successCounter));
            builder.append(String.format("|  %-20s %,10d   |\n",
                    "incorrect:", incorrectCounter));

            builder.append(String.format("|  %-20s %,10d   |\n",
                    "not found:", expectedlArr.size()));
            builder.append(String.format("|  %-20s %,10d   |\n",
                    "extra:", actualArr.size()));
        }

        builder.append(" ------------------------------------ \n");

        return builder.toString();
    }

    private String getRoteCode(ChargeRoute route) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s  ", route.getOrigin().getAirport().getAirportCode()));
        if (route.getVia() != null) {
            builder.append(String.format("%s  ", route.getVia().getAirport().getAirportCode()));
        } else {
            builder.append(String.format("%s  ", "   "));
        }
        builder.append(String.format("%s ", route.getDestination().getAirport().getAirportCode()));
        return builder.toString();
    }

    private void sort(List<ChargeRoute> list) {
        Collections.sort(list);
        this.sortInnerElements(list);
    }


    private void sortInnerElements(List<ChargeRoute> spendRoutes) {
        for (ChargeRoute chargeRoute : spendRoutes) {
            chargeRoute.sort();
        }
    }

    private boolean isShallowEquals(ChargeRoute expected, ChargeRoute actual) {
        if (expected == actual) return true;
        return Objects.equals(expected.getOrigin(), actual.getOrigin()) &&
                Objects.equals(expected.getDestination(), actual.getDestination()) &&
                Objects.equals(expected.getVia(), actual.getVia());
    }

    private boolean isDeepEquals(ChargeRoute expected, ChargeRoute actual) {
        return   Objects.equals(expected.getSegments(), actual.getSegments());
    }


}
