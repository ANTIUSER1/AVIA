package ru.integrotech.su.outputparams.spend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.searcher.BonusSearcher;
import ru.integrotech.airline.utils.BonusFilters;
import ru.integrotech.su.inputparams.route.RoutesInput;
import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.outputparams.route.RoutesBuilder;

/**
 * this class takes input params (SpendInput) and returns back output params
 * (ResultMilesSpend) logic is:
 *
 * var 1: 1.ResultMilesSpend result = buildResult(SpendInput spendInput)<br />
 *
 * var 2: <br />
 * 1.List<Route> routes = getRoutes(SpendInput spendInput)<br />
 * 2.ODM rules with(routes, milesMin, milesMax) - instead of method
 * updateFitsMilesIntervals(routes, milesMin, milesMax) in this class<br />
 * 3.ResultMilesSpend result = buildResult(List<Route> routes, SpendInput
 * spendInput)
 * 
 * <hr />
 * class for build spendResponse from spendRequest
 *
 * data(private RegisterCache cache; private RoutesBuilder routesBuilder;
 * private BonusSearcher bonusSearcher; private final Airline afl;)
 */

public class SpendBuilder {

	/**
	 * names of necessary registers
	 * */
	public static String[] getRegisterNames() {
		return REGISTER_NAMES;
	}

	/**
	 * names of necessary registers
	 * */
	private static final String[] REGISTER_NAMES = new String[] { "airline",
			"region", "country", "city", "airport", "pair",
			"serviceClassLimit", "bonusRoute", "award", "wrongRoute" };

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param cache
	 * @return
	 */
	public static SpendBuilder of(RegisterCache cache) {
		SpendBuilder res = new SpendBuilder();
		res.setCache(cache);
		res.setRoutesBuilder(RoutesBuilder.of(cache));
		res.setBonusSearcher(BonusSearcher.of(cache));

		res.setAfl(cache.getAirline(Airline.AFL_CODE));

		return res;
	}

	public void setCache(RegisterCache cache) {
		this.cache = cache;
	}

	public void setRoutesBuilder(RoutesBuilder routesBuilder) {
		this.routesBuilder = routesBuilder;
	}

	public void setBonusSearcher(BonusSearcher bonusSearcher) {
		this.bonusSearcher = bonusSearcher;
	}

	public void setAfl(Airline afl) {
		this.afl = afl;
	}

	private RegisterCache cache;

	private RoutesBuilder routesBuilder;

	private BonusSearcher bonusSearcher;

	private Airline afl;

	/*
	 * method for use in ODM in spendRuleFlow contains program and business
	 * logic
	 */
	public ResultMilesSpend buildResult(SpendInput spendInput) throws Exception {

		List<SpendRoute> routes = this.getSpendRoutes(spendInput);
		return ResultMilesSpend.of(routes);
	}

	/*
	 * method for use in ODM in spendJavaFlow contains program logic only
	 */
	public ResultMilesSpend buildResult(List<Route> routes,
			SpendInput spendInput) {
		List<SpendRoute> spendRoutes = this
				.buildSpendRoutes(routes, spendInput);
		return ResultMilesSpend.of(spendRoutes);
	}

	/* method for use in TESTS */
	public List<SpendRoute> getSpendRoutes(SpendInput spendInput)
			throws Exception {

		return this.getSpendRoutes(spendInput, null);
	}

	/* method for use in TESTS */
	public List<SpendRoute> getSpendRoutes(SpendInput spendInput,
			String airlineCode) throws Exception {

		List<Route> routes = this.getRoutes(spendInput, airlineCode);
		this.executeAllFilters(routes, spendInput);
		this.bonusSummation(routes);
		this.updateFitsMilesIntervals(routes, spendInput);
		return this.buildSpendRoutes(routes, spendInput);
	}

	public List<Route> getRoutes(SpendInput spendInput) throws Exception {
		return this.getRoutes(spendInput, null);
	}

	public List<Route> getRoutes(SpendInput spendInput, String airlineCode)
			throws Exception {
		RoutesInput routesInput = RoutesInput.of(spendInput, airlineCode);
		List<Route> routes = this.routesBuilder.getRoutes(routesInput);

		for (Route route : routes) {
			this.bonusSearcher.findBonuses(route, spendInput.getIsOnlyAfl());
		}

		return routes;
	}

	/**
	 * method for use in ODM in spendJavaFlow contains program logic only
	 */
	public List<SpendRoute> buildSpendRoutes(List<Route> routes,
			SpendInput spendInput) {

		boolean aflOnly = spendInput.getIsOnlyAfl();
		List<SpendRoute> result = new ArrayList<>();

		for (Route route : routes) {
			if (route.isOperatesBy(this.afl)) {
				SpendRoute bonusRoad = SpendRoute.ofAfl(route);
				if (!bonusRoad.isInvalid()) {
					result.add(bonusRoad);
				}
			}

			if (!aflOnly && route.otherAirlinesIsPresent(this.afl)) {
				SpendRoute bonusRoad = SpendRoute.ofScyteam(route);
				if (!bonusRoad.isInvalid()) {
					result.add(bonusRoad);
				}
			}
		}

		return result;
	}

