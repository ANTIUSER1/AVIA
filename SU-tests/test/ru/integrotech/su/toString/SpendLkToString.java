package ru.integrotech.su.toString;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.spend.ResultMilesSpendLk;
import ru.integrotech.su.outputparams.spend.SpendBuilder;
import ru.integrotech.su.outputparams.spend.SpendLkBuilder;
import ru.integrotech.su.outputparams.spend.SpendLkRoute;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Class for visualization data of SpendLk project
 */

public class SpendLkToString {

	@BeforeClass
	public static void updateRegisters() {
		MockLoader.getInstance()
				.updateRegisters(MockLoader.REGISTERS_TYPE.REAL,
						SpendBuilder.getRegisterNames());
	}

	private SpendLkBuilder spendLkBuilder;

	@Before
	public void init() {
		SpendBuilder spendBuilder = SpendBuilder.of(MockLoader.getInstance()
				.getRegisterCache());
		this.spendLkBuilder = SpendLkBuilder.of(spendBuilder);
	}

	@Test
	public void printSpendLk() throws Exception {
		SpendInput spendInput = SpendInput.of("airporT",// from
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
		List<SpendLkRoute> result = this.spendLkBuilder.getSpendLkRoutes(
				spendInput, airlineCode);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonResult = gson.toJson(result);
		System.out.println(jsonResult);
	}

	@Test
	public void printSpendLkForODM() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
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
		List<Route> routes = this.spendLkBuilder.getRoutes(spendInput);
		/* ODM rules works here */
		ResultMilesSpendLk result = this.spendLkBuilder.buildResult(routes,
				spendInput);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonResult = gson.toJson(result);
		System.out.println(jsonResult);
	}

}
