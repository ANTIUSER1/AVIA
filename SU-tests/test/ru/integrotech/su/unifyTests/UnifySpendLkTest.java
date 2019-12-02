package ru.integrotech.su.unifyTests;

import com.google.gson.JsonElement;

import org.junit.Assert;
import org.junit.Test;

import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.spend.SpendLkRoute;

import java.io.IOException;
import java.util.*;

/* Unify test for ru.aeroflot.fmc.io.output.spend.SpendLkRoute
 * to create new tests, follow next steps
 * 1. Create root path for spendLk unify tests in your local machine
 * 2. Define path to this directory in test.ru.aeroflot.fmc.resources.unifyTest.properties in uniSpendLkTestDirectory property,
 *    for example " uniSpendTestDirectory=D:/fpm_tests/spend_lk/ "
 * 3. Create test case directory inside root directory, for example "SVO-JFK-00"
 * 4. Put file "request.json" inside SVO-JFK-00 directory
 * 5. Put file "expectedResponse.json" inside SVO-JFK-00 directory
 * 6. Create another test directory, for example "SVO-NRT-01" and repeat steps 4 and 5 for this new directory.
 * 7. Run public void test(). Program test each folder, test is OK if actual response is equals to your expectedResponse.json
 *    Program will say OK if test is ok or write right answer in exact directory as "actualResponse.json".
 */
public class UnifySpendLkTest extends UnifyBaseTest {

    private static final String ROOT_TEST_DIRECTORY_PATH = "uniSpendLkTestDirectory";
    private static final String SUCCESS = "OK\n";
    private static final String INCORRECT = "INCORRECT\n";
    private static final String NOT_FOUND = "not found\n";
    private static final String EXTRA = "extra\n";

    public UnifySpendLkTest() {
        super(MockLoader.ofRealRegisters(), SpendLkRoute.class);
    }

    @Override
    protected boolean isCorrectCase(String pathToCaseFolder) throws IOException {
        JsonElement jsonElement = this.common.getLoader().loadJson(pathToCaseFolder, REQUEST_FILE_NAME);
        SpendInput spendInput = this.common.getTestsCache().loadSpendInputParams(jsonElement);
        List<SpendLkRoute> actualSpendLkRoutes = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);

        jsonElement = this.common.getLoader().loadJson(pathToCaseFolder, EXPECTED_RESPONSE_FILE_NAME);
        List<SpendLkRoute> expectedSpendLkRoutes = this.common.getTestsCache().loadSpendLkRoutes(jsonElement);

        String testReport = this.compareSpendRoute(expectedSpendLkRoutes, actualSpendLkRoutes);
        boolean result = testReport.contains(SUCCESS)
                && ( !testReport.contains(INCORRECT)
                &&!testReport.contains(NOT_FOUND)
                &&!testReport.contains(EXTRA));
        printTestResults(result, actualSpendLkRoutes, pathToCaseFolder);
        printReport(testReport, pathToCaseFolder);
        return result;
    }

    @Test
    public void test() {
        Assert.assertTrue(executeTest(ROOT_TEST_DIRECTORY_PATH));
    }


    private String compareSpendRoute(List<SpendLkRoute> expected, List<SpendLkRoute> actual) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%-23s     %s\n", " --- R O U T E ---", "RESULT"));
        builder.append("\n");
        this.sort(actual);
        this.sort(expected);
        ArrayList<SpendLkRoute> actualArr = new ArrayList<>(actual);
        ArrayList<SpendLkRoute> expectedlArr = new ArrayList<>(expected);
        Iterator<SpendLkRoute> expectedIterator = expectedlArr.iterator();
        expected: while (expectedIterator.hasNext()) {
            SpendLkRoute expectedRoute = expectedIterator.next();
            Iterator<SpendLkRoute> actualIterator = actualArr.iterator();
            while (actualIterator.hasNext()) {
                SpendLkRoute actualRoute = actualIterator.next();
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

        for (SpendLkRoute route : expectedlArr) {
            builder.append(String.format("%-25s     %s",
                    this.getRoteCode(route),
                    NOT_FOUND));
        }

        for (SpendLkRoute route : actualArr) {
            builder.append(String.format("%-25s     %s",
                    this.getRoteCode(route),
                    EXTRA));
        }

        return builder.toString();
    }

    private String getRoteCode(SpendLkRoute route) {
        String builder = String.format("%s  ", route.getOrigin().getAirport().getAirportCode()) +
                String.format("%s ", route.getDestination().getAirport().getAirportCode());
        return builder;
    }

    private void sort(List<SpendLkRoute> list) {
        Collections.sort(list);
        this.sortInnerElements(list);
    }


    private void sortInnerElements(List<SpendLkRoute> spendRoutes) {
        for (SpendLkRoute spendLkRoute : spendRoutes) {
            spendLkRoute.sort();
        }
    }

    private boolean isShallowEquals(SpendLkRoute expected, SpendLkRoute actual) {
        if (expected == actual) return true;
        return  Objects.equals(expected.getOrigin(), actual.getOrigin()) &&
                Objects.equals(expected.getDestination(), actual.getDestination());
    }

    private boolean isDeepEquals(SpendLkRoute expected, SpendLkRoute actual) {
        return  Objects.equals(expected.getRequiredAwards(), actual.getRequiredAwards());
    }



}
