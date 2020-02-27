package ru.integrotech.su.test;

import com.google.gson.*;

import org.junit.Assert;
import org.junit.Test;

import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.outputparams.spend.SpendBuilder;
import ru.integrotech.su.outputparams.spend.SpendLkRoute;

import java.io.IOException;
import java.util.List;

/*test for io.spend.SpendLkRoute*/
public class SpendLkTest {

    private static final String RESULTS_FOLDER = "test/ru/integrotech/su/resources/results/spendLkRoutes/";

    private final CommonTest common;

    public SpendLkTest() {
        this.common = CommonTest.of(SpendLkRoute.class, SpendBuilder.getRegisterNames());
    }

    private List<SpendLkRoute> getExpected(String jsonName) {
        List<SpendLkRoute> expectedSpendLkRoutes = null;
        try {
            JsonElement jsonElement = this.common.getLoader().loadJson(RESULTS_FOLDER + jsonName);
            expectedSpendLkRoutes = this.common.getTestsCache().loadSpendLkRoutes(jsonElement);
        } catch (JsonIOException
                | JsonSyntaxException
                | IOException e) {
            e.printStackTrace();
        }
        return expectedSpendLkRoutes;
    }

    /*
    ////////////////////////////////////////////
    //use this method for visualization actual//
    ////////////////////////////////////////////
    @Test
    public void PRINT() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "VVO", // to
                -1, // miles min
                50000, // miles max
                null, // class listOf service name
                "all", // award type
                true, // afl only
                null // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        this.common.sort(actualSpendLk);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(actualSpendLk);
        System.out.println(jsonResult);
    }


    //////////////////////////////////////////////
    //use this method for visualization expected//
    //////////////////////////////////////////////
    @Test
    public void PRINT_SAVED() {
        List<SpendLkRoute> expectedSpendLk = getExpected("LAX-NRT-00.json");
        this.common.sort(expectedSpendLk);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(expectedSpendLk);
        System.out.println(jsonResult);
    }
    */

    @Test
    public void MOW_LED_00() {
        SpendInput spendInput = SpendInput.of(
                "city", // from type
                "MOW",// from
                "city", // to type
                "LED", // to
                -1, // miles min
                500000, // miles max
                null, // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        String airlineCode = "SU";
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput, airlineCode);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("MOW-LED-00.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }


    @Test
    public void SVO_LED_01() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "LED", // to
                -1, // miles min
                100000, // miles max
                "economy", // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        String airlineCode = "SU";
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput, airlineCode);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-LED-01.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }


    @Test
    public void SVO_LED_02() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "LED", // to
                -1, // miles min
                100000, // miles max
                "economy", // class listOf service name
                "ticket", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-LED-02.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }

    @Test
    public void SVO_LED_03() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "LED", // to
                -1, // miles min
                100000, // miles max
                "economy", // class listOf service name
                "all", // award type
                true, // afl only
                true // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-LED-03.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }

    @Test
    public void SVO_LED_04() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "LED", // to
                -1, // miles min
                100000, // miles max
                "business", // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-LED-04.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }

    @Test
    public void SVO_LED_05() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "LED", // to
                -1, // miles min
                100000, // miles max
                null, // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-LED-05.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }

    @Test
    public void SVO_LED_06() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "LED", // to
                -1, // miles min
                100000, // miles max
                "business", // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-LED-06.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }

    @Test
    public void SVO_LED_07() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "LED", // to
                -1, // miles min
                15000, // miles max
                "economy", // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-LED-07.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }

    @Test
    public void SVO_VVO_01() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "VVO", // to
                -1, // miles min
                50000, // miles max
                null, // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-VVO-01.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }

    @Test
    public void SVO_VVO_02() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "VVO", // to
                -1, // miles min
                50000, // miles max
                null, // class listOf service name
                "all", // award type
                true, // afl only
                null // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-VVO-02.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }

    @Test
    public void SVO_CDG_00() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "CDG", // to
                -1, // miles min
                20000, // miles max
                null, // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-CDG-00.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }

    @Test
    public void SVO_CDG_01() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "CDG", // to
                -1, // miles min
                20000, // miles max
                null, // class listOf service name
                "ticket", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-CDG-01.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }

    @Test
    public void SVO_CDG_02() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "CDG", // to
                -1, // miles min
                20000, // miles max
                null, // class listOf service name
                "upgrade", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-CDG-02.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }

    @Test
    public void SVO_CDG_03() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "CDG", // to
                -1, // miles min
                10000, // miles max
                null, // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        Assert.assertTrue(actualSpendLk.isEmpty());
    }

    @Test
    public void SVO_JFK_00() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "JFK", // to
                -1, // miles min
                50000, // miles max
                null, // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-JFK-00.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }

    @Test
    public void LAX_NRT_00() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "LAX",// from
                "airport", // to type
                "NRT", // to
                -1, // miles min
                50000, // miles max
                null, // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("LAX-NRT-00.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }


    @Test
    public void SVO_WORLD_00() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                null, // to type
                null, // to
                -1, // miles min
                50000, // miles max
                null, // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-WORLD-00.json");
        this.common.testIsPresent(expectedSpendLk, actualSpendLk);
    }

    @Test
    public void SVO_WORLD_01() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                null, // to type
                null, // to
                -1, // miles min
                100000, // miles max
                null, // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-WORLD-01.json");
        this.common.testIsEquals(expectedSpendLk, actualSpendLk);
    }


    @Test
    public void SVO_RU_00() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "country", // to type
                "RU", // to
                -1, // miles min
                50000, // miles max
                null, // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-RU-00.json");
        this.common.testIsPresent(expectedSpendLk, actualSpendLk);

        expectedSpendLk = this.getExpected("SVO-CDG-00.json");
        this.common.testIsNotPresent(expectedSpendLk, actualSpendLk);

        expectedSpendLk = this.getExpected("SVO-JFK-00.json");
        this.common.testIsNotPresent(expectedSpendLk, actualSpendLk);

        expectedSpendLk = this.getExpected("LAX-NRT-00.json");
        this.common.testIsNotPresent(expectedSpendLk, actualSpendLk);
    }

    @Test
    public void SVO_CIS_00() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "region", // to type
                "CIS", // to
                -1, // miles min
                50000, // miles max
                null, // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        List<SpendLkRoute> actualSpendLk = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput);
        List<SpendLkRoute> expeSpendLkcted = this.getExpected("SVO-RU-00.json");
        this.common.testIsPresent(expeSpendLkcted, actualSpendLk);

        expeSpendLkcted = this.getExpected("SVO-CDG-00.json");
        this.common.testIsNotPresent(expeSpendLkcted, actualSpendLk);

        expeSpendLkcted = this.getExpected("SVO-JFK-00.json");
        this.common.testIsNotPresent(expeSpendLkcted, actualSpendLk);

        expeSpendLkcted = this.getExpected("LAX-NRT-00.json");
        this.common.testIsNotPresent(expeSpendLkcted, actualSpendLk);
    }

}
