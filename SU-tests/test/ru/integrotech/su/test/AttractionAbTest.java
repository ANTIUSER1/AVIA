package ru.integrotech.su.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.airline.core.info.PassengerMilesInfo;
import ru.integrotech.airline.utils.StringMethods;
import ru.integrotech.su.inputparams.attractionAB.AttractionAbInput;
import ru.integrotech.su.inputparams.attractionAB.Data;
import ru.integrotech.su.inputparams.attractionAB.Segment;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.attractionAB.AttractionAbBuilder;
import ru.integrotech.su.outputparams.attractionAB.AttractionAbOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for AttractionAb project, used remote registers
 */

public class AttractionAbTest {

    @BeforeClass
    public static void updateRegisters() {
        MockLoader.getInstance().updateRegisters(
                                    MockLoader.REGISTERS_TYPE.MOCK,
                                    AttractionAbBuilder.getRegisterNames());
    }

    private AttractionAbBuilder attractionAbBuilder;

    @Before
    public void init() {
        this.attractionAbBuilder = AttractionAbBuilder.of(MockLoader.getInstance().getRegisterCache());
	}
	 
    @Test
	public void TestRegexList() {
        List<String> masks = new ArrayList<>();
        masks.add("CID50*");
        masks.add("*CID50*");
        masks.add("*CID*50*");
        masks.add("?CID*50?");
        masks.add("?CID*50#");
        masks.add("??CID#50##");
        masks.add("??###CI*D#50##");
        masks.add("#####CI*D#50?????");
        masks.add("##???CI*D#50?????");

        for (String mask : masks) {
            String newMask = StringMethods.milesRuleToRegexTransform(mask);
            System.out.printf("%-17.17s -> %-15s\n", mask, newMask);
        }
	}

