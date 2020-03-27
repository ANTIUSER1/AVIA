package ru.integrotech.su.toString;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.spend.ResultMilesSpend;
import ru.integrotech.su.outputparams.spend.SpendBuilder;
import ru.integrotech.su.outputparams.spend.SpendRoute;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Class for visualization data of Spend project
 */

public class SpendToString {

	@BeforeClass
	public static void updateRegisters() {
		MockLoader.getInstance()
				.updateRegisters(MockLoader.REGISTERS_TYPE.REAL,
						SpendBuilder.getRegisterNames());
	}

	private SpendBuilder spendBuilder;

	@Before
	public void init() {
		this.spendBuilder = SpendBuilder.of(MockLoader.getInstance()
				.getRegisterCache());
	}

	@Test
	public void printSpend() throws Exception {
		SpendInput spendInput = SpendInput.of("Airport",// from
				"svo", // from type
				"Airport", // to
				"vvo", // to type
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"All", // award type
				false, // afl only
				null // is round trip
				);
		String airlineCode = "su";
		if (airlineCode != null) {
			airlineCode = airlineCode.toUpperCase();
		}
		List<SpendRoute> result = this.spendBuilder.getSpendRoutes(spendInput,
				airlineCode);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonResult = gson.toJson(result);
		System.out.println(jsonResult);
	}

	@Test
	public void printSpendForODM() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
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
		List<Route> routes = this.spendBuilder.getRoutes(spendInput);
		/* ODM rules works here */
		ResultMilesSpend result = this.spendBuilder.buildResult(routes,
				spendInput);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonResult = gson.toJson(result);
		System.out.println(jsonResult);
	}

}
