package ru.integrotech.su.unifyTests;

import com.google.gson.JsonElement;

import org.junit.Assert;
import org.junit.Test;

import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.spend.SpendLkRoute;

import java.io.IOException;
import java.util.List;

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

        boolean result = this.common.isEquals(expectedSpendLkRoutes, actualSpendLkRoutes);
        printTestResults(result, actualSpendLkRoutes, pathToCaseFolder);
        return result;
    }

    @Test
    public void test() {
        Assert.assertTrue(executeTest(ROOT_TEST_DIRECTORY_PATH));
    }

}
