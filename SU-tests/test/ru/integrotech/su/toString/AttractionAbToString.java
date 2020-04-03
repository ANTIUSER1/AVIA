package ru.integrotech.su.toString;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.airline.core.bonus.MilesRule;
import ru.integrotech.su.inputparams.attractionAB.AttractionAbInput;
import ru.integrotech.su.inputparams.attractionAB.Data;
import ru.integrotech.su.inputparams.attractionAB.Segment;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.attractionAB.AttractionAbBuilder;
import ru.integrotech.su.outputparams.attractionAB.AttractionAbOutput;

import java.util.ArrayList;
import java.util.List;

public class AttractionAbToString {

    @BeforeClass
    public static void updateRegisters() {
        MockLoader.getInstance().updateRegisters(
                MockLoader.REGISTERS_TYPE.REAL,
                AttractionAbBuilder.getRegisterNames());
    }

    private AttractionAbBuilder attractionAbBuilder;

    @Before
    public void init() {
        this.attractionAbBuilder = AttractionAbBuilder.of(MockLoader.getInstance().getRegisterCache());
    }

    @Test
    public void printMilesRules() {
        for (MilesRule milesRule : MockLoader.getInstance().getRegisterCache().getMilesRules()) {
            System.out.println(milesRule);
        }
    }

    @Test
    public void printOutput() {
        AttractionAbInput input =
                AttractionAbInput.of("SU",
                        "SVO",
                        "KZN",
                        "K",
                        "KFXR",
                        "DP10",
                        "basic");
        AttractionAbOutput result = this.attractionAbBuilder.buildResult(input);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }

    @Test
    public void printOutputNodata() {
        AttractionAbInput input = new AttractionAbInput();
        Data data = new Data();
        Segment inputSegment1 = new Segment();
        inputSegment1.setAirlineIATA("SU");
        inputSegment1.setOriginIATA("KJA");
        inputSegment1.setDestinationIATA("SVO");
        inputSegment1.setBookingClassCode("PR");
        inputSegment1.setFareBasisCode("PLCRNSDF");
        inputSegment1.setTicketDesignator("DP10");
        Segment inputSegment2 = new Segment();
        inputSegment2.setAirlineIATA("SU");
        inputSegment2.setOriginIATA("SVO");
        inputSegment2.setDestinationIATA("PRG");
        inputSegment2.setBookingClassCode("R");
        inputSegment2.setFareBasisCode("RSSDF");
        inputSegment2.setTicketDesignator("DP10");
        List<Segment> inputSegments = new ArrayList<>();
        inputSegments.add(inputSegment1);
        inputSegments.add(inputSegment2);
        data.setSegments(inputSegments);
        data.setTierCode("basic");
        input.setData(data);

        AttractionAbOutput result = this.attractionAbBuilder.buildResult(input);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);

    }
}
