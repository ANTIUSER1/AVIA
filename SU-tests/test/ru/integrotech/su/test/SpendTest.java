package ru.integrotech.su.test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.spend.SpendBuilder;
import ru.integrotech.su.outputparams.spend.SpendRoute;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import ru.integrotech.su.utils.ValidatorSpend;

/**
 * Tests for Spend project, used inner registers
 */

public class SpendTest {

	private static final String RESULTS_FOLDER = "test/ru/integrotech/su/resources/results/spendRoutes/";

	@BeforeClass
	public static void updateRegisters() {
		MockLoader.getInstance()
				.updateRegisters(MockLoader.REGISTERS_TYPE.MOCK,
						SpendBuilder.getRegisterNames());
	}

	private Comparator comparator;

	private SpendBuilder spendBuilder;

	private ValidatorSpend validatorSpend;


    @Before
	public void init() {
		this.comparator = Comparator.of(SpendRoute.class);
		this.spendBuilder = SpendBuilder.of(MockLoader.getInstance()
				.getRegisterCache());
        this.validatorSpend = new ValidatorSpend();
    }

	private List<SpendRoute> getExpected(String jsonName) {
		MockLoader loader = MockLoader.getInstance();
		List<SpendRoute> expectedSpendRoutes = null;
		try {
			JsonElement jsonElement = loader
					.loadJson(RESULTS_FOLDER + jsonName);
			expectedSpendRoutes = loader.getTestsCache().loadSpendRoutes(
					jsonElement);
		} catch (JsonIOException | JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		return expectedSpendRoutes;
	}

	// //////////////////////////////////////////
	// use this method for visualization actual//
	// //////////////////////////////////////////
	@Test
	public void PRINT() throws Exception {
        SpendInput spendInput = SpendInput.of("airport", // from type
                "SVO",// from
                "airport", // to type
                "LED", // to
                -1, // miles min
                100000, // miles max
                null, // class listOf service name
                "all", // award type
                false, // afl only
                null // is round trip
        );
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		this.comparator.sort(actualSpend);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonResult = gson.toJson(actualSpend);
		System.out.println(jsonResult);
	}

	// ////////////////////////////////////////////
	// use this method for visualization expected//
	// ////////////////////////////////////////////
	@Test
	public void PRINT_SAVED() throws Exception {
		List<SpendRoute> actualSpend = getExpected("FITS-MILES-INTERVAL.json");
		this.comparator.sort(actualSpend);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonResult = gson.toJson(actualSpend);
		System.out.println(jsonResult);
	}

	@Test
	public void SVO_VVO_01() throws Exception {
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
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this.getExpected("SVO-VVO-01.json");
		this.comparator.testIsEquals(expectedSpend, actualSpend);
	}

	@Test
	public void SVO_VVO_02() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"VVO", // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this.getExpected("SVO-VVO-02.json");
		this.comparator.testIsPresent(expectedSpend, actualSpend);
	}

