package ru.integrotech.su.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.integrotech.airline.register.RegisterLoader;
import ru.integrotech.su.common.spend.ClassOfService;
import ru.integrotech.su.exceptions.UnsupportedParamException;
import ru.integrotech.su.inputparams.spend.LocationInput;
import ru.integrotech.su.inputparams.spend.MilesInterval;
import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.spend.SpendBuilder;
import ru.integrotech.su.utils.ValidatorSpend;

public class SpendValidationTest {

	@BeforeClass
	public static void updateRegisters() {
		MockLoader.getInstance()
				.updateRegisters(MockLoader.REGISTERS_TYPE.MOCK,
						SpendBuilder.getRegisterNames());
	}

	private SpendBuilder spendBuilder;
	private ValidatorSpend validatorSpend;

	@Before
	public void init() {
		this.spendBuilder = SpendBuilder.of(MockLoader.getInstance()
				.getRegisterCache());
		this.validatorSpend = new ValidatorSpend();
	}

	@Test(expected = UnsupportedParamException.class)
	public void ORIGIN_NULL() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(null);
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
		this.validatorSpend.validateHardDataSpend(spendInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void ORIGIN_LOCATION_TYPE_NULL() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of(null, "SVO"));
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void ORIGIN_LOCATION_TYPE_EMPTY() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("  ", "SVO"));
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void ORIGIN_LOCATION_TYPE_INCORRECT() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("*** NOT EXIST ***", "SVO"));
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void ORIGIN_LOCATION_CODE_NULL() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", null));
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void ORIGIN_LOCATION_CODE_EMPTY() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "   "));
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void ORIGIN_LOCATION_CODE_INCORRECT() throws Exception {
		RegisterLoader.getInstance("").testReadLock();
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "*** NOT EXIST ***"));
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
		this.spendBuilder.getSpendRoutes(spendInput);
	}

	@Test
	public void DEST_NULL() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "LED"));
		spendInput.setDestination(null);
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
		this.spendBuilder.getSpendRoutes(spendInput);
	}

    @Test
    public void DEST_NULL_2() throws Exception {
        SpendInput spendInput = new SpendInput();
        spendInput.setOrigin(LocationInput.of("airport", "LED"));
        spendInput.setDestination(LocationInput.of(null, null));
        spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
        spendInput.setClassOfService(ClassOfService.of("economy"));
        spendInput.setAwardType("all");
        spendInput.setIsOnlyAfl(true);
        spendInput.setIsRoundTrip(true);
        spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
        this.spendBuilder.getSpendRoutes(spendInput);
    }

    @Test
    public void DEST_EMPTY() throws Exception {
        SpendInput spendInput = new SpendInput();
        spendInput.setOrigin(LocationInput.of("airport", "LED"));
        spendInput.setDestination(LocationInput.of("   ", "   "));
        spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
        spendInput.setClassOfService(ClassOfService.of("economy"));
        spendInput.setAwardType("all");
        spendInput.setIsOnlyAfl(true);
        spendInput.setIsRoundTrip(true);
        spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
        this.spendBuilder.getSpendRoutes(spendInput);
    }

	@Test(expected = UnsupportedParamException.class)
	public void DEST_LOCATION_TYPE_NULL() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "SVO"));
		spendInput.setDestination(LocationInput.of(null, "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void DEST_LOCATION_TYPE_EMPTY() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "SVO"));
		spendInput.setDestination(LocationInput.of("   ", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void DEST_LOCATION_TYPE_INCORRECT() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "SVO"));
		spendInput
				.setDestination(LocationInput.of("*** NOT EXISTS ***", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void DEST_LOCATION_CODE_EMPTY() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "LED"));
		spendInput.setDestination(LocationInput.of("airport", "   "));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void DEST_LOCATION_CODE_NULL() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "SVO"));
		spendInput.setDestination(LocationInput.of("airport", null));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void DEST_LOCATION_CODE_INCORRECT() throws Exception {
		RegisterLoader.getInstance("").testReadLock();
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "SVO"));
		spendInput.setDestination(LocationInput.of("airport",
				"*** NOT EXISTS ***"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
		this.spendBuilder.getSpendRoutes(spendInput);
	}

	@Test
	public void MILES_INTERVAL_NULL() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "SVO"));
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(null);
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
		this.spendBuilder.getSpendRoutes(spendInput);
        Assert.assertEquals(spendInput.getMilesInterval().getMilesMin(), 10000);
        Assert.assertEquals(spendInput.getMilesInterval().getMilesMax(), 250000);
	}

    @Test(expected = UnsupportedParamException.class)
    public void MILES_INTERVAL_EMPTY() throws Exception {
        SpendInput spendInput = new SpendInput();
        spendInput.setOrigin(LocationInput.of("airport", "SVO"));
        spendInput.setDestination(LocationInput.of("airport", "LED"));
        spendInput.setMilesInterval(MilesInterval.of(0,0));
        spendInput.setClassOfService(ClassOfService.of("economy"));
        spendInput.setAwardType("all");
        spendInput.setIsOnlyAfl(true);
        spendInput.setIsRoundTrip(true);
        spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
    }

    @Test(expected = UnsupportedParamException.class)
    public void MILES_INTERVAL() throws Exception {
        SpendInput spendInput = new SpendInput();
        spendInput.setOrigin(LocationInput.of("airport", "SVO"));
        spendInput.setDestination(LocationInput.of("airport", "LED"));
        spendInput.setMilesInterval(MilesInterval.of(2,1));
        spendInput.setClassOfService(ClassOfService.of("economy"));
        spendInput.setAwardType("all");
        spendInput.setIsOnlyAfl(true);
        spendInput.setIsRoundTrip(true);
        spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
    }

    @Test(expected = UnsupportedParamException.class)
    public void MILES_INTERVAL_NEGATIVE() throws Exception {
        SpendInput spendInput = new SpendInput();
        spendInput.setOrigin(LocationInput.of("airport", "SVO"));
        spendInput.setDestination(LocationInput.of("airport", "LED"));
        spendInput.setMilesInterval(MilesInterval.of(0,-1));
        spendInput.setClassOfService(ClassOfService.of("economy"));
        spendInput.setAwardType("all");
        spendInput.setIsOnlyAfl(true);
        spendInput.setIsRoundTrip(true);
        spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
    }


    @Test
	public void ROUND_TRIP_NULL() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "SVO"));
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(null);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
        this.spendBuilder.getSpendRoutes(spendInput);
    }

	@Test
	public void CLASS_OF_SERVICE_NULL() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "SVO"));
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(null);
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
        this.spendBuilder.getSpendRoutes(spendInput);
    }

	@Test(expected = UnsupportedParamException.class)
	public void CLASS_OF_SERVICE_CODE_NULL() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "SVO"));
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		ClassOfService classOfService = new ClassOfService();
		classOfService.setClassOfServiceName(null);
		spendInput.setClassOfService(classOfService);
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void CLASS_OF_SERVICE_CODE_EMPTY() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "SVO"));
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("   "));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void CLASS_OF_SERVICE_INCORRECT() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "SVO"));
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("*** NOT EXISTS ***"));
		spendInput.setAwardType("all");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void AWARD_TYPE_INCORRECT() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "SVO"));
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("*** NOT EXISTS ***");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
	}

	@Test
	public void AWARD_TYPE_EMPTY() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "SVO"));
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType("   ");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
        this.spendBuilder.getSpendRoutes(spendInput);
    }

	@Test
	public void AWARD_TYPE_NULL() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "SVO"));
		spendInput.setDestination(LocationInput.of("airport", "LED"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType(null);
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
        this.spendBuilder.getSpendRoutes(spendInput);
        Assert.assertEquals(spendInput.getAwardType(), "ALL");
    }

	@Test
	public void WORLD_REGION() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("airport", "SVO"));
		spendInput.setDestination(LocationInput.of("worldRegion", "CIS"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("economy"));
		spendInput.setAwardType(null);
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("ru");
		this.validatorSpend.validateHardDataSpend(spendInput);
        this.spendBuilder.getSpendRoutes(spendInput);
    }

    @Test
    public void WORLD() throws Exception {
        SpendInput spendInput = new SpendInput();
        spendInput.setOrigin(LocationInput.of("airport", "SVO"));
        spendInput.setDestination(LocationInput.of("world", null));
        spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
        spendInput.setClassOfService(ClassOfService.of("economy"));
        spendInput.setAwardType(null);
        spendInput.setIsOnlyAfl(true);
        spendInput.setIsRoundTrip(true);
        spendInput.setLang("ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
        this.spendBuilder.getSpendRoutes(spendInput);
    }

	@Test
	public void UP_AND_LOW_CASES() throws Exception {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of("AirPorT", "sVo"));
		spendInput.setDestination(LocationInput.of("aIrpOrt", "LeD"));
		spendInput.setMilesInterval(MilesInterval.of(-1, 100000));
		spendInput.setClassOfService(ClassOfService.of("eCoNoMy"));
		spendInput.setAwardType("tIcKet");
		spendInput.setIsOnlyAfl(true);
		spendInput.setIsRoundTrip(true);
		spendInput.setLang("Ru");
        this.validatorSpend.validateHardDataSpend(spendInput);
        this.spendBuilder.getSpendRoutes(spendInput);
    }

}
