package ru.integrotech.su.toString;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.register.RegisterLoader;
import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.spend.ResultMilesSpendLk;
import ru.integrotech.su.outputparams.spend.SpendBuilder;
import ru.integrotech.su.outputparams.spend.SpendLkBuilder;
import ru.integrotech.su.outputparams.spend.SpendLkRoute;
import ru.integrotech.su.test.CommonTest;

import java.util.List;

/* class for visualization all possible data
*  use to check proper performance listOf toString methods*/
public class SpendLkToString {

    private CommonTest common;

    public SpendLkToString() {
        this.common = CommonTest.of(MockLoader.ofRealRegisters());
    }

    @BeforeClass
    public static void updateRegisters() {
        RegisterLoader.updateInstance(SpendBuilder.getRegisterNames());
    }

    @Test
    public void printSpendLk() {
        SpendInput spendInput = SpendInput.of(
                "airporT",// from
                "SVO", // from type
                "aiRport", // to
                "LED", // to type
                -1, // miles min
                30000, // miles max
                null, // class listOf service name
                "alL", // award type
                false, // afl only
                null // is round trip
        );
        String airlineCode = null;
        if (airlineCode != null) {
            airlineCode = airlineCode.toUpperCase();
        }
        List<SpendLkRoute> result = this.common.getSpendLkBuilder().getSpendLkRoutes(spendInput, airlineCode);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }

    @Test
    public void printSpendLkForODM() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "MMK",// from
                "airport", // to type
                "AER", // to
                -1, // miles min
                100000, // miles max
                null, // class listOf service name
                "all", // award type
                false, // afl only
                false // is round trip
        );
        SpendLkBuilder builder = this.common.getSpendLkBuilder();
        List<Route> routes = builder.getRoutes(spendInput);
        /* ODM rules works here*/
        ResultMilesSpendLk result = builder.buildResult(routes, spendInput);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }


}
