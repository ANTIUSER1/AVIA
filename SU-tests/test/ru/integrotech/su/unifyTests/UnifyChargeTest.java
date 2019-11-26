package ru.integrotech.su.unifyTests;

import com.google.gson.JsonElement;

import org.junit.Assert;
import org.junit.Test;

import ru.integrotech.su.inputparams.charge.ChargeInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.charge.ChargeRoute;

import java.io.IOException;
import java.util.List;

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

        boolean result = this.common.isEquals(expectedChargeRoutes, actualChargeRoutes);
        printTestResults(result, actualChargeRoutes, pathToCaseFolder);
        return result;
    }

    @Test
    public void test() {
        Assert.assertTrue(executeTest(ROOT_TEST_DIRECTORY_PATH));
    }

}
