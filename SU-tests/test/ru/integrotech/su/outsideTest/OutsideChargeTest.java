package ru.integrotech.su.outsideTest;

import com.google.gson.JsonElement;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.su.inputparams.charge.ChargeInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.charge.ChargeBuilder;
import ru.integrotech.su.outputparams.charge.ChargeRoute;
import ru.integrotech.su.unifyTest.UnifyChargeTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class OutsideChargeTest extends UnifyChargeTest {

    private static final String ROOT_TEST_DIRECTORY_PATH = "outChargeTestDirectory";
    private static final String SUCCESS = "OK\n";
    private static final String INCORRECT = "INCORRECT\n";
    private static final String NOT_FOUND = "not found\n";
    private static final String EXTRA = "extra\n";

    @BeforeClass
    public static void updateRegisters() {
        MockLoader.getInstance().updateRegisters(
                                    MockLoader.REGISTERS_TYPE.REAL,
                                    ChargeBuilder.getRegisterNames());
    }

    @Override
    protected boolean isCorrectCase(String pathToCaseFolder) throws IOException {
        MockLoader loader = MockLoader.getInstance();
        JsonElement jsonElement = loader.loadJson(pathToCaseFolder + REQUEST_FILE_NAME);
        ChargeInput chargeInput = loader.getTestsCache().loadChargeInputParams(jsonElement);

        jsonElement = loader.loadJson(pathToCaseFolder + ACTUAL_RESPONSE_FILE_NAME);
        List<ChargeRoute> actualChargeRoutes = loader.getTestsCache().loadChargeRoutes(jsonElement);

        jsonElement = loader.loadJson(pathToCaseFolder + EXPECTED_RESPONSE_FILE_NAME);
        List<ChargeRoute> expectedChargeRoutes = loader.getTestsCache().loadChargeRoutes(jsonElement);

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

}
