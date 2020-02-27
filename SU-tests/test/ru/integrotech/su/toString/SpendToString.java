package ru.integrotech.su.toString;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.register.RegisterLoader;
import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.spend.ResultMilesSpend;
import ru.integrotech.su.outputparams.spend.SpendBuilder;
import ru.integrotech.su.outputparams.spend.SpendRoute;
import ru.integrotech.su.test.CommonTest;

import java.util.List;

/* class for visualization all possible data
*  use to check proper performance listOf toString methods*/
public class SpendToString {

    private CommonTest common;

    public SpendToString() {
        this.common = CommonTest.of(MockLoader.ofRealRegisters());
    }

    @BeforeClass
    public static void updateRegisters() {
        RegisterLoader.updateInstance(SpendBuilder.getRegisterNames());
    }

    @Test
    public void printStartLogs() {
        this.common.getTestsCache();
    }

    @Test
    public void printSpend() {
        this.common = null;
        this.common = CommonTest.of(MockLoader.ofRealRegisters("airline"));
        SpendInput spendInput = SpendInput.of(
                                    "Airport",// from
                                    "svo", // from type
                                    "Airport", // to
                                    "vvo", // to type
                                    -1, // miles min
                                    100000, // miles max
                                    "Economy", // class listOf service name
                                    "All", // award type
                                    false, // afl only
                                    null // is round trip
                                    );
        String airlineCode = "su";
        if (airlineCode != null) {
            airlineCode = airlineCode.toUpperCase();
        }
        List<SpendRoute> result = this.common.getSpendBuilder().getSpendRoutes(spendInput, airlineCode);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }

    @Test
    public void printSpendForODM() {
        SpendInput spendInput = SpendInput.of(
                "airport", // from type
                "SVO",// from
                "airport", // to type
                "VVO", // to
                -1, // miles min
                100000, // miles max
                "economy", // class listOf service name
                "all", // award type
                true, // afl only
                false // is round trip
        );
        SpendBuilder builder = this.common.getSpendBuilder();
        List<Route> routes = builder.getRoutes(spendInput);
        /* ODM rules works here*/
        ResultMilesSpend result = builder.buildResult(routes, spendInput);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }


}