	/**
	 * this is a business logic, it should works by ODM rules or calls from ODM
	 * rules
	 */
	public void executeAllFilters(List<Route> routes, SpendInput spendInput) {
		for (Route route : routes) {
			this.executeAllFilters(route, spendInput);
		}
	}

	/**
	 * this is a business logic, it should works by ODM rules or calls from ODM
	 * rules
	 */
	public void executeFiltersByInputParams(List<Route> routes,
			SpendInput spendInput) {
		for (Route route : routes) {
			this.executeFiltersByInputParams(route, spendInput);
		}
	}

	/**
	 * this is a business logic, it should works by ODM rules or calls from ODM
	 * rules
	 */
	public void executeFiltersByMilesInterval(List<Route> routes,
			SpendInput spendInput) {
		for (Route route : routes) {
			this.executeFiltersByMilesInterval(route, spendInput);
		}
	}

	/**
	 * this is a business logic, it should works by ODM rules or calls from ODM
	 * rules
	 */
	public void executeFiltersByCommonTypes(List<Route> routes,
			SpendInput spendInput) {
		for (Route route : routes) {
			if (!route.isWrong()) {
				BonusFilters.byCommonTypes(route);
			}
		}
	}

	/**
	 * this is a business logic, it should works by ODM rules or calls from ODM
	 * rules
	 */
	private void executeAllFilters(Route route, SpendInput spendInput) {

		int milesMin = spendInput.getMilesInterval().getMilesMin();
		int milesMax = spendInput.getMilesInterval().getMilesMax();
		boolean aflOnly = spendInput.getIsOnlyAfl();
		Boolean isRoundTrip = spendInput.getIsRoundTrip();
		ServiceClass.SERVICE_CLASS_TYPE serviceClassType = this
				.getServiceClassType(spendInput);
		String award = spendInput.getAwardType();

		if (route.isOperatesBy(this.afl)) {
			BonusFilters.byInputParams(route.getAflBonuses(), serviceClassType,
					route.getOrigin(), award, isRoundTrip, route.isDirect(),
					route.isWrong());
			BonusFilters.byMilesInterval(route.getAflBonuses(), milesMin,
					milesMax);
			for (Flight flight : route.getFlights()) {
				BonusFilters.byInputParams(flight.getAflBonuses(),
						serviceClassType, flight.getOrigin(), award,
						isRoundTrip, false, route.isWrong());
				BonusFilters.byMilesInterval(flight.getAflBonuses(), milesMin,
						milesMax);
			}
		}

		if (!aflOnly && route.otherAirlinesIsPresent(this.afl)) {
			BonusFilters.byInputParams(route.getScyteamBonuses(),
					serviceClassType, route.getOrigin(), award, isRoundTrip,
					route.isDirect(), route.isWrong());
			BonusFilters.byMilesInterval(route.getScyteamBonuses(), milesMin,
					milesMax);
			for (Flight flight : route.getFlights()) {
				BonusFilters.byInputParams(flight.getScyteamBonuses(),
						serviceClassType, flight.getOrigin(), award,
						isRoundTrip, false, route.isWrong());
				BonusFilters.byMilesInterval(flight.getScyteamBonuses(),
						milesMin, milesMax);
			}
		}

		if (!route.isWrong()) {
			BonusFilters.byCommonTypes(route);
		}
	}

	/**
	 * this is a business logic, it should works by ODM rules or calls from ODM
	 * rules
	 */
	private void executeFiltersByInputParams(Route route, SpendInput spendInput) {

		boolean aflOnly = spendInput.getIsOnlyAfl();
		boolean isRoundTrip = spendInput.getIsRoundTrip();
		ServiceClass.SERVICE_CLASS_TYPE serviceClassType = this
				.getServiceClassType(spendInput);
		String award = spendInput.getAwardType();

		if (route.isOperatesBy(this.afl)) {
			BonusFilters.byInputParams(route.getAflBonuses(), serviceClassType,
					route.getOrigin(), award, isRoundTrip, route.isDirect(),
					route.isWrong());
			for (Flight flight : route.getFlights()) {
				BonusFilters.byInputParams(flight.getAflBonuses(),
						serviceClassType, flight.getOrigin(), award,
						isRoundTrip, false, route.isWrong());
			}
		}

		if (!aflOnly && route.otherAirlinesIsPresent(this.afl)) {
			BonusFilters.byInputParams(route.getScyteamBonuses(),
					serviceClassType, route.getOrigin(), award, isRoundTrip,
					route.isDirect(), route.isWrong());
			for (Flight flight : route.getFlights()) {
				BonusFilters.byInputParams(flight.getScyteamBonuses(),
						serviceClassType, flight.getOrigin(), award,
						isRoundTrip, false, route.isWrong());
			}
		}
	}

