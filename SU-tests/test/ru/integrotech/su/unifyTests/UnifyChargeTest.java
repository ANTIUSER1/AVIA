package ru.integrotech.su.unifyTests;

import com.google.gson.JsonElement;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.integrotech.airline.register.RegisterLoader;
import ru.integrotech.su.common.Location;
import ru.integrotech.su.inputparams.charge.ChargeInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.charge.ChargeBuilder;
import ru.integrotech.su.outputparams.charge.ChargeRoute;

import java.io.IOException;
import java.util.*;

/*Unify test for ru.aeroflot.fmc.io.charge
* to create new tests, follow next steps
* 1. Create root path for ChargeRoute unify tests in your local machine
* 2. Define path to this directory in test.ru.aeroflot.fmc.resources.unifyTest.properties in uniChargeTestDirectory property,
*    for example " uniChargeTestDirectory=D:/fpm_tests/charge/ "
* 3. Create test case directory inside root directory, for example "SVO-LED-00"
* 4. Put file "request.json" inside SVO-LED-00 directory
* 5. Put file "expectedResponse.json" inside SVO-LED-00 directory
* 6. Create another test directory, for example "LED-UFA-00" and repeat steps 4 and 5 for this new directory.
* 7. Run public void test(). Program test each folder, test is OK if actual response is equals to your expectedResponse.json
*    Program will say OK if test is ok or write right answer in exact directory as "actualResponse.json".
 */

public class UnifyChargeTest extends UnifyBaseTest {

    private static final String ROOT_TEST_DIRECTORY_PATH = "uniChargeTestDirectory";
    private static final String SUCCESS = "OK\n";
    private static final String INCORRECT = "INCORRECT\n";
    private static final String NOT_FOUND = "not found\n";
    private static final String EXTRA = "extra\n";

    public UnifyChargeTest() {
        super(MockLoader.ofRealRegisters(), ChargeRoute.class);
    }

    @BeforeClass
    public static void updateRegisters() {
        RegisterLoader.updateInstance(ChargeBuilder.getRegisterNames());
    }

    @Override
    protected boolean isCorrectCase(String pathToCaseFolder) throws IOException {
        JsonElement jsonElement = this.common.getLoader().loadJson(pathToCaseFolder + REQUEST_FILE_NAME);
        ChargeInput chargeInput = this.common.getTestsCache().loadChargeInputParams(jsonElement);
        List<ChargeRoute> actualChargeRoutes = this.common.getChargeBuilder().getChargeRoutes(chargeInput);

        jsonElement = this.common.getLoader().loadJson(pathToCaseFolder + EXPECTED_RESPONSE_FILE_NAME);
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

    private String printLocation(Location location) {
        StringBuilder builder = new StringBuilder();
        if (location.getAirport() != null) {
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
