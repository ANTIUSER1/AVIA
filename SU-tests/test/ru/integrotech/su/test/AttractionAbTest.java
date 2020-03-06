package ru.integrotech.su.test;

import com.google.gson.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.airline.core.bonus.MilesRule;
import ru.integrotech.airline.utils.StringMethods;
import ru.integrotech.su.inputparams.attractionAB.AttractionAbInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.attractionAB.AttractionAbBuilder;
import ru.integrotech.su.outputparams.attractionAB.AttractionAbOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AttractionAbTest {
	
	private static final String RESULTS_FOLDER = "test/ru/integrotech/su/resources/results/attractionsAB/";

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
	 
	private AttractionAbInput getInput(String jsonName) {
        MockLoader loader = MockLoader.getInstance();
	        AttractionAbInput result = null;
	        try {
	            JsonElement jsonElement = loader.loadJson(RESULTS_FOLDER + jsonName);
	            result = loader.getTestsCache().loadAttractionAbInputParams(jsonElement);
	        } catch (JsonIOException
	                | JsonSyntaxException
	                | IOException e) {
	            e.printStackTrace();
	        }
	        return result;
	}
	 
	 @Test
	  public void printMilesRules() {
	      for (MilesRule milesRule : MockLoader.getInstance().getRegisterCache().getMilesRules()) {
	           System.out.println(milesRule);
	      }
	  }

	 @Test
	  public void PRINT_Example01() {
	        AttractionAbInput input = this.getInput("Example01.json");
	        AttractionAbOutput output = null;
			output = this.attractionAbBuilder.buildResult(input);
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        String jsonResult = gson.toJson(output);
	        System.out.println(jsonResult);
	  }

	  /* expected "miles": 889*/
      @Test
      public void PRINT_SVO_SVX_00() {
            AttractionAbInput input = this.getInput("SVO-SVX-00.json");
            AttractionAbOutput output = null;
            output = this.attractionAbBuilder.buildResult(input);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResult = gson.toJson(output);
            System.out.println(jsonResult);
      }

        /* expected "miles": 1778*/
        @Test
        public void PRINT_SVO_SVX_01() {
            AttractionAbInput input = this.getInput("SVO-SVX-01.json");
            AttractionAbOutput output = null;
            output = this.attractionAbBuilder.buildResult(input);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResult = gson.toJson(output);
            System.out.println(jsonResult);
        }

        /* expected "miles": 500*/
        @Test
        public void PRINT_SVO_SVX_02() {
            AttractionAbInput input = this.getInput("SVO-SVX-02.json");
            AttractionAbOutput output = null;
            output = this.attractionAbBuilder.buildResult(input);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResult = gson.toJson(output);
            System.out.println(jsonResult);
        }

        /* expected "miles": 0*/
        @Test
        public void PRINT_SVO_SVX_03() {
            AttractionAbInput input = this.getInput("SVO-SVX-03.json");
            AttractionAbOutput output = null;
            output = this.attractionAbBuilder.buildResult(input);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResult = gson.toJson(output);
            System.out.println(jsonResult);
        }

        /* expected "miles": 0*/
        @Test
        public void PRINT_SVO_SVX_04() {
            AttractionAbInput input = this.getInput("SVO-SVX-04.json");
            AttractionAbOutput output = null;
            output = this.attractionAbBuilder.buildResult(input);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResult = gson.toJson(output);
            System.out.println(jsonResult);
        }

    /* expected "miles": 557*/
    @Test
    public void PRINT_SVO_KZN_00() {
         AttractionAbInput input = this.getInput("SVO-KZN-00.json");
         AttractionAbOutput output = null;
         output = this.attractionAbBuilder.buildResult(input);
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
         String jsonResult = gson.toJson(output);
         System.out.println(jsonResult);
    }

    /* expected "miles": 557*/
    @Test
    public void PRINT_SVO_KZN_01() {
        AttractionAbInput input = this.getInput("SVO-KZN-01.json");
        AttractionAbOutput output = null;
        output = this.attractionAbBuilder.buildResult(input);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(output);
        System.out.println(jsonResult);
    }

    @Test
    public void PRINT_SVO_ROV_00() {
        AttractionAbInput input = this.getInput("SVO-ROV-00.json");
        AttractionAbOutput output = null;
        output = this.attractionAbBuilder.buildResult(input);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(output);
        System.out.println(jsonResult);
    }

    @Test
    public void PRINT_SVO_IST_SVO() {
        AttractionAbInput input = this.getInput("SVO-IST-SVO.json");
        AttractionAbOutput output = null;
        output = this.attractionAbBuilder.buildResult(input);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(output);
        System.out.println(jsonResult);
    }

    @Test
    public void PRINT_SVO_IST_01() {
        AttractionAbInput input = this.getInput("SVO-IST-01.json");
        AttractionAbOutput output = null;
        output = this.attractionAbBuilder.buildResult(input);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(output);
        System.out.println(jsonResult);
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




}
