package ru.integrotech.su.test;

import com.google.gson.*;
import org.junit.Test;
import ru.integrotech.airline.core.bonus.ChargeRule;
import ru.integrotech.airline.core.bonus.TicketDesignator;
import ru.integrotech.su.inputparams.attractionAB.AttractionAbInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.attractionAB.AttractionAbOutput;

import java.io.IOException;

public class AttractionAbTest {
	
	 private static final String RESULTS_FOLDER = "/results/attractionsAB/";

	 private final CommonTest common;

	 public AttractionAbTest() {
		this.common = CommonTest.of(MockLoader.ofRealRegisters());
	 }
	 
	 private AttractionAbInput getInput(String jsonName) {
	        AttractionAbInput result = null;
	        try {
	            JsonElement jsonElement = this.common.getLoader().loadJson(RESULTS_FOLDER + jsonName);
	            result = this.common.getTestsCache().loadAttractionAbInputParams(jsonElement);
	        } catch (JsonIOException
	                | JsonSyntaxException
	                | IOException e) {
	            e.printStackTrace();
	        }
	        return result;
	  }
	 
	 @Test
	  public void printChargeRules() {
	      for (ChargeRule chargeRule : this.common.getTestsCache().getRegisters().getChargeRules()) {
	           System.out.println(chargeRule);
	      }
	  }

	  @Test
	  public void printTicketDesignators() {
			for (TicketDesignator designator : this.common.getTestsCache().getRegisters().getTicketDesignators()) {
				System.out.println(designator);
			}
	  }
	 
	 
	 @Test
	  public void PRINT_SVO_PRG_00() {
	        AttractionAbInput input = this.getInput("Example01.json");
	        AttractionAbOutput output = null;
			output = this.common.getAttractionAbBuilder().buildResult(input);
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        String jsonResult = gson.toJson(output);
	        System.out.println(jsonResult);
	  }

	  /* expected "miles": 889*/
      @Test
      public void PRINT_SVO_SVX_00() {
            AttractionAbInput input = this.getInput("SVO-SVX-00.json");
            AttractionAbOutput output = null;
            output = this.common.getAttractionAbBuilder().buildResult(input);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResult = gson.toJson(output);
            System.out.println(jsonResult);
      }

        /* expected "miles": 1778*/
        @Test
        public void PRINT_SVO_SVX_01() {
            AttractionAbInput input = this.getInput("SVO-SVX-01.json");
            AttractionAbOutput output = null;
            output = this.common.getAttractionAbBuilder().buildResult(input);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResult = gson.toJson(output);
            System.out.println(jsonResult);
        }

        /* expected "miles": 500*/
        @Test
        public void PRINT_SVO_SVX_02() {
            AttractionAbInput input = this.getInput("SVO-SVX-02.json");
            AttractionAbOutput output = null;
            output = this.common.getAttractionAbBuilder().buildResult(input);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResult = gson.toJson(output);
            System.out.println(jsonResult);
        }

        /* expected "miles": 0*/
        @Test
        public void PRINT_SVO_SVX_03() {
            AttractionAbInput input = this.getInput("SVO-SVX-03.json");
            AttractionAbOutput output = null;
            output = this.common.getAttractionAbBuilder().buildResult(input);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResult = gson.toJson(output);
            System.out.println(jsonResult);
        }

        /* expected "miles": 0*/
        @Test
        public void PRINT_SVO_SVX_04() {
            AttractionAbInput input = this.getInput("SVO-SVX-04.json");
            AttractionAbOutput output = null;
            output = this.common.getAttractionAbBuilder().buildResult(input);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResult = gson.toJson(output);
            System.out.println(jsonResult);
        }

        /* expected "miles": 557*/
        @Test
        public void PRINT_SVO_KZN_00() {
            AttractionAbInput input = this.getInput("SVO-KZN-00.json");
            AttractionAbOutput output = null;
            output = this.common.getAttractionAbBuilder().buildResult(input);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResult = gson.toJson(output);
            System.out.println(jsonResult);
        }


    @Test
	  public void TestRegex() {
            String example = "DP8#*";
            example = example.replaceAll("\\*", ".*").replaceAll("#", "\\\\d");
            System.out.println(example);
	  }

  


}
