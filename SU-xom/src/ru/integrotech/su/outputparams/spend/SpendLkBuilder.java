package ru.integrotech.su.outputparams.spend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.su.inputparams.spend.SpendInput;

/**
 * this class takes input params (SpendInput) and returns back output params
 * (ResultMilesSpendLk) logic is:
 *
 * var 1: 1.ResultMilesSpendLk result = buildResult(SpendInput spendInput) <br />
 * var 2:<br />
 * 1.List<Route> routes = getRoutes(SpendInput spendInput)<br />
 * 2.ODM rules with(routes, milesMin, milesMax) - instead of method
 * updateFitsMilesIntervals(routes, milesMin, milesMax) in SpendBuilder class<br />
 * 3.ResultMilesSpendLk result = buildResult(List<Route> routes, SpendInput
 * spendInput)
 * <hr />
 *
 * class for build spendLKResponse from spendLKRequest
 *
 * data(private SpendBuilder spendBuilder;)
 *
 */
public class SpendLkBuilder {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param spendBuilder
	 * @return
	 */
	public static SpendLkBuilder of(SpendBuilder spendBuilder) {
		SpendLkBuilder res = new SpendLkBuilder();
		res.setSpendBuilder(spendBuilder);
		return res;
	}

	private SpendBuilder spendBuilder;

	public void setSpendBuilder(SpendBuilder spendBuilder) {
		this.spendBuilder = spendBuilder;
	}

	/** method for use in ODM */
	public ResultMilesSpendLk buildResult(SpendInput spendInput) {
		List<SpendLkRoute> routes = this.getSpendLkRoutes(spendInput);
		return ResultMilesSpendLk.of(routes);
	}

	/** method for use in ODM */
	public ResultMilesSpendLk buildResult(List<Route> routes,
			SpendInput spendInput) {
		this.spendBuilder.executeAllFilters(routes, spendInput);
		this.spendBuilder.bonusSummation(routes);
		List<SpendRoute> spendRoutes = this.spendBuilder.buildSpendRoutes(
				routes, spendInput);
		List<SpendLkRoute> spendLkRoutes = this.buildSpendLkRoutes(spendRoutes,
				spendInput);
		return ResultMilesSpendLk.of(spendLkRoutes);
	}

	/** method for use in TESTS */
	public List<SpendLkRoute> getSpendLkRoutes(SpendInput spendInput) {
		return this.getSpendLkRoutes(spendInput, null);
	}

	/** method for use in TESTS */
	public List<SpendLkRoute> getSpendLkRoutes(SpendInput spendInput,
			String airlineCode) {

		spendInput.setIsOnlyAfl(true);

		List<Route> routes = this.getRoutes(spendInput);
		this.spendBuilder.executeAllFilters(routes, spendInput);
		this.spendBuilder.bonusSummation(routes);
		this.spendBuilder.updateFitsMilesIntervals(routes, spendInput);
		this.replaceUO(routes);

		List<SpendRoute> spendRoutes = this.spendBuilder.buildSpendRoutes(
				routes, spendInput);

		return this.buildSpendLkRoutes(spendRoutes, spendInput);
	}

	public List<Route> getRoutes(SpendInput spendInput) {
		return this.spendBuilder.getRoutes(spendInput, null);
	}

	public List<SpendLkRoute> buildSpendLkRoutes(List<SpendRoute> spendRoutes,
			SpendInput spendInput) {

		Map<String, SpendLkRoute> resultMap = new HashMap<>();
		Map<String, Map<String, RequiredAward>> routeMap = this
				.createRouteMap(spendRoutes);

		for (SpendRoute spendRoute : spendRoutes) {
			if (!resultMap.containsKey(spendRoute.getCityKey())) {
				resultMap.put(spendRoute.getCityKey(),
						SpendLkRoute.of(spendRoute));
			}
		}

		for (Map.Entry<String, SpendLkRoute> entry : resultMap.entrySet()) {
			List<RequiredAward> awardList = new ArrayList<>(routeMap.get(
					entry.getKey()).values());
			Collections.sort(awardList);
			entry.getValue().getRequiredAwards().addAll(awardList);
		}

		List<SpendLkRoute> result = new ArrayList<>(resultMap.values());

		int milesMin = spendInput.getMilesInterval().getMilesMin();
		int milesMax = spendInput.getMilesInterval().getMilesMax();

		for (SpendLkRoute spendLkRoute : result) {
			spendLkRoute.updateFitsMilesIntervals(milesMin, milesMax);
		}

		Collections.sort(result);

		return result;
	}

