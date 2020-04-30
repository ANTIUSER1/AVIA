package ru.integrotech.su.outputparams.attractionAB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.integrotech.airline.core.bonus.Loyalty;
import ru.integrotech.airline.core.info.PassengerMilesInfo;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.searcher.MileRuleSearcher;
import ru.integrotech.airline.utils.NumberMethods;
import ru.integrotech.su.inputparams.attractionAB.AttractionAbInput;
import ru.integrotech.su.inputparams.attractionAB.AttractionAbUtils;

/**
 * class for build attractionAbBuilderResponse from attractionAbBuilderRequest
 *
 * data(private RegisterCache cache; private AttractionAbUtils utils;)
 */
public class AttractionAbBuilder {

	/**
	 * names of necessary registers
	 */
	public static String[] getRegisterNames() {
		return REGISTER_NAMES;
	}

	/**
	 * names of necessary registers
	 */
	private static final String[] REGISTER_NAMES = new String[] { "airline",
			"region", "country", "city", "airport", "pair", "tierLevel",
			"mileAccrualRule" };

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param registerCache
	 * @return
	 */
	public static AttractionAbBuilder of(RegisterCache registerCache) {
		AttractionAbBuilder result = new AttractionAbBuilder();
		result.setCache(registerCache);
		return result;
	}

	private RegisterCache cache;

	private AttractionAbUtils utils;

	private void setCache(RegisterCache cache) {
		this.cache = cache;
	}

	/*
	 * Method for tests
	 */
	public AttractionAbOutput buildResult(AttractionAbInput input) {

		List<PassengerMilesInfo> infos = this.getInfos(input);
		int tierPassengerFactor = this.getTierLevelFactor(input);

		// this cycle must no be represented in ODM.
		for (PassengerMilesInfo info : infos) {
			this.calculateMiles(info, tierPassengerFactor);
		}

		return this.buildResult(infos, input);
	}

	public List<PassengerMilesInfo> getInfos(AttractionAbInput input) {
		this.utils = AttractionAbUtils.of(this.cache);
		List<PassengerMilesInfo> infos = utils.toPassengerChargeInfo(input);
		MileRuleSearcher searcher = MileRuleSearcher.of(this.cache);
		searcher.findMileRules(infos);
		return infos;
	}

	public AttractionAbOutput buildResult(List<PassengerMilesInfo> infos,
			AttractionAbInput input) {
		List<Segment> segments = new ArrayList<>();
		int totalBonusMiles = 0;
		Map<PassengerMilesInfo.Status, Integer> statusMap = new HashMap<>();
		for (PassengerMilesInfo info : infos) {
			segments.add(Segment.of(info));
			totalBonusMiles = totalBonusMiles + info.getTotalBonusMiles();
			if (statusMap.containsKey(info.getStatus())) {
				int counter = statusMap.get(info.getStatus()) + 1;
				statusMap.put(info.getStatus(), counter);
			} else {
				statusMap.put(info.getStatus(), 1);
			}
		}

		return AttractionAbOutput.of(statusMap, totalBonusMiles, segments);
	}

	public int getTierLevelFactor(AttractionAbInput input) {
		return this.utils.getTierLevelFactor(input);
	}

	public int getMultiplication(double... args) {
		return NumberMethods.getMultiplication(args);
	}

	/*
	 * this logic must be implicated by ODM rules
	 */
	private void calculateMiles(PassengerMilesInfo info,
			int tierPassengerFactor) {

		if (info.getStatus() == PassengerMilesInfo.Status.nodata)
			return;

		int coeff = info.getDistanceCoeff();

		if (coeff == 0) {
			info.setTotalBonusMiles(0);

		} else if (coeff > 0 && coeff <= 100) {

			int bonusDistance = this.getBonusDistance(info, coeff);

			int additionalMiles = NumberMethods.getPercent (bonusDistance, tierPassengerFactor);
			info.setTotalBonusMiles(bonusDistance + additionalMiles);

		} else if (coeff > 100) {

			int bonusDistance = this.getBonusDistance(info, coeff);

			int additionalMiles = 0;

			if (tierPassengerFactor > 0) {

                if (this.getNewDistance(info, coeff) < info.getMinBonusMiles()) {
                    bonusDistance = info.getMinBonusMiles();
                    additionalMiles = NumberMethods.getPercent(bonusDistance, tierPassengerFactor);

                } else {
                    additionalMiles = NumberMethods.getPercent(this.getMinDistance(info), tierPassengerFactor);
                }
            }

			info.setTotalBonusMiles(bonusDistance + additionalMiles);
		}
	}

    private int getMinDistance(PassengerMilesInfo info) {

        int result = info.getFlightDistance();

        if (result < info.getMinBonusMiles()) {
            result = info.getMinBonusMiles();
        }

        return result;
    }

	private int getNewDistance(PassengerMilesInfo info, int coeff) {

		int result = this.getMinDistance(info);

		return NumberMethods.getPercent(result, coeff);
	}

	private int getBonusDistance(PassengerMilesInfo info, int coeff) {

		int result = this.getNewDistance(info, coeff);

		if (result < info.getMinBonusMiles()) {
			result = info.getMinBonusMiles();
		}

		return result;
	}

}
