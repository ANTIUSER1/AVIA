package ru.integrotech.su.test;

import com.google.gson.*;

import org.junit.Assert;
import org.junit.Test;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.airline.Tariff;
import ru.integrotech.su.inputparams.charge.ChargeInput;
import ru.integrotech.su.outputparams.charge.ChargeRoute;
import ru.integrotech.su.outputparams.charge.ChargeUtil;

import java.io.IOException;
import java.util.*;

/*test for io.ChargeRoute*/
public class ChargeTest{

    private static final String RESULTS_FOLDER = "/results/chargeRoutes/";

    private final CommonTest common;

    public ChargeTest() {
        this.common = CommonTest.of(ChargeRoute.class);
    }

    private List<ChargeRoute> getExpected(String jsonName) {
        List<ChargeRoute> result = null;
        try {
            JsonElement jsonElement = this.common.getLoader().loadJson(RESULTS_FOLDER + jsonName);
            result = this.common.getTestsCache().loadChargeRoutes(jsonElement);
        } catch (JsonIOException
                | JsonSyntaxException
                | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private int getMinMilesCharge(List<ChargeRoute> actualChargeRoutes) {
        int result = Integer.MAX_VALUE;
        for (ChargeRoute chargeRoute : actualChargeRoutes) {
            if (ChargeUtil.getMinMilesCharge(chargeRoute) < result) {
                result = ChargeUtil.getMinMilesCharge(chargeRoute);
            }
        }
        return result;
    }

    private void testReverseOrderTariffs(List<ChargeRoute> chargeRoutes, String airlineCode) {

        for (ChargeRoute chargeRoute : chargeRoutes) {

            Map<String, List<String>> actual = ChargeUtil.getTariffMap(chargeRoute);

            Map<String, Tariff> tariffMap = new HashMap<>();
            Airline airline = this.common.getTestsCache().getAirline(airlineCode);
            for (Map.Entry<ServiceClass.SERVICE_CLASS_TYPE, ServiceClass> serviceClassEntry : airline.getServiceClassMap().entrySet()) {
                tariffMap.putAll(serviceClassEntry.getValue().getTariffMap());
            }

            for (Map.Entry<String, List<String>> serviceClass : actual.entrySet()) {
                List<String> tariffCodes = serviceClass.getValue();
                for (int i = 0; i < tariffCodes.size() - 1; i++) {
                    Tariff current = tariffMap.get(tariffCodes.get(i));
                    Tariff next = tariffMap.get(tariffCodes.get(i + 1));
                    Assert.assertTrue(current.compareTo(next) > 0);
                }
            }
        }
    }

    private void testReverseOrderServiceClasses(List<ChargeRoute> chargeRoutes, String airlineCode) {

        for (ChargeRoute chargeRoute : chargeRoutes) {

            Airline airline = this.common.getTestsCache().getAirline(airlineCode);
            Map<ServiceClass.SERVICE_CLASS_TYPE, ServiceClass> serviceMap = airline.getServiceClassMap();
            List<String> actual = new ArrayList<>(ChargeUtil.getClassOfServiceCodes(chargeRoute));

            for (int i = 0; i < actual.size() - 1 ; i++) {
                ServiceClass current = serviceMap.get(ServiceClass.SERVICE_CLASS_TYPE.valueOf(actual.get(i)));
                ServiceClass next = serviceMap.get(ServiceClass.SERVICE_CLASS_TYPE.valueOf(actual.get(i + 1)));
                Assert.assertTrue((current.compareTo(next)) > 0);
            }
        }
    }

    private void testNaturalOrderFarePrefixes(List<ChargeRoute> chargeRoutes) {
        for (ChargeRoute chargeRoute : chargeRoutes) {
            List<List<String>> prefList = ChargeUtil.getAllPrefixes(chargeRoute);
            for (List<String> prefixes : prefList) {
                for (int i = 0; i < prefixes.size() - 1; i++) {
                    String current = prefixes.get(i);
                    String next = prefixes.get(i + 1);
                    Assert.assertTrue((current.compareTo(next)) < 0);

                }
            }
        }
    }

    private boolean classOfServiceIsPresent(List<ChargeRoute> actualChargeRoutes, String serviceClassCode) {
        for (ChargeRoute chargeRoute : actualChargeRoutes) {
            Set<String> allServiceClassCodes = ChargeUtil.getClassOfServiceCodes(chargeRoute);
            if (allServiceClassCodes.contains(serviceClassCode)) return true;
        }
        return false;
    }

    /*
    ////////////////////////////////////////////
    //use this method for visualization actual//
    ////////////////////////////////////////////
    @Test
    public void PRINT_ACTUAL() {
        ChargeInput chargeInput = ChargeInput.of(
                "airport", // from type
                "SVO", // from
                null, // to type
                null, // to
                "SU", // airline
                "basic", // tierLevelCode
                false //isRound
                );
        List<ChargeRoute> actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        this.common.sort(actualCharge);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(actualCharge);
        System.out.println(jsonResult);
    }


    ////////////////////////////////////////////
    //use this method for visualization expected//
    ////////////////////////////////////////////
    @Test
    public void PRINT_EXPECTED() {
        List<ChargeRoute> expectedCharge = getExpected("SVO-WORLD-00.json");
        this.common.sort(expectedCharge);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(expectedCharge);
        System.out.println(jsonResult);
    }
    */

    @Test
    public void SVO_LED_00() {
        ChargeInput chargeInput = ChargeInput.of(
                "airport", // from type
                "SVO", // from
                "airport", // to type
                "LED", // to
                "SU", // airline
                "gold", // tierLevelCode
                false //isRound
                );
        List<ChargeRoute> actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        List<ChargeRoute> expectedCharge = this.getExpected("SVO-LED-00.json");
        this.common.testIsPresent(actualCharge, expectedCharge.get(0));
    }

    @Test
    public void LED__UFA_00() {
        ChargeInput chargeInput = ChargeInput.of(
                "airport", // from type
                "LED", // from
                "airport", // to type
                "UFA", // to
                "SU", // airline
                "gold", // tierLevelCode
                false //isRound
                );
        List<ChargeRoute> actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        List<ChargeRoute> expectedCharge = this.getExpected("LED-UFA-00.json");
        this.common.testIsPresent(actualCharge, expectedCharge.get(0));
    }

    // http://support.integrotechnologies.ru/issues/20896
    // service class restrictions
    @Test
    public void COMFORT_CLASS_RESTRICTIONS() {

        List<ChargeRoute> actualCharge;

        ChargeInput chargeInput = ChargeInput.of(
                "airport", // from type
                "SVO", // from
                "airport", // to type
                "CDG", // to
                "SU", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        Assert.assertTrue(!classOfServiceIsPresent(actualCharge, ServiceClass.SERVICE_CLASS_TYPE.comfort.name()));

        chargeInput = ChargeInput.of(
                "airport", // from type
                "SVO", // from
                "airport", // to type
                "ROV", // to
                "SU", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        Assert.assertTrue(!classOfServiceIsPresent(actualCharge, ServiceClass.SERVICE_CLASS_TYPE.comfort.name()));

        chargeInput = ChargeInput.of(
                "airport", // from type
                "SVO", // from
                "airport", // to type
                "PRG", // to
                "SU", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        Assert.assertTrue(!classOfServiceIsPresent(actualCharge, ServiceClass.SERVICE_CLASS_TYPE.comfort.name()));

        chargeInput = ChargeInput.of(
                "airport", // from type
                "SVO", // from
                "airport", // to type
                "VVO", // to
                "SU", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        Assert.assertTrue(classOfServiceIsPresent(actualCharge, ServiceClass.SERVICE_CLASS_TYPE.comfort.name()));

        chargeInput = ChargeInput.of(
                "airport", // from type
                "SVO", // from
                "airport", // to type
                "JFK", // to
                "SU", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        Assert.assertTrue(classOfServiceIsPresent(actualCharge, ServiceClass.SERVICE_CLASS_TYPE.comfort.name()));

        chargeInput = ChargeInput.of(
                "airport", // from type
                "SVO", // from
                "airport", // to type
                "ICN", // to
                "SU", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        Assert.assertTrue(classOfServiceIsPresent(actualCharge, ServiceClass.SERVICE_CLASS_TYPE.comfort.name()));
    }


    @Test
    public void MIN_MILES_RESTRICTIONS() {

        List<ChargeRoute> actualCharge;

        ChargeInput chargeInput = ChargeInput.of(
                "airport", // from type
                "SVO", // from
                "airport", // to type
                "LED", // to
                "SU", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        Assert.assertEquals(500, getMinMilesCharge(actualCharge));

        chargeInput = ChargeInput.of(
                "airport", // from type
                "VVO", // from
                "airport", // to type
                "ICN", // to
                "SU", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        Assert.assertEquals(500, getMinMilesCharge(actualCharge));

        chargeInput = ChargeInput.of(
                "airport", // from type
                "PRG", // from
                "airport", // to type
                "KLV", // to
                "OK", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        Assert.assertTrue(getMinMilesCharge(actualCharge) < 500);

        chargeInput = ChargeInput.of(
                "airport", // from type
                "PRG", // from
                "airport", // to type
                "FRA", // to
                "OK", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        Assert.assertEquals(500, getMinMilesCharge(actualCharge));

        chargeInput = ChargeInput.of(
                "airport", // from type
                "PRG", // from
                "airport", // to type
                "FRA", // to
                "VN", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        Assert.assertTrue(getMinMilesCharge(actualCharge) < 500);

        chargeInput = ChargeInput.of(
                "airport", // from type
                "CDG", // from
                "airport", // to type
                "FRA", // to
                "VN", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        Assert.assertTrue(getMinMilesCharge(actualCharge) < 500);
    }

    @Test
    public void SORT_BY_SERVICE_CLASSES() {

        List<ChargeRoute> actualCharge;

        ChargeInput chargeInput = ChargeInput.of(
                "airport", // from type
                "SVO", // from
                null, // to type
                null, // to
                "SU", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        testReverseOrderServiceClasses(actualCharge, "SU");

        chargeInput = ChargeInput.of(
                "airport", // from type
                "PRG", // from
                null, // to type
                null, // to
                "OK", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        testReverseOrderServiceClasses(actualCharge, "OK");

        chargeInput = ChargeInput.of(
                "airport", // from type
                "JFK", // from
                null, // to type
                null, // to
                "VN", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        testReverseOrderServiceClasses(actualCharge, "VN");
    }

    // http://support.integrotechnologies.ru/issues/21578
    // http://support.integrotechnologies.ru/issues/21350
    // http://support.integrotechnologies.ru/issues/21326
    @Test
    public void SORT_BY_TARIFFS() {

        List<ChargeRoute> actualCharge;

        ChargeInput chargeInput = ChargeInput.of(
                "airport", // from type
                "SVO", // from
                null, // to type
                null, // to
                "SU", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        testReverseOrderTariffs(actualCharge, "SU");

        chargeInput = ChargeInput.of(
                "airport", // from type
                "PRG", // from
                null, // to type
                null, // to
                "OK", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        testReverseOrderTariffs(actualCharge, "OK");

        chargeInput = ChargeInput.of(
                "airport", // from type
                "JFK", // from
                null, // to type
                null, // to
                "VN", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        testReverseOrderTariffs(actualCharge, "VN");
    }



    // http://support.integrotechnologies.ru/issues/21328
    @Test
    public void SORT_BY_FARE_PREFIXES() {

        List<ChargeRoute> actualCharge;

        ChargeInput chargeInput = ChargeInput.of(
                "airport", // from type
                "SVO", // from
                null, // to type
                null, // to
                "SU", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        testNaturalOrderFarePrefixes(actualCharge);

        chargeInput = ChargeInput.of(
                "airport", // from type
                "PRG", // from
                null, // to type
                null, // to
                "OK", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        testNaturalOrderFarePrefixes(actualCharge);

        chargeInput = ChargeInput.of(
                "airport", // from type
                "JFK", // from
                null, // to type
                null, // to
                "VN", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        testNaturalOrderFarePrefixes(actualCharge);
    }

    @Test
    public void SVO_WORLD_00() {
        ChargeInput chargeInput = ChargeInput.of(
                "airport", // from type
                "SVO", // from
                null, // to type
                null, // to
                "SU", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        List<ChargeRoute> actualCharge = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        List<ChargeRoute> expectedChargeRoutes = this.getExpected("SVO-WORLD-00.json");
        this.common.testIsEquals(actualCharge, expectedChargeRoutes);
    }

}