	private Map<String, Map<String, RequiredAward>> createRouteMap(
			List<SpendRoute> spendRoutes) {
		Map<String, Map<String, RequiredAward>> result = new HashMap<>();
		for (SpendRoute spendRoute : spendRoutes) {
			if (result.containsKey(spendRoute.getCityKey())) {
				Map<String, RequiredAward> awardMap = result.get(spendRoute
						.getCityKey());
				Map<String, RequiredAward> updateMap = this
						.createAwardMap(spendRoute);
				this.updateAwardMap(awardMap, updateMap);
			} else {
				result.put(spendRoute.getCityKey(),
						this.createAwardMap(spendRoute));
			}
		}
		return result;
	}

	private Map<String, RequiredAward> createAwardMap(SpendRoute spendRoute) {
		Map<String, RequiredAward> result = new HashMap<>();
		for (MileCost mileCost : spendRoute.getMileCosts()) {
			Map<String, RequiredAward> transitionalResult = new HashMap<>();
			for (RequiredAward award : mileCost.getRequiredAward()) {
				String key = award.awardKey();
				if (transitionalResult.containsKey(key)) {
					RequiredAward savedAward = transitionalResult.get(key);
					if (award.compareTo(savedAward) < 0) {
						transitionalResult.put(key, award);
					}
				} else {
					transitionalResult.put(key, award);
				}
			}
			for (Map.Entry<String, RequiredAward> entry : transitionalResult
					.entrySet()) {
				String key = entry.getKey();
				RequiredAward award = entry.getValue();
				if (result.containsKey(key)) {
					result.get(key).setValue(
							result.get(key).getValue() + award.getValue());
				} else {
					result.put(key, award);
				}
			}
		}
		return result;
	}

	private void updateAwardMap(Map<String, RequiredAward> awardMap,
			Map<String, RequiredAward> updateMap) {
		for (String key : updateMap.keySet()) {
			if (awardMap.containsKey(key)) {
				RequiredAward savedAward = awardMap.get(key);
				RequiredAward newAward = updateMap.get(key);
				if (newAward.compareTo(savedAward) < 0) {
					awardMap.put(key, newAward);
				}
			} else {
				RequiredAward newAward = updateMap.get(key);
				awardMap.put(key, newAward);
			}
		}
	}

	public void replaceUO(List<Route> routes) {
		for (Route route : routes) {
			this.replaceUO(route.getAflBonuses());
			this.replaceUO(route.getScyteamBonuses());
			for (Flight flight : route.getFlights()) {
				this.replaceUO(flight.getAflBonuses());
				this.replaceUO(flight.getScyteamBonuses());
			}
		}
	}

	private void replaceUO(Set<Bonus> bonuses) {
		List<Bonus> bonusesForRemove = new ArrayList<>();
		List<Bonus> bonusesForAdd = new ArrayList<>();
		for (Bonus bonus : bonuses) {
			if (bonus.getType().equals(Bonus.BONUS_TYPE.UO)) {
				bonusesForRemove.add(bonus);
				Bonus bonusUC = Bonus.of("UC", bonus.getServiceClass(),
						bonus.getUpgradeServiceClass(), bonus.getValue(),
						bonus.isLight(), bonus.getValidFrom(),
						bonus.getValidTo());
				bonusesForAdd.add(bonusUC);
			}
		}
		for (Bonus bonus : bonusesForRemove) {
			bonuses.remove(bonus);
		}
		bonuses.addAll(bonusesForAdd);
	}
}
