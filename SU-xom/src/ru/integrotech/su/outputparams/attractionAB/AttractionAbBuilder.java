package ru.integrotech.su.outputparams.attractionAB;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.integrotech.airline.core.flight.PassengerChargeInfo;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.su.inputparams.attractionAB.AttractionAbInput;
import ru.integrotech.su.inputparams.attractionAB.AttractionAbUtils;

public class AttractionAbBuilder {
	
	public static AttractionAbBuilder of(RegisterCache registerCache) {
		AttractionAbBuilder result = new AttractionAbBuilder();
		result.cache = registerCache;
		return result;		
	}
	
	private RegisterCache cache;

	private AttractionAbBuilder() {
		
	}
	 
	public AttractionAbOutput buildResult(AttractionAbInput input) {
		
		AttractionAbUtils utils = AttractionAbUtils.of(this.cache);
		List<PassengerChargeInfo> charges = utils.toPassengerCharge(input);
		AttractionAbOutput result = new AttractionAbOutput();
		int miles = 0;
		
		if (charges != null) {
			
			Set<String> statuses = new HashSet<>();
			int tierLevelFactor = utils.getTierLevelFactor(input);
			
			for (PassengerChargeInfo charge : charges) {
				if (charge.getDistance() != 0) {
					this.update(charge, tierLevelFactor);
				}
				miles = miles + charge.getDistance();
				statuses.add(charge.getChargeStatus());
			}
			
			result.setMiles(miles);
			result.setStatus(this.getStatus(statuses));
			
		} else {
			result.setMiles(0);
			result.setStatus("nodata");
		}
		
		return result;
	}

	private String getStatus(Set<String> statuses) {
		
		String result = "distance";
		
		if (statuses.size() == 1) {
			result = statuses.iterator().next();
		}
		
		return result;
	}
	
	private boolean isInternational(PassengerChargeInfo charge) {
		Airport origin = charge.getOrigin();
		Airport destination = charge.getDestination();
		return !origin.getCity().getCountry().equals(destination.getCity().getCountry());
	}

	private void update(PassengerChargeInfo charge, int tierLevelFactor) {

		int multiplier = 0;

		if (charge.getDistanceCoeff() == 0 ) {
			multiplier = charge.getChargeCoeff();
		} else {
			multiplier = charge.getChargeCoeff() * charge.getDistanceCoeff() / 100;
		}

		int minBonusMiles = charge.getAirline().getMinMilesCharge();
		String milesLimitation = charge.getAirline().getMinMilesLimit();
		boolean isInternational = this.isInternational(charge);
		int bonusMiles = 0;
		int qualifyingMiles = 0;
		int promoMiles = 0; // current value
	
		if (multiplier <= 100) {

			qualifyingMiles = charge.getDistance() * multiplier / 100;

			if (qualifyingMiles != 0
					&& qualifyingMiles < minBonusMiles
					&& (milesLimitation.equals("A")
						|| (milesLimitation.equals("I") && isInternational))) {

				qualifyingMiles = minBonusMiles;
			}
			bonusMiles = qualifyingMiles * tierLevelFactor / 100;
		}
		else {
			bonusMiles = charge.getDistance() * multiplier / 100;
		}
		charge.setDistance(bonusMiles + promoMiles);
	}
	
	
	
	
	 
	
}
