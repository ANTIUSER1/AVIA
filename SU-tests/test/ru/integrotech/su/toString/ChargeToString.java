package ru.integrotech.su.toString;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.su.inputparams.charge.ChargeInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.charge.ChargeBuilder;
import ru.integrotech.su.outputparams.charge.ChargeRoute;
import ru.integrotech.su.outputparams.charge.ResultMilesCharge;

import java.util.List;

/**
 * Class for visualization data of Charge project
 */

public class ChargeToString {

    @BeforeClass
    public static void updateRegisters() {
        MockLoader.getInstance().updateRegisters(
                                    MockLoader.REGISTERS_TYPE.REAL,
                                    ChargeBuilder.getRegisterNames());
    }

    private ChargeBuilder chargeBuilder;

    @Before
    public void init() {
        this.chargeBuilder = ChargeBuilder.of(MockLoader.getInstance().getRegisterCache());
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
        List<ChargeRoute> result = this.chargeBuilder.getChargeRoutes(chargeInput);
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
        List<Route> routes = this.chargeBuilder.getRoutes(chargeInput);
        this.chargeBuilder.getFactor(chargeInput);
        /* ODM rules works here*/
        ResultMilesCharge result = this.chargeBuilder.buildResult(routes, chargeInput);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }


}
