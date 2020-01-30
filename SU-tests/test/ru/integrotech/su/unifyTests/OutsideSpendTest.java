package ru.integrotech.su.unifyTests;

import com.google.gson.JsonElement;
import org.junit.Assert;
import org.junit.Test;
import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.spend.SpendRoute;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
public class OutsideSpendTest extends UnifySpendTest {

    private static final String ROOT_TEST_DIRECTORY_PATH = "outSpendTestDirectory";

    public OutsideSpendTest() {
        super(MockLoader.ofMockRegisters());
    }

    @Override
    protected boolean isCorrectCase(String pathToCaseFolder) throws IOException {
        JsonElement jsonElement = this.common.getLoader().loadJson(pathToCaseFolder, REQUEST_FILE_NAME);
        SpendInput spendInput = this.common.getTestsCache().loadSpendInputParams(jsonElement);

        jsonElement = this.common.getLoader().loadJson(pathToCaseFolder, ACTUAL_RESPONSE_FILE_NAME);
        List<SpendRoute> actualSpendRoutes = this.common.getTestsCache().loadSpendRoutes(jsonElement);

        jsonElement = this.common.getLoader().loadJson(pathToCaseFolder, EXPECTED_RESPONSE_FILE_NAME);
        List<SpendRoute> expectedSpendRoutes = this.common.getTestsCache().loadSpendRoutes(jsonElement);

        String testHeader = this.buildReportHeader(spendInput);
        String testBody = this.compareSpendRoute(expectedSpendRoutes, actualSpendRoutes);
        boolean result = testBody.contains(SUCCESS)
                    && ( !testBody.contains(INCORRECT)
                         &&!testBody.contains(NOT_FOUND)
                         &&!testBody.contains(EXTRA));
        printTestResults(result, actualSpendRoutes, pathToCaseFolder);
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
}
