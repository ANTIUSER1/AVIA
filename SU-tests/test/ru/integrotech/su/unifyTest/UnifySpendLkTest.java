package ru.integrotech.su.unifyTest;

import com.google.gson.JsonElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.spend.SpendBuilder;
import ru.integrotech.su.outputparams.spend.SpendLkBuilder;
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

    @BeforeClass
    public static void updateRegisters() {
        MockLoader.getInstance().updateRegisters(
                                    MockLoader.REGISTERS_TYPE.REAL,
                                    SpendBuilder.getRegisterNames());
    }

    private SpendLkBuilder spendLkBuilder;

    @Before
    public void init() {
        SpendBuilder spendBuilder = SpendBuilder.of(MockLoader.getInstance().getRegisterCache());
        this.spendLkBuilder = SpendLkBuilder.of(spendBuilder);
    }

    @Override
    protected boolean isCorrectCase(String pathToCaseFolder) throws IOException {
        MockLoader loader = MockLoader.getInstance();
        JsonElement jsonElement = loader.loadJson(pathToCaseFolder + REQUEST_FILE_NAME);
        SpendInput spendInput = loader.getTestsCache().loadSpendInputParams(jsonElement);
        List<SpendLkRoute> actualSpendLkRoutes = this.spendLkBuilder.getSpendLkRoutes(spendInput);

        jsonElement = loader.loadJson(pathToCaseFolder + EXPECTED_RESPONSE_FILE_NAME);
        List<SpendLkRoute> expectedSpendLkRoutes = loader.getTestsCache().loadSpendLkRoutes(jsonElement);

        String testHeader = this.buildReportHeader(spendInput);
        String testBody = this.compareSpendRoute(expectedSpendLkRoutes, actualSpendLkRoutes);
        boolean result = testBody.contains(SUCCESS)
                && ( !testBody.contains(INCORRECT)
                &&!testBody.contains(NOT_FOUND)
                &&!testBody.contains(EXTRA));
        printTestResults(result, actualSpendLkRoutes, pathToCaseFolder);
        String testReport = String.format("%s%s", testHeader, testBody);
        printReport(testReport, pathToCaseFolder);
        return result;
    }

    @Test
    public void test() {
        Assert.assertTrue(executeTest(ROOT_TEST_DIRECTORY_PATH));
    }

    protected String buildReportHeader(SpendInput spendInput) {
        StringBuilder builder = new StringBuilder();
        builder.append(" ---------- SPEND LK TEST -----------\n");
        builder.append("|  Parameters:                       |\n");

        //origin block
        builder.append("|------------------------------------|\n");
        builder.append("|  Origin:                           |\n");
        builder.append(String.format("|  %-20s %-10s   |\n",
                "location type:",
                spendInput.getOrigin().getLocationType()));
        builder.append(String.format("|  %-20s %-10s   |\n",
                "location code:",
                spendInput.getOrigin().getLocationCode()));

        //destination block
        builder.append("|------------------------------------|\n");
        builder.append("|  Destination:                      |\n");
        builder.append(String.format("|  %-20s %-10s   |\n",
                "location type:",
                spendInput.getDestination().getLocationType()));
        builder.append(String.format("|  %-20s %-10s   |\n",
                "location code:",
                spendInput.getDestination().getLocationCode()));

        //miles interval block
        builder.append("|------------------------------------|\n");
        builder.append("|  Miles interval:                   |\n");
        builder.append(String.format("|  %-20s %,-10d   |\n",
                "miles min:",
                spendInput.getMilesInterval().getMilesMin()));
        builder.append(String.format("|  %-20s %,-10d   |\n",
                "miles max:",
                spendInput.getMilesInterval().getMilesMax()));

        //other values block
        builder.append("|------------------------------------|\n");
        builder.append(String.format("|  %-20s %-10s   |\n",
                "Is only afl:", spendInput.getIsOnlyAfl()));
        builder.append(String.format("|  %-20s %-10s   |\n",
                "Is round trip:", spendInput.getIsRoundTrip()));
        String classOfServiceName = "null";
        if (spendInput.getClassOfService() != null) {
            classOfServiceName = spendInput.getClassOfService().getClassOfServiceName();
        }
        builder.append(String.format("|  %-20s %-10s   |\n",
                "Class of service:", classOfServiceName));
        builder.append(String.format("|  %-20s %-10s   |\n",
                "Award type:", spendInput.getAwardType()));
        builder.append(" ------------------------------------\n");
        builder.append("\n");

        return builder.toString();
    }

    protected String compareSpendRoute(List<SpendLkRoute> expected, List<SpendLkRoute> actual) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%-26s     %s\n", " - R O U T E -", "RESULT"));
        builder.append("\n");
        this.sort(actual);
        this.sort(expected);
        ArrayList<SpendLkRoute> actualArr = new ArrayList<>(actual);
        ArrayList<SpendLkRoute> expectedlArr = new ArrayList<>(expected);
        int successCounter = 0;
        int incorrectCounter = 0;
        Iterator<SpendLkRoute> expectedIterator = expectedlArr.iterator();
        expected: while (expectedIterator.hasNext()) {
            SpendLkRoute expectedRoute = expectedIterator.next();
            Iterator<SpendLkRoute> actualIterator = actualArr.iterator();
            while (actualIterator.hasNext()) {
                SpendLkRoute actualRoute = actualIterator.next();
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

        for (SpendLkRoute route : expectedlArr) {
            builder.append(String.format("   %-22s     %s",
                    this.getRoteCode(route),
                    NOT_FOUND));
        }

        for (SpendLkRoute route : actualArr) {
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