	@Test
	public void SVO_AER_01() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"AER", // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this.getExpected("SVO-AER-01.json");
		this.comparator.testIsPresent(expectedSpend, actualSpend);
	}

	/*
	 * https://jira.ramax.ru/browse/AFLSITE-8889
	 */
	@Test
	public void SVO_AER_02() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"AER", // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				null // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this.getExpected("SVO-AER-02.json");
		this.comparator.testIsPresent(expectedSpend, actualSpend);
	}

	@Test
	public void SVO_KHV_01() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"KHV", // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this.getExpected("SVO-KHV-01.json");
		this.comparator.testIsPresent(expectedSpend, actualSpend);
	}

	@Test
	public void SVO_KJA_01() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"KJA", // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this.getExpected("SVO-KJA-01.json");
		this.comparator.testIsPresent(expectedSpend, actualSpend);
	}

	@Test
	public void SVO_ROV_01() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"ROV", // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this.getExpected("SVO-ROV-01.json");
		this.comparator.testIsPresent(expectedSpend, actualSpend);
	}

	@Test
	public void SVO_SVX_01() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"SVX", // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this.getExpected("SVO-SVX-01.json");
		this.comparator.testIsPresent(expectedSpend, actualSpend);
	}

	@Test
	public void SVO_UFA_01() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"UFA", // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this.getExpected("SVO-UFA-01.json");
		this.comparator.testIsPresent(expectedSpend, actualSpend);
	}

	// http://support.integrotechnologies.ru/issues/20890
	@Test
	public void FITS_MILES_INTERVAL() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"country", // to type
				"RU", // to
				10000, // miles min
				20000, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this
				.getExpected("FITS-MILES-INTERVAL.json");
		this.comparator.testIsPresent(expectedSpend, actualSpend);
	}

	// http://support.integrotechnologies.ru/issues/20888
	// task not closed yet
	@Test
	public void SVO_LED_01() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"LED", // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this.getExpected("SVO-LED-01.json");
		this.comparator.testIsEquals(expectedSpend, actualSpend);
	}

	// http://support.integrotechnologies.ru/issues/20890
	@Test
	public void SVO_LED_02() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"LED", // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this
				.getExpected("SVO-LED-direct-01.json");
		this.comparator.testIsPresent(expectedSpend, actualSpend);
	}

	// http://support.integrotechnologies.ru/issues/20890
	@Test
	public void SVO_LED_03() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to
				"LED", // to type
				10000, // miles min
				10001, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this
				.getExpected("SVO-LED-direct-02.json");
		this.comparator.testIsPresent(expectedSpend, actualSpend);
	}

	// http://support.integrotechnologies.ru/issues/20890
	@Test
	public void SVO_LED_04() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to
				"LED", // to type
				9500, // miles min
				9999, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this
				.getExpected("SVO-LED-direct-01.json");
		this.comparator.testIsNotPresent(expectedSpend, actualSpend);
	}

	// http://support.integrotechnologies.ru/issues/20890
	@Test
	public void SVO_LED_05() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"country", // to type
				"RU", // to
				9000, // miles min
				9999, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this.getExpected("SVO-LED-01.json");
		this.comparator.testIsNotPresent(expectedSpend, actualSpend);
	}

	// http://support.integrotechnologies.ru/issues/20894
	// task not closed yet
	@Test
	public void SVO_ROV_SVX_01() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"SVX", // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this.getExpected("SVO-ROV-SVX.json");
		this.comparator.testIsPresent(expectedSpend, actualSpend);
	}

	// http://support.integrotechnologies.ru/issues/20894
	// task not closed yet
	@Test
	public void KJA_SVO_VVO_01() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"KJA",// from
				"airport", // to type
				"VVO", // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				true, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this.getExpected("KJA-SVO-VVO.json");
		this.comparator.testIsPresent(expectedSpend, actualSpend);
	}

	@Test
	public void IS_ROUND_TRIP_NULL() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				"airport", // to type
				"LED", // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				null // is round trip
		);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this.getExpected("IS-ROUND-TRIP-NULL.json");
		this.comparator.testIsEquals(expectedSpend, actualSpend);
	}

	// http://support.integrotechnologies.ru/issues/21198
	// http://support.integrotechnologies.ru/issues/21200
	@Test
	public void SORT_CITIES() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				null, // to type
				null, // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		Collections.sort(actualSpend);

		for (int i = 0; i < actualSpend.size() - 1; i++) {
			Assert.assertTrue(actualSpend.get(i + 1).compareTo(
					actualSpend.get(i)) >= 0);
		}

	}

	@Test
	public void SVO_WORLD() throws Exception {
		SpendInput spendInput = SpendInput.of("airport", // from type
				"SVO",// from
				null, // to type
				null, // to
				-1, // miles min
				100000, // miles max
				null, // class listOf service name
				"all", // award type
				false, // afl only
				false // is round trip
				);
        this.validatorSpend.validateHardDataSpend(spendInput);
        List<SpendRoute> actualSpend = this.spendBuilder
				.getSpendRoutes(spendInput);
		List<SpendRoute> expectedSpend = this.getExpected("SVO-WORLD.json");
		this.comparator.testIsEquals(expectedSpend, actualSpend);

	}
}
