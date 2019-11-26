package ru.integrotech.su.test;

import java.io.IOException;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import ru.integrotech.airline.core.bonus.ChargeRule;
import ru.integrotech.airline.core.bonus.TicketDesignator;
import ru.integrotech.airline.core.flight.PassengerCharge;
import ru.integrotech.airline.exceptions.UnsupportedFlightException;
import ru.integrotech.su.inputparams.attractionAB.AttractionAbInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.attractionAB.AttractionAbOutput;

public class AttractionAbTest {
	
	 private static final String RESULTS_FOLDER = "/results/attractionsAB/";

	 private final CommonTest common;

	 public AttractionAbTest() {
		this.common = CommonTest.of(MockLoader.ofMockRegisters());
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
	 
	


}
