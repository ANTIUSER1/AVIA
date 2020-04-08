package ru.integrotech.su.test;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.spend.SpendBuilder;
import ru.integrotech.su.outputparams.spend.SpendLkBuilder;
import ru.integrotech.su.outputparams.spend.SpendLkRoute;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * Tests for SpendLK project, used inner registers
 */

public class SpendLkTest {

	private static final String RESULTS_FOLDER = "test/ru/integrotech/su/resources/results/spendLkRoutes/";

	@BeforeClass
	public static void updateRegisters() {
		MockLoader.getInstance()
				.updateRegisters(MockLoader.REGISTERS_TYPE.MOCK,
						SpendBuilder.getRegisterNames());
	}

	private Comparator comparator;

	private SpendLkBuilder spendLkBuilder;

	@Before
	public void init() {
		this.comparator = Comparator.of(SpendLkRoute.class);
		SpendBuilder spendBuilder = SpendBuilder.of(MockLoader.getInstance()
				.getRegisterCache());
		this.spendLkBuilder = SpendLkBuilder.of(spendBuilder);
	}

	private List<SpendLkRoute> getExpected(String jsonName) {
		MockLoader loader = MockLoader.getInstance();
		List<SpendLkRoute> expectedSpendLkRoutes = null;
		try {
			JsonElement jsonElement = loader
					.loadJson(RESULTS_FOLDER + jsonName);
			expectedSpendLkRoutes = loader.getTestsCache().loadSpendLkRoutes(
					jsonElement);
		} catch (JsonIOException | JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		return expectedSpendLkRoutes;
	}

	// //////////////////////////////////////////
	// use this method for visualization actual//
	// //////////////////////////////////////////
	@Test
	public void PRINT() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				null, // to type
				null, // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		this.comparator.sort(actualSpendLk);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonResult = gson.toJson(actualSpendLk);
		System.out.println(jsonResult);
	}

	// ////////////////////////////////////////////
	// use this method for visualization expected//
	// ////////////////////////////////////////////
	@Test
	public void PRINT_SAVED() throws Exception {
		List<SpendLkRoute> expectedSpendLk = getExpected("LAX-NRT-00.json");
		this.comparator.sort(expectedSpendLk);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonResult = gson.toJson(expectedSpendLk);
		System.out.println(jsonResult);
	}

	@Test
	public void MOW_LED_00() throws Exception {
		SpendInput spendInput = SpendInput.of("city", // from type
				"MOW",// from
				"city", // to type
				"LED", // to
				-1, // miles min
				500000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		String airlineCode = "SU";
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput, airlineCode);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("MOW-LED-00.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_LED_01() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"LED", // to
				-1, // miles min
				100000, // miles max
				"economy", // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		String airlineCode = "SU";
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput, airlineCode);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("SVO-LED-01.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_LED_02() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"LED", // to
				-1, // miles min
				100000, // miles max
				"economy", // class listOf service name
				"ticket", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("SVO-LED-02.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_LED_03() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"LED", // to
				-1, // miles min
				100000, // miles max
				"economy", // class listOf service name
				"all", // award type
				true, // afl only
				true // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("SVO-LED-03.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_LED_04() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"LED", // to
				-1, // miles min
				100000, // miles max
				"business", // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("SVO-LED-04.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_LED_05() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"LED", // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("SVO-LED-05.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_LED_06() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"LED", // to
				-1, // miles min
				100000, // miles max
				"business", // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("SVO-LED-06.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_LED_07() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"LED", // to
				-1, // miles min
				15000, // miles max
				"economy", // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("SVO-LED-07.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_VVO_01() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"VVO", // to
				-1, // miles min
				50000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("SVO-VVO-01.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_VVO_02() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"VVO", // to
				-1, // miles min
				50000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				null // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("SVO-VVO-02.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_CDG_00() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"CDG", // to
				-1, // miles min
				20000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("SVO-CDG-00.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_CDG_01() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"CDG", // to
				-1, // miles min
				20000, // miles max
				null, // class listOf service name
				"ticket", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("SVO-CDG-01.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_CDG_02() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"CDG", // to
				-1, // miles min
				20000, // miles max
				null, // class listOf service name
				"upgrade", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("SVO-CDG-02.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_CDG_03() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"CDG", // to
				-1, // miles min
				10000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		Assert.assertTrue(actualSpendLk.isEmpty());
	}

	@Test
	public void SVO_JFK_00() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"JFK", // to
				-1, // miles min
				50000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("SVO-JFK-00.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void LAX_NRT_00() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"LAX",// from
				"airport", // to type
				"NRT", // to
				-1, // miles min
				50000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("LAX-NRT-00.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_WORLD_00() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				null, // to type
				null, // to
				-1, // miles min
				50000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("SVO-WORLD-00.json");
		this.comparator.testIsPresent(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_WORLD_01() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"world", // to type
				null, // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this
				.getExpected("SVO-WORLD-01.json");
		this.comparator.testIsEquals(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_RU_00() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"country", // to type
				"RU", // to
				-1, // miles min
				50000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expectedSpendLk = this.getExpected("SVO-RU-00.json");
		this.comparator.testIsPresent(expectedSpendLk, actualSpendLk);

		expectedSpendLk = this.getExpected("SVO-CDG-00.json");
		this.comparator.testIsNotPresent(expectedSpendLk, actualSpendLk);

		expectedSpendLk = this.getExpected("SVO-JFK-00.json");
		this.comparator.testIsNotPresent(expectedSpendLk, actualSpendLk);

		expectedSpendLk = this.getExpected("LAX-NRT-00.json");
		this.comparator.testIsNotPresent(expectedSpendLk, actualSpendLk);
	}

	@Test
	public void SVO_CIS_00() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"region", // to type
				"CIS", // to
				-1, // miles min
				50000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
		List<SpendLkRoute> actualSpendLk = this.spendLkBuilder
				.getSpendLkRoutes(spendInput);
		List<SpendLkRoute> expeSpendLkcted = this.getExpected("SVO-RU-00.json");
		this.comparator.testIsPresent(expeSpendLkcted, actualSpendLk);

		expeSpendLkcted = this.getExpected("SVO-CDG-00.json");
		this.comparator.testIsNotPresent(expeSpendLkcted, actualSpendLk);

		expeSpendLkcted = this.getExpected("SVO-JFK-00.json");
		this.comparator.testIsNotPresent(expeSpendLkcted, actualSpendLk);

		expeSpendLkcted = this.getExpected("LAX-NRT-00.json");
		this.comparator.testIsNotPresent(expeSpendLkcted, actualSpendLk);
	}

}
