package ru.integrotech.su.unifyTests;

import com.google.gson.JsonElement;

import org.junit.Assert;
import org.junit.Test;

import ru.integrotech.su.inputparams.charge.ChargeInput;
import ru.integrotech.su.mock.MockLoader;
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

    @Override
    protected boolean isCorrectCase(String pathToCaseFolder) throws IOException {
        JsonElement jsonElement = this.common.getLoader().loadJson(pathToCaseFolder, REQUEST_FILE_NAME);
        ChargeInput chargeInput = this.common.getTestsCache().loadChargeInputParams(jsonElement);
        List<ChargeRoute> actualChargeRoutes = this.common.getChargeBuilder().getChargeRoutes(chargeInput);

        jsonElement = this.common.getLoader().loadJson(pathToCaseFolder, EXPECTED_RESPONSE_FILE_NAME);
        List<ChargeRoute> expectedChargeRoutes = this.common.getTestsCache().loadChargeRoutes(jsonElement);

        String testReport = this.compareSpendRoute(expectedChargeRoutes, actualChargeRoutes);
        boolean result = testReport.contains(SUCCESS)
                && ( !testReport.contains(INCORRECT)
                &&!testReport.contains(NOT_FOUND)
                &&!testReport.contains(EXTRA));
        printTestResults(result, actualChargeRoutes, pathToCaseFolder);
        printReport(testReport, pathToCaseFolder);
        return result;
    }

    @Test
    public void test() {
        Assert.assertTrue(executeTest(ROOT_TEST_DIRECTORY_PATH));
    }

    private String compareSpendRoute(List<ChargeRoute> expected, List<ChargeRoute> actual) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%-23s     %s\n", " --- R O U T E ---", "RESULT"));
        builder.append("\n");
        this.sort(actual);
        this.sort(expected);
        ArrayList<ChargeRoute> actualArr = new ArrayList<>(actual);
        ArrayList<ChargeRoute> expectedlArr = new ArrayList<>(expected);
        Iterator<ChargeRoute> expectedIterator = expectedlArr.iterator();
        expected: while (expectedIterator.hasNext()) {
            ChargeRoute expectedRoute = expectedIterator.next();
            Iterator<ChargeRoute> actualIterator = actualArr.iterator();
            while (actualIterator.hasNext()) {
                ChargeRoute actualRoute = actualIterator.next();
                if (this.isShallowEquals(expectedRoute, actualRoute)) {
                    if (this.isDeepEquals(expectedRoute, actualRoute)) {
                        builder.append(String.format("%-25s     %s",
                                this.getRoteCode(actualRoute),
                                SUCCESS));
                    } else {
                        builder.append(String.format("%-25s     %s",
                                this.getRoteCode(actualRoute),
                                INCORRECT));
                    }
                    expectedIterator.remove();
                    actualIterator.remove();
                    continue expected;
                }
            }
        }

        for (ChargeRoute route : expectedlArr) {
            builder.append(String.format("%-25s     %s",
                    this.getRoteCode(route),
                    NOT_FOUND));
        }

        for (ChargeRoute route : actualArr) {
            builder.append(String.format("%-25s     %s",
                    this.getRoteCode(route),
                    EXTRA));
        }

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