    @Test
    public void LED_SVO_QNB() {
        AttractionAbInput input =
                AttractionAbInput.of("LED",
                                     "SVO",
                                     "Q",
                                     "QNB",
                                     "DP10",
                                     "basic");
        AttractionAbOutput expectedOutput =
               AttractionAbOutput.of(500,
                                     "full",
                                     "LED",
                                     "SVO",
                                     "full",
                                     500);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_TD() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                                     "KJA",
                                     "Q",
                                     "QNB",
                                     "LSDP65AB",
                                     "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(0,
                                      "full",
                                      "SVO",
                                      "KJA",
                                      "full",
                                      0);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_KVTN() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                                     "KJA",
                                     "K",
                                     "KVTN",
                                     "DP10",
                                     "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(2585,
                                      "full",
                                      "SVO",
                                      "KJA",
                                      "full",
                                      2585);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_NVTN() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "K",
                        "NVTN",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(1551,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        1551);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_BPXAS() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "B",
                        "BPXAS",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(4136,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        4136);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_CFPL() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "C",
                        "CFPL",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(5170,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        5170);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_KFRLN() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "K",
                        "KFRLN",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(4136,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        4136);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_VVO_KFRLN() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "VVO",
                        "K",
                        "KFRLN",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(5983,
                        "full",
                        "SVO",
                        "VVO",
                        "full",
                        5983);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_VVO_BPXAS() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "VVO",
                        "B",
                        "BPXAS",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(997,
                        "full",
                        "SVO",
                        "VVO",
                        "full",
                        997);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_ACTHN() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "Ф",
                        "ACTHN",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(3102,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        3102);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_KDYBRVSJDN() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "Ф",
                        "KDYBRVSJDN",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(0,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        0);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_KDSOCSJDN() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "K",
                        "KDSOCSJDN",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(517,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        517);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_KPSE3SSDN() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "K",
                        "KPSE3SSDN",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(0,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        0);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_CID750SC() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "K",
                        "CID750SC",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(0,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        0);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_QEPSCVS() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "K",
                        "QEPSCVS",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(2068,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        2068);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_X_Class() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "X",
                        "XPLKNJB",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(0,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        0);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_T_class_TD_FSSLADMA() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "T",
                        "TPLKNJB",
                        "FSSLADMA",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(0,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        0);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_T_Class() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "T",
                        "TPLKNJB",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(1551,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        1551);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_W_Class() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "W",
                        "WPLKNJB",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(4136,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        4136);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_HID50PLD() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "H",
                        "HID50PLD",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(0,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        0);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_KJA_RNSDF() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "KJA",
                        "R",
                        "RNSDF",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(1551,
                        "full",
                        "SVO",
                        "KJA",
                        "full",
                        1551);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SVO_VVO_RNSDF() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "VVO",
                        "R",
                        "RNSDF",
                        "DP10",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(997,
                        "full",
                        "SVO",
                        "VVO",
                        "full",
                        997);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }


    @Test
    public void SF_01_case() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "AER",
                        "W",
                        "YPASSJK",
                        "",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(1276,
                        "full",
                        "SVO",
                        "AER",
                        "full",
                        1276);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }


    @Test
    public void SF_02_case() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "AER",
                        "W",
                        "KID50dfghh",
                        "",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(0,
                        "full",
                        "SVO",
                        "AER",
                        "full",
                        0);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void SF_03_case() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "LED",
                        "W",
                        "CFGTR",
                        "",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(990,
                        "full",
                        "SVO",
                        "LED",
                        "full",
                        990);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void Nodata_case() {
        AttractionAbInput input =
                AttractionAbInput.of("KHV",
                        "VVO",
                        "W",
                        "",
                        "129ID129sdfg",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(0,
                        "nodata",
                        "KHV",
                        "VVO",
                        "nodata",
                        0);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void TD_case() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "LED",
                        "W",
                        "CFGTR",
                        "129ID129sdfg",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(0,
                        "full",
                        "SVO",
                        "LED",
                        "full",
                        0);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void BC_case() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "LED",
                        "S",
                        "",
                        "",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(792,
                        "full",
                        "SVO",
                        "LED",
                        "full",
                        792);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void Airport_case_02() {
        AttractionAbInput input =
                AttractionAbInput.of("SVO",
                        "AER",
                        "V",
                        "BFloi",
                        "",
                        "basic");
        AttractionAbOutput expectedOutput =
                AttractionAbOutput.of(1702,
                        "full",
                        "SVO",
                        "AER",
                        "full",
                        1702);
        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);
        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void nodata() {
        AttractionAbInput input = new AttractionAbInput();
        Data data = new Data();
        Segment inputSegment1 = new Segment();
        inputSegment1.setOriginIATA("KJA");
        inputSegment1.setDestinationIATA("SVO");
        inputSegment1.setBookingClassCode("PR");
        inputSegment1.setFareBasisCode("PLCRNSDF");
        inputSegment1.setTicketDesignator("DP10");
        Segment inputSegment2 = new Segment();
        inputSegment2.setOriginIATA("KHV");
        inputSegment2.setDestinationIATA("VVO");
        inputSegment2.setBookingClassCode("R");
        inputSegment2.setFareBasisCode("PLCRNSDF");
        inputSegment2.setTicketDesignator("DP10");
        List<Segment> inputSegments = new ArrayList<>();
        inputSegments.add(inputSegment1);
        inputSegments.add(inputSegment2);
        data.setSegments(inputSegments);
        data.setTierCode("basic");
        input.setData(data);

        AttractionAbOutput expectedOutput = new  AttractionAbOutput();
        expectedOutput.setTotalMiles(0);
        expectedOutput.setTotalStatus(PassengerMilesInfo.Status.valueOf("nodata"));
        ru.integrotech.su.outputparams.attractionAB.Segment outputSegment1 = new ru.integrotech.su.outputparams.attractionAB.Segment();
        outputSegment1.setOriginIATA("KJA");
        outputSegment1.setDestinationIATA("SVO");
        outputSegment1.setStatus(PassengerMilesInfo.Status.valueOf("nodata"));
        outputSegment1.setMiles(0);
        ru.integrotech.su.outputparams.attractionAB.Segment outputSegment2 = new ru.integrotech.su.outputparams.attractionAB.Segment();
        outputSegment2.setOriginIATA("KHV");
        outputSegment2.setDestinationIATA("VVO");
        outputSegment2.setStatus(PassengerMilesInfo.Status.valueOf("nodata"));
        outputSegment2.setMiles(0);
        List<ru.integrotech.su.outputparams.attractionAB.Segment> outputSegments = new ArrayList<>();
        outputSegments.add(outputSegment1);
        outputSegments.add(outputSegment2);
        expectedOutput.setSegments(outputSegments);

        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);

        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void partial() {
        AttractionAbInput input = new AttractionAbInput();
        Data data = new Data();
        Segment inputSegment1 = new Segment();
        inputSegment1.setOriginIATA("SVO");
        inputSegment1.setDestinationIATA("AER");
        inputSegment1.setBookingClassCode("PR");
        inputSegment1.setFareBasisCode("PLCRNSDF");
        inputSegment1.setTicketDesignator("DP10");
        Segment inputSegment2 = new Segment();
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

        AttractionAbOutput expectedOutput = new  AttractionAbOutput();
        expectedOutput.setTotalMiles(500);
        expectedOutput.setTotalStatus(PassengerMilesInfo.Status.valueOf("partial"));
        ru.integrotech.su.outputparams.attractionAB.Segment outputSegment1 = new ru.integrotech.su.outputparams.attractionAB.Segment();
        outputSegment1.setOriginIATA("SVO");
        outputSegment1.setDestinationIATA("AER");
        outputSegment1.setStatus(PassengerMilesInfo.Status.valueOf("nodata"));
        outputSegment1.setMiles(0);
        ru.integrotech.su.outputparams.attractionAB.Segment outputSegment2 = new ru.integrotech.su.outputparams.attractionAB.Segment();
        outputSegment2.setOriginIATA("SVO");
        outputSegment2.setDestinationIATA("PRG");
        outputSegment2.setStatus(PassengerMilesInfo.Status.valueOf("full"));
        outputSegment2.setMiles(500);
        List<ru.integrotech.su.outputparams.attractionAB.Segment> outputSegments = new ArrayList<>();
        outputSegments.add(outputSegment1);
        outputSegments.add(outputSegment2);
        expectedOutput.setSegments(outputSegments);

        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);

        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void full() {
        AttractionAbInput input = new AttractionAbInput();
        Data data = new Data();
        Segment inputSegment1 = new Segment();
        inputSegment1.setOriginIATA("KJA");
        inputSegment1.setDestinationIATA("SVO");
        inputSegment1.setBookingClassCode("N");
        inputSegment1.setFareBasisCode("NCFSD");
        inputSegment1.setTicketDesignator("DP10");
        Segment inputSegment2 = new Segment();
        inputSegment2.setOriginIATA("SVO");
        inputSegment2.setDestinationIATA("PRG");
        inputSegment2.setBookingClassCode("N");
        inputSegment2.setFareBasisCode("NCSDF");
        inputSegment2.setTicketDesignator("DP10");
        List<Segment> inputSegments = new ArrayList<>();
        inputSegments.add(inputSegment1);
        inputSegments.add(inputSegment2);
        data.setSegments(inputSegments);
        data.setTierCode("basic");
        input.setData(data);

        AttractionAbOutput expectedOutput = new  AttractionAbOutput();
        expectedOutput.setTotalMiles(3106);
        expectedOutput.setTotalStatus(PassengerMilesInfo.Status.valueOf("full"));
        ru.integrotech.su.outputparams.attractionAB.Segment outputSegment1 = new ru.integrotech.su.outputparams.attractionAB.Segment();
        outputSegment1.setOriginIATA("KJA");
        outputSegment1.setDestinationIATA("SVO");
        outputSegment1.setStatus(PassengerMilesInfo.Status.valueOf("full"));
        outputSegment1.setMiles(2068);
        ru.integrotech.su.outputparams.attractionAB.Segment outputSegment2 = new ru.integrotech.su.outputparams.attractionAB.Segment();
        outputSegment2.setOriginIATA("SVO");
        outputSegment2.setDestinationIATA("PRG");
        outputSegment2.setStatus(PassengerMilesInfo.Status.valueOf("full"));
        outputSegment2.setMiles(1038);
        List<ru.integrotech.su.outputparams.attractionAB.Segment> outputSegments = new ArrayList<>();
        outputSegments.add(outputSegment1);
        outputSegments.add(outputSegment2);
        expectedOutput.setSegments(outputSegments);

        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);

        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void full_TD() {
        AttractionAbInput input = new AttractionAbInput();
        Data data = new Data();
        Segment inputSegment1 = new Segment();
        inputSegment1.setOriginIATA("KJA");
        inputSegment1.setDestinationIATA("SVO");
        inputSegment1.setBookingClassCode("N");
        inputSegment1.setFareBasisCode("NCFSD");
        inputSegment1.setTicketDesignator("XDP60");
        Segment inputSegment2 = new Segment();
        inputSegment2.setOriginIATA("SVO");
        inputSegment2.setDestinationIATA("PRG");
        inputSegment2.setBookingClassCode("N");
        inputSegment2.setFareBasisCode("NCSDF");
        inputSegment2.setTicketDesignator("DP10");
        List<Segment> inputSegments = new ArrayList<>();
        inputSegments.add(inputSegment1);
        inputSegments.add(inputSegment2);
        data.setSegments(inputSegments);
        data.setTierCode("basic");
        input.setData(data);

        AttractionAbOutput expectedOutput = new  AttractionAbOutput();
        expectedOutput.setTotalMiles(1038);
        expectedOutput.setTotalStatus(PassengerMilesInfo.Status.valueOf("full"));
        ru.integrotech.su.outputparams.attractionAB.Segment outputSegment1 = new ru.integrotech.su.outputparams.attractionAB.Segment();
        outputSegment1.setOriginIATA("KJA");
        outputSegment1.setDestinationIATA("SVO");
        outputSegment1.setStatus(PassengerMilesInfo.Status.valueOf("full"));
        outputSegment1.setMiles(0);
        ru.integrotech.su.outputparams.attractionAB.Segment outputSegment2 = new ru.integrotech.su.outputparams.attractionAB.Segment();
        outputSegment2.setOriginIATA("SVO");
        outputSegment2.setDestinationIATA("PRG");
        outputSegment2.setStatus(PassengerMilesInfo.Status.valueOf("full"));
        outputSegment2.setMiles(1038);
        List<ru.integrotech.su.outputparams.attractionAB.Segment> outputSegments = new ArrayList<>();
        outputSegments.add(outputSegment1);
        outputSegments.add(outputSegment2);
        expectedOutput.setSegments(outputSegments);

        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);

        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void partial_TD() {
        AttractionAbInput input = new AttractionAbInput();
        Data data = new Data();
        Segment inputSegment1 = new Segment();
        inputSegment1.setOriginIATA("KJA");
        inputSegment1.setDestinationIATA("SVO");
        inputSegment1.setBookingClassCode("N");
        inputSegment1.setFareBasisCode("NCFSD");
        inputSegment1.setTicketDesignator("XDP60");
        Segment inputSegment2 = new Segment();
        inputSegment2.setOriginIATA("SVO");
        inputSegment2.setDestinationIATA("PRG");
        inputSegment2.setBookingClassCode("NK");
        inputSegment2.setFareBasisCode("ZZNCSDF");
        inputSegment2.setTicketDesignator("DP10");
        List<Segment> inputSegments = new ArrayList<>();
        inputSegments.add(inputSegment1);
        inputSegments.add(inputSegment2);
        data.setSegments(inputSegments);
        data.setTierCode("basic");
        input.setData(data);

        AttractionAbOutput expectedOutput = new  AttractionAbOutput();
        expectedOutput.setTotalMiles(0);
        expectedOutput.setTotalStatus(PassengerMilesInfo.Status.valueOf("partial"));
        ru.integrotech.su.outputparams.attractionAB.Segment outputSegment1 = new ru.integrotech.su.outputparams.attractionAB.Segment();
        outputSegment1.setOriginIATA("KJA");
        outputSegment1.setDestinationIATA("SVO");
        outputSegment1.setStatus(PassengerMilesInfo.Status.valueOf("full"));
        outputSegment1.setMiles(0);
        ru.integrotech.su.outputparams.attractionAB.Segment outputSegment2 = new ru.integrotech.su.outputparams.attractionAB.Segment();
        outputSegment2.setOriginIATA("SVO");
        outputSegment2.setDestinationIATA("PRG");
        outputSegment2.setStatus(PassengerMilesInfo.Status.valueOf("nodata"));
        outputSegment2.setMiles(0);
        List<ru.integrotech.su.outputparams.attractionAB.Segment> outputSegments = new ArrayList<>();
        outputSegments.add(outputSegment1);
        outputSegments.add(outputSegment2);
        expectedOutput.setSegments(outputSegments);

        AttractionAbOutput actualOutput = this.attractionAbBuilder.buildResult(input);

        Assert.assertEquals(actualOutput, expectedOutput);
    }


}
