package ru.integrotech.su.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.airline.core.info.PassengerLoyaltyInfo;
import ru.integrotech.su.exceptions.UnsupportedParamException;
import ru.integrotech.su.inputparams.chargeLoyalty.ChargeLoyaltyRequest;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.chargeLoyalty.ChargeLoyaltyBuilder;
import ru.integrotech.su.outputparams.chargeLoyalty.ChargeLoyaltyResponse;

public class ChargeLoyaltyTest {

    @BeforeClass
    public static void updateRegisters() {
        MockLoader.getInstance().updateRegisters(
                                    MockLoader.REGISTERS_TYPE.REAL,
                                    ChargeLoyaltyBuilder.getRegisterNames());
    }

    private ChargeLoyaltyBuilder builder;

    @Before
    public void init() {
        this.builder = ChargeLoyaltyBuilder.of(MockLoader.getInstance().getRegisterCache());
    }

    @Test
    public void test_illegal() throws UnsupportedParamException {
        ChargeLoyaltyRequest request = ChargeLoyaltyRequest.of("points",
                100,
                "DXT01_2.5");
        ChargeLoyaltyResponse response = this.builder.buildResponse(request);
        Assert.assertEquals(response.getStatus(), PassengerLoyaltyInfo.STATUS.illegal.name());
        Assert.assertEquals(response.getPoints(), 0);

    }

    @Test
    public void test_01() throws UnsupportedParamException {
        ChargeLoyaltyRequest request = ChargeLoyaltyRequest.of("cash",
                100,
                "DXT01_2.5");
        ChargeLoyaltyResponse response = this.builder.buildResponse(request);
        Assert.assertEquals(response.getStatus(), PassengerLoyaltyInfo.STATUS.legal.name());
        Assert.assertEquals(response.getPoints(), 4);
    }

    @Test
    public void test_02() throws UnsupportedParamException {
        ChargeLoyaltyRequest request = ChargeLoyaltyRequest.of("cash",
                100,
                "DXT01_0");
        ChargeLoyaltyResponse response = this.builder.buildResponse(request);
        Assert.assertEquals(response.getStatus(), PassengerLoyaltyInfo.STATUS.legal.name());
        Assert.assertEquals(response.getPoints(), 3);
    }

    @Test
    public void test_03() throws UnsupportedParamException {
        ChargeLoyaltyRequest request = ChargeLoyaltyRequest.of("cash",
                100,
                "DXT01_3");
        ChargeLoyaltyResponse response = this.builder.buildResponse(request);
        Assert.assertEquals(response.getStatus(), PassengerLoyaltyInfo.STATUS.legal.name());
        Assert.assertEquals(response.getPoints(), 5);
    }

    @Test
    public void test_04() throws UnsupportedParamException {
        ChargeLoyaltyRequest request = ChargeLoyaltyRequest.of("cash",
                100,
                "DXT02");
        ChargeLoyaltyResponse response = this.builder.buildResponse(request);
        Assert.assertEquals(response.getStatus(), PassengerLoyaltyInfo.STATUS.legal.name());
        Assert.assertEquals(response.getPoints(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_error() throws UnsupportedParamException {
        ChargeLoyaltyRequest request = ChargeLoyaltyRequest.of("something",
                100,
                "DXT02");
        this.builder.buildResponse(request);
    }

    @Test
    public void test_low_and_upper_cases() throws UnsupportedParamException {
        ChargeLoyaltyRequest request = ChargeLoyaltyRequest.of("cAsH",
                100,
                "Dxt02");
        ChargeLoyaltyResponse response = this.builder.buildResponse(request);
        Assert.assertEquals(response.getStatus(), PassengerLoyaltyInfo.STATUS.legal.name());
        Assert.assertEquals(response.getPoints(), 0);
    }

    @Test(expected = UnsupportedParamException.class)
    public void test_not_found_accounting_code1() throws UnsupportedParamException {
        ChargeLoyaltyRequest request = ChargeLoyaltyRequest.of("cash",
                100,
                "Something");
        ChargeLoyaltyResponse response = this.builder.buildResponse(request);
        Assert.assertEquals(response.getStatus(), PassengerLoyaltyInfo.STATUS.legal.name());
        Assert.assertEquals(response.getPoints(), 0);
    }

    @Test
    public void test_not_found_accounting_code2() throws UnsupportedParamException {
        ChargeLoyaltyRequest request = ChargeLoyaltyRequest.of("points",
                100,
                "Something");
        ChargeLoyaltyResponse response = this.builder.buildResponse(request);
        Assert.assertEquals(response.getStatus(), PassengerLoyaltyInfo.STATUS.illegal.name());
        Assert.assertEquals(response.getPoints(), 0);
    }

    @Test(expected = UnsupportedParamException.class)
    public void test_tariff_sum_zero() throws UnsupportedParamException {
        ChargeLoyaltyRequest request = ChargeLoyaltyRequest.of("points",
                0,
                "Something");
        ChargeLoyaltyResponse response = this.builder.buildResponse(request);
        Assert.assertEquals(response.getStatus(), PassengerLoyaltyInfo.STATUS.illegal.name());
        Assert.assertEquals(response.getPoints(), 0);
    }

    @Test()
    public void test_tariff_sum_below_zero() throws UnsupportedParamException {
        ChargeLoyaltyRequest request = ChargeLoyaltyRequest.of("points",
                -1,
                "Something");
        ChargeLoyaltyResponse response = this.builder.buildResponse(request);
        Assert.assertEquals(response.getStatus(), PassengerLoyaltyInfo.STATUS.illegal.name());
        Assert.assertEquals(response.getPoints(), 0);
    }
}
