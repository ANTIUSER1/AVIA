package ru.integrotech.su.test;

import com.google.gson.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.su.inputparams.route.RoutesInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.route.RoutesBuilder;
import ru.integrotech.su.outputparams.spend.SpendBuilder;
import ru.integrotech.su.records.RouteRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for RouteSearcher, used inner registers
 */

public class RouteSearcherTest {

    private static final String RESULTS_FOLDER = "test/ru/integrotech/su/resources/results/routes/";

    @BeforeClass
    public static void updateRegisters() {
        MockLoader.getInstance().updateRegisters(
                                        MockLoader.REGISTERS_TYPE.MOCK,
                                        SpendBuilder.getRegisterNames());
    }

    private Comparator comparator;

    private RoutesBuilder routesBuilder;

    @Before
    public void init() {
        this.comparator = Comparator.of(Route.class);
        this.routesBuilder = RoutesBuilder.of(MockLoader.getInstance().getRegisterCache());
    }

    private List<Route> getExpected(String jsonName) {
        MockLoader loader = MockLoader.getInstance();
        List<Route> result = null;
        try {
            JsonElement jsonElement = loader.loadJson(RESULTS_FOLDER + jsonName);
            result = loader.getTestsCache().loadRoutes(jsonElement, MockLoader.getInstance().getRegisterCache());
        } catch (JsonIOException
                | JsonSyntaxException
                | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    ////////////////////////////////////////////
    //use this method for visualization actual//
    ////////////////////////////////////////////
    @Test
    public void PRINT_ACTUAL() {
        RoutesInput routesInput = RoutesInput.of(
                "city", // from type
                "LED", // from
                null, // to type
                null, // to
                null // airline
        );
        List<Route> actualRoutes = this.routesBuilder.getRoutes(routesInput);
        this.comparator.sort(actualRoutes);
        List<RouteRecord> records = new ArrayList<>();
        for (Route route : actualRoutes) {
            records.add(RouteRecord.of(route));
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(records);
        System.out.println(jsonResult);
    }

    //////////////////////////////////////////////
    //use this method for visualization expected//
    //////////////////////////////////////////////
    @Test
    public void PRINT_EXPECTED() {
        List<Route> expectedRoutes = this.getExpected("LED-WORLD.json");
        this.comparator.sort(expectedRoutes);
        List<RouteRecord> records = new ArrayList<>();
        for (Route route : expectedRoutes) {
            records.add(RouteRecord.of(route));
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(records);
        System.out.println(jsonResult);
    }

    // http://support.integrotechnologies.ru/issues/21958
    @Test
    public void MOW_VVO() {
        RoutesInput routesInput = RoutesInput.of(
                "city", // from type
                "MOW", // from
                "city", // to type
                "VVO", // to
                "SU" // airline

        );
        List<Route> actualRoutes = this.routesBuilder.getRoutes(routesInput);
        List<Route> expectedRoutes = this.getExpected("MOW-VVO.json");
        this.comparator.testIsEquals(expectedRoutes, actualRoutes);
    }


    // http://support.integrotechnologies.ru/issues/21958
    @Test
    public void MOW_CIS() {
        RoutesInput routesInput = RoutesInput.of(
                "city", // from type
                "MOW", // from
                "region", // to type
                "CIS", // to
                "SU" // airline
        );
        List<Route> actualRoutes = this.routesBuilder.getRoutes(routesInput);

        List<Route> expectedRoutes = this.getExpected("MOW-CIS.json");
        this.comparator.testIsPresent(expectedRoutes, actualRoutes);

        List<Route> notExpectedRoutes = this.getExpected("MOW-EU.json");
        this.comparator.testIsNotPresent(notExpectedRoutes, actualRoutes);
    }

    @Test
    public void MOW_EU() {
        RoutesInput routesInput = RoutesInput.of(
                "city", // from type
                "MOW", // from
                "region", // to type
                "EU", // to
                "SU" // airline
        );
        List<Route> actualRoutes = this.routesBuilder.getRoutes(routesInput);

        List<Route> expectedRoutes = this.getExpected("MOW-EU.json");
        this.comparator.testIsPresent(expectedRoutes, actualRoutes);

        List<Route> notExpectedRoutes = this.getExpected("MOW-CIS.json");
        this.comparator.testIsNotPresent(notExpectedRoutes, actualRoutes);
    }

    @Test
    public void SVO_GMP() {
        RoutesInput routesInput = RoutesInput.of(
                "airport", // from type
                "SVO", // from
                "airport", // to type
                "GMP", // to
                "SU" // airline

        );
        List<Route> actualRoutes = this.routesBuilder.getRoutes(routesInput);
        List<Route> notExpectedRoutes = new ArrayList<>();
        this.comparator.testIsNotPresent(notExpectedRoutes, actualRoutes);
    }

    @Test
    public void SVO_NRT() {
        RoutesInput routesInput = RoutesInput.of(
                "airport", // from type
                "SVO", // from
                "airport", // to type
                "NRT", // to
                "SU" // airline

        );
        List<Route> actualRoutes = this.routesBuilder.getRoutes(routesInput);

        List<Route> expectedRoutes = this.getExpected("SVO-NRT.json");
        this.comparator.testIsEquals(expectedRoutes, actualRoutes);

        List<Route> notExpectedRoutes = this.getExpected("MOW-VVO.json");
        this.comparator.testIsNotPresent(notExpectedRoutes, actualRoutes);
    }

    @Test
    public void SVO_PRG() {
        RoutesInput routesInput = RoutesInput.of(
                "airport", // from type
                "SVO", // from
                "airport", // to type
                "PRG", // to
                null // airline
        );
        List<Route> actualRoutes = this.routesBuilder.getRoutes(routesInput);

        List<Route> expectedRoutes = this.getExpected("SVO-PRG.json");
        this.comparator.testIsEquals(expectedRoutes, actualRoutes);

        List<Route> notExpectedRoutes = this.getExpected("SVO-FRA.json");
        this.comparator.testIsNotPresent(notExpectedRoutes, actualRoutes);
    }

    @Test
    public void SVO_FRA() {
        RoutesInput routesInput = RoutesInput.of(
                "airport", // from type
                "SVO", // from
                "airport", // to type
                "FRA", // to
                null // airline
        );
        List<Route> actualRoutes = this.routesBuilder.getRoutes(routesInput);

        List<Route> expectedRoutes = this.getExpected("SVO-FRA.json");
        this.comparator.testIsEquals(expectedRoutes, actualRoutes);

        List<Route> notExpectedRoutes = this.getExpected("SVO-PRG.json");
        this.comparator.testIsNotPresent(notExpectedRoutes, actualRoutes);
    }

    @Test
    public void LED_AER() {
        RoutesInput routesInput = RoutesInput.of(
                "airport", // from type
                "LED", // from
                "airport", // to type
                "AER", // to
                "SU" // airline
        );
        List<Route> actualRoutes = this.routesBuilder.getRoutes(routesInput);
        List<Route> expectedRoutes = this.getExpected("LED-AER.json");
        this.comparator.testIsEquals(expectedRoutes, actualRoutes);
    }

    @Test
    public void LED_CIS() {
        RoutesInput routesInput = RoutesInput.of(
                "airport", // from type
                "LED", // from
                "region", // to type
                "CIS", // to
                "SU" // airline
        );
        List<Route> actualRoutes = this.routesBuilder.getRoutes(routesInput);
        List<Route> expectedRoutes = this.getExpected("LED-CIS.json");
        this.comparator.testIsPresent(expectedRoutes, actualRoutes);
    }

    @Test
    public void MOW_WORLD() {
        RoutesInput routesInput = RoutesInput.of(
                "city", // from type
                "MOW", // from
                null, // to type
                null, // to
                null // airline
        );
        List<Route> actualRoutes = this.routesBuilder.getRoutes(routesInput);

        List<Route> expectedRoutes = this.getExpected("MOW-WORLD.json");
        this.comparator.testIsEquals(expectedRoutes, actualRoutes);

        expectedRoutes = this.getExpected("LED-WORLD.json");
        this.comparator.testIsNotPresent(expectedRoutes, actualRoutes);
    }
}
