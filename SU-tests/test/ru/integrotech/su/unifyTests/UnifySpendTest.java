package ru.integrotech.su.unifyTests;

import com.google.gson.JsonElement;
import org.junit.Assert;
import org.junit.Test;
import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.spend.SpendRoute;

import java.io.IOException;
import java.util.*;

/* Unify test for ru.aeroflot.fmc.io.spend
 * to create new tests, follow next steps
 * 1. Create root path for spend unify tests in your local machine
 * 2. Define path to this directory in test.ru.aeroflot.fmc.resources.unifyTest.properties in uniSpendTestDirectory property,
 *    for example " uniSpendTestDirectory=D:/fpm_tests/spend/ "
 * 3. Create test case directory inside root directory, for example "SVO-VVO-01"
 * 4. Put file "request.json" inside SVO-VVO-01 directory
 * 5. Put file "expectedResponse.json" inside SVO-VVO-01 directory
 * 6. Create another test directory, for example "LED-KJA-01" and repeat steps 4 and 5 for this new directory.
 * 7. Run public void test(). Program test each folder, test is OK if actual response is equals to your expectedResponse.json
 * 8. Program will say OK if test is ok or write right answer in exact directory as "actualResponse.json".
 * 9. Program will write report about each route in test case and put it in the same directory.
 */
public class UnifySpendTest extends UnifyBaseTest {

    private static final String ROOT_TEST_DIRECTORY_PATH = "uniSpendTestDirectory";
    private static final String SUCCESS = "OK\n";
    private static final String INCORRECT = "INCORRECT\n";
    private static final String NOT_FOUND = "not found\n";
    private static final String EXTRA = "extra\n";

    public UnifySpendTest() {
        super(MockLoader.ofRealRegisters(), SpendRoute.class);
    }

    @Override
    protected boolean isCorrectCase(String pathToCaseFolder) throws IOException {
        JsonElement jsonElement = this.common.getLoader().loadJson(pathToCaseFolder, REQUEST_FILE_NAME);
        SpendInput spendInput = this.common.getTestsCache().loadSpendInputParams(jsonElement);
        List<SpendRoute> actualSpendRoutes = this.common.getSpendBuilder().getSpendRoutes(spendInput);

        jsonElement = this.common.getLoader().loadJson(pathToCaseFolder, EXPECTED_RESPONSE_FILE_NAME);
        List<SpendRoute> expectedSpendRoutes = this.common.getTestsCache().loadSpendRoutes(jsonElement);

        String testReport = this.compareSpendRoute(expectedSpendRoutes, actualSpendRoutes);
        boolean result = testReport.contains(SUCCESS)
                    && ( !testReport.contains(INCORRECT)
                         &&!testReport.contains(NOT_FOUND)
                         &&!testReport.contains(EXTRA));
        printTestResults(result, actualSpendRoutes, pathToCaseFolder);
        printReport(testReport, pathToCaseFolder);
        return result;
    }

    @Test
    public void test() {
        Assert.assertTrue(executeTest(ROOT_TEST_DIRECTORY_PATH));
    }

    private String compareSpendRoute(List<SpendRoute> expected, List<SpendRoute> actual) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%-23s     %s\n", " --- R O U T E ---", "RESULT"));
        builder.append("\n");
        this.sort(actual);
        this.sort(expected);
        ArrayList<SpendRoute> actualArr = new ArrayList<>(actual);
        ArrayList<SpendRoute> expectedlArr = new ArrayList<>(expected);
        Iterator<SpendRoute> expectedIterator = expectedlArr.iterator();
        expected: while (expectedIterator.hasNext()) {
            SpendRoute expectedRoute = expectedIterator.next();
            Iterator<SpendRoute> actualIterator = actualArr.iterator();
            while (actualIterator.hasNext()) {
                SpendRoute actualRoute = actualIterator.next();
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

        for (SpendRoute route : expectedlArr) {
            builder.append(String.format("%-25s     %s",
                    this.getRoteCode(route),
                    NOT_FOUND));
        }

        for (SpendRoute route : actualArr) {
            builder.append(String.format("%-25s     %s",
                    this.getRoteCode(route),
                    EXTRA));
        }

        return builder.toString();
    }

    private String getRoteCode(SpendRoute route) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s  ", route.getOrigin().getAirport().getAirportCode()));
        if (route.getVia() != null) {
            builder.append(String.format("%s  ", route.getVia().getAirport().getAirportCode()));
        } else {
            builder.append(String.format("%s  ", "   "));
        }
        builder.append(String.format("%s ", route.getDestination().getAirport().getAirportCode()));
        builder.append(String.format("  %s", route.isAfl() ? "afl" : "scyteam"));
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

    private boolean isShallowEquals(SpendRoute expected, SpendRoute actual) {
        if (expected == actual) return true;
        return  expected.isAfl() == actual.isAfl() &&
                expected.isSingle() == actual.isSingle() &&
                Objects.equals(expected.getOrigin(), actual.getOrigin()) &&
                Objects.equals(expected.getDestination(), actual.getDestination()) &&
                Objects.equals(expected.getVia(), actual.getVia());
    }

    private boolean isDeepEquals(SpendRoute expected, SpendRoute actual) {
        return  Objects.equals(expected.getAirlines(), actual.getAirlines()) &&
                Objects.equals(expected.getMileCosts(), actual.getMileCosts());
    }



}