	/**
	 * this is a business logic, it should works by ODM rules or calls from ODM
	 * rules
	 */
	private void executeFiltersByMilesInterval(Route route,
			SpendInput spendInput) {

		int milesMin = spendInput.getMilesInterval().getMilesMin();
		int milesMax = spendInput.getMilesInterval().getMilesMax();
		boolean aflOnly = spendInput.getIsOnlyAfl();
		if (route.isOperatesBy(this.afl)) {
			BonusFilters.byMilesInterval(route.getAflBonuses(), milesMin,
					milesMax);
			for (Flight flight : route.getFlights()) {
				BonusFilters.byMilesInterval(flight.getAflBonuses(), milesMin,
						milesMax);
			}
		}

		if (!aflOnly && route.otherAirlinesIsPresent(this.afl)) {
			BonusFilters.byMilesInterval(route.getScyteamBonuses(), milesMin,
					milesMax);
			for (Flight flight : route.getFlights()) {
				BonusFilters.byMilesInterval(flight.getScyteamBonuses(),
						milesMin, milesMax);
			}
		}
	}

	public ServiceClass.SERVICE_CLASS_TYPE getServiceClassType(
			SpendInput spendInput) {

		String classOfServiceName = null;
		ServiceClass.SERVICE_CLASS_TYPE serviceClassType = null;

		if (spendInput.getClassOfService() != null) {
			classOfServiceName = spendInput.getClassOfService()
					.getClassOfServiceName();
		}

		if (classOfServiceName != null) {
			serviceClassType = ServiceClass.SERVICE_CLASS_TYPE
					.valueOf(classOfServiceName);
		}

		return serviceClassType;
	}

	/**
	 * this is a business logic, it should works by ODM rules or calls from ODM
	 * rules
	 */
	public void updateFitsMilesIntervals(List<Route> routes,
			SpendInput spendInput) {

		int milesMin = spendInput.getMilesInterval().getMilesMin();
		int milesMax = spendInput.getMilesInterval().getMilesMax();

		for (Route route : routes) {
			this.updateFitsMilesInterval(route, milesMin, milesMax);
		}
	}

	/**
	 * this is a business logic, it should works by ODM rules or calls from ODM
	 * rules
	 */
	private void updateFitsMilesInterval(Route route, int milesMin, int milesMax) {
		for (Bonus bonus : route.getAflBonuses()) {
			bonus.setFitsMilesInterval(milesMin, milesMax);
		}

		for (Bonus bonus : route.getScyteamBonuses()) {
			bonus.setFitsMilesInterval(milesMin, milesMax);
		}

		for (Flight flight : route.getFlights()) {
			this.updateFitsMilesInterval(flight, milesMin, milesMax);
		}
	}

	/**
	 * 
	 * this is a business logic, it should works by ODM rules or calls from ODM
	 * rules
	 */
	private void updateFitsMilesInterval(Flight flight, int milesMin,
			int milesMax) {
		for (Bonus bonus : flight.getAflBonuses()) {
			bonus.setFitsMilesInterval(milesMin, milesMax);
		}

		for (Bonus bonus : flight.getScyteamBonuses()) {
			bonus.setFitsMilesInterval(milesMin, milesMax);
		}
	}

	/**
	 * this is a business logic, it should works by ODM rules or calls from ODM
	 * rules
	 */
	public void bonusSummation(List<Route> routes) {
		for (Route route : routes) {
			this.aflBonusSummation(route);
		}
	}

	private void aflBonusSummation(Route route) {
		if (route.getAflBonuses().size() > 0 || route.getFlights().size() < 2)
			return;
		Set<Bonus> bonuses1 = route.getFlights().get(0).getAflBonuses();
		Set<Bonus> bonuses2 = route.getFlights().get(1).getAflBonuses();
		Set<Bonus> newBonuses = new HashSet<>();
		if (bonuses1.size() == bonuses2.size()) {
			for (Bonus bonus1 : bonuses1) {
				for (Bonus bonus2 : bonuses2) {
					if (bonus1.getDescription().equals(bonus2.getDescription())) {
						Bonus newBonus = Bonus.of(bonus1.getType().name(),
								bonus1.getServiceClass(),
								bonus1.getUpgradeServiceClass(),
								bonus1.getValue() + bonus2.getValue(),
								bonus1.isLight(), bonus1.getValidFrom(),
								bonus1.getValidTo());
						newBonuses.add(newBonus);
					}
				}
			}
		}
		if (newBonuses.size() == bonuses1.size()) {
			route.setAflBonuses(newBonuses);
			bonuses1.clear();
			bonuses2.clear();
			route.setBonusSummation(true);
		}
	}

}
