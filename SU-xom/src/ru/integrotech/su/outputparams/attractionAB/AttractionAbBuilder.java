package ru.integrotech.su.outputparams.attractionAB;

import java.util.*;

import ru.integrotech.airline.core.flight.PassengerChargeInfo;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.searcher.MileRuleSearcher;
import ru.integrotech.airline.utils.NumberMethods;
import ru.integrotech.su.inputparams.attractionAB.AttractionAbInput;
import ru.integrotech.su.inputparams.attractionAB.AttractionAbUtils;

public class AttractionAbBuilder {

	public static String[] getRegisterNames() {
		return REGISTER_NAMES;
	}

	private static final String[] REGISTER_NAMES = new String[]
			        {"airline",
					"region",
					"country",
					"city",
					"airport",
					"pair",
					"tierLevel",
					"mileAccrualRule"};
	
	public static AttractionAbBuilder of(RegisterCache registerCache) {
		AttractionAbBuilder result = new AttractionAbBuilder();
		result.cache = registerCache;
		return result;		
	}
	
	private RegisterCache cache;
	
	private AttractionAbUtils utils;

	private AttractionAbBuilder() {
	}
	 
	/*
	 * Method for tests
	 */
	public AttractionAbOutput buildResult(AttractionAbInput input) {
		
		List<PassengerChargeInfo> infos = this.getInfos(input);
		double tierPassengerFactor = this.getTierLevelFactor(input);
	    
		//this cycle must no be represented in ODM.
		for (PassengerChargeInfo info : infos) {
	            this.calculateMiles(info, tierPassengerFactor);
	    }

        return this.buildResult(infos, input);
	}
	
	public List<PassengerChargeInfo> getInfos(AttractionAbInput input) {
		this.utils = AttractionAbUtils.of(this.cache);
		List<PassengerChargeInfo> infos = utils.toPassengerChargeInfo(input);
        MileRuleSearcher searcher = MileRuleSearcher.of(this.cache);
        searcher.findMileRules(infos);
        return infos;
	}
	
	public AttractionAbOutput buildResult(List<PassengerChargeInfo> infos, AttractionAbInput input) {
	        List<Segment> segments = new ArrayList<>();
	        int totalBonusMiles = 0;
	        Map<PassengerChargeInfo.Status, Integer> statusMap = new HashMap<>();
	        for (PassengerChargeInfo info : infos) {
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
	
	public double getTierLevelFactor(AttractionAbInput input) {
		return this.utils.getTierLevelFactor(input);
	}

	public int getMultiplication(double...args) {
	    return NumberMethods.getMultiplication(args);
    }

	/*
	 * this logic must be implicated by ODM rules
	 */
	private void calculateMiles(PassengerChargeInfo info, double tierPassengerFactor) {

		if (info.getStatus() == PassengerChargeInfo.Status.nodata) return;

	    double coeff = info.getDistanceCoeff();

	    if (coeff == 0) {
	        info.setTotalBonusMiles(0);

	    } else if (coeff > 0 && coeff <= 1) {

	        int newDistance = (int) (info.getFlightDistance() * coeff);
            int additionalMiles = 0;

	        if (newDistance < info.getMinBonusMiles()) {
	            newDistance = info.getMinBonusMiles();
            }

            additionalMiles = (int) (tierPassengerFactor * newDistance);
            info.setTotalBonusMiles(newDistance + additionalMiles);

	    } else if (coeff > 1) {

	        int newDistance = (int) (info.getFlightDistance() * coeff);
            int additionalMiles = 0;

            if (newDistance < info.getMinBonusMiles()) {
                newDistance = info.getMinBonusMiles();
                additionalMiles = (int) (tierPassengerFactor * newDistance);

            } else {
                additionalMiles = (int) (tierPassengerFactor * info.getFlightDistance());
            }

            info.setTotalBonusMiles(newDistance + additionalMiles);
        }
    }


}
