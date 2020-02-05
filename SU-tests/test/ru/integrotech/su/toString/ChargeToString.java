package ru.integrotech.su.toString;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.register.RegisterLoader;
import ru.integrotech.su.inputparams.charge.ChargeInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.charge.ChargeBuilder;
import ru.integrotech.su.outputparams.charge.ChargeRoute;
import ru.integrotech.su.outputparams.charge.ResultMilesCharge;
import ru.integrotech.su.test.CommonTest;

import java.util.List;

/* class for visualization all possible data
*  use to check proper performance listOf toString methods*/
public class ChargeToString {

    private CommonTest common;

    public ChargeToString() {
        this.common = CommonTest.of(MockLoader.ofRealRegisters());
    }

    @BeforeClass
    public static void updateRegisters() {
        RegisterLoader.updateInstance(ChargeBuilder.getRegisterNames());
    }

    @Test
    public void printCharge() {
        ChargeInput chargeInput = ChargeInput.of("City", //from
                                                 "Mow", // from type
                                                 "cIty", // to
                                                 "pRG", // to type
                                                 "sU", // airline code
                                                 "Basic", //loyalty
                                                 false); //is round
        List<ChargeRoute> result = this.common.getChargeBuilder().getChargeRoutes(chargeInput);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }

    @Test
    public void printChargeForODM() {
        ChargeInput chargeInput = ChargeInput.of(
                "airport", // from type
                "SVO", // from
                "airport", // to type
                "VVO", // to
                "SU", // airline
                "basic", // tierLevelCode
                false //isRound
        );
        ChargeBuilder builder = this.common.getChargeBuilder();
        List<Route> routes = builder.getRoutes(chargeInput);
        builder.getFactor(chargeInput);
        /* ODM rules works here*/
        ResultMilesCharge result = builder.buildResult(routes, chargeInput);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }


}
