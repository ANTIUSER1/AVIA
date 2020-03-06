package ru.integrotech.su.toString;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.su.exceptions.UnsupportedParamException;
import ru.integrotech.su.inputparams.chargeLoyalty.ChargeLoyaltyRequest;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.chargeLoyalty.ChargeLoyaltyBuilder;
import ru.integrotech.su.outputparams.chargeLoyalty.ChargeLoyaltyResponse;

import java.util.Map;

public class ChargeLoyaltyToString {

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
    public void printLoyaltyLevelCode() {
        Map<String, Integer> register = MockLoader.getInstance().getRegisterCache().getLoyaltyLevelCodeMap();
        for (Map.Entry<String, Integer> entry : register.entrySet()) {
            System.out.printf("%-10.10s  %s\n", entry.getKey(), entry.getValue());
        }
    }

    @Test
    public void printResponse() throws UnsupportedParamException {
        ChargeLoyaltyRequest request = ChargeLoyaltyRequest.of("cash",
                                                                15130,
                                                                "DXT01_2.5");
        ChargeLoyaltyResponse response = this.builder.buildResponse(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(response);
        System.out.println(jsonResult);

    }

}
