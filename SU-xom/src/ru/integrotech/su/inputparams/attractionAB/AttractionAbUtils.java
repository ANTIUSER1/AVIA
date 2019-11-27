package ru.integrotech.su.inputparams.attractionAB;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.bonus.ChargeRule;
import ru.integrotech.airline.core.bonus.TicketDesignator;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.PassengerChargeInfo;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.searcher.ChargeSearcher;

public class AttractionAbUtils {
	
	public static AttractionAbUtils of(RegisterCache registers) {
		return new AttractionAbUtils(registers);
	}
	
	private final RegisterCache registers;
		
	private AttractionAbUtils(RegisterCache registers) {
		this.registers = registers;
	}
	
	public int getTierLevelFactor(AttractionAbInput input) {
		String passengerTierLevel = input.getData().getTierCode();
		if (this.isEmpty(passengerTierLevel)) {
			passengerTierLevel = "basic";
		}
		return registers.getLoyaltyMap().get(passengerTierLevel).getFactor() + 100;
	}

	public List<PassengerChargeInfo> toPassengerCharge(AttractionAbInput input) {
		
		List<PassengerChargeInfo> result = new ArrayList<>();
		Airline airline = this.registers.getAirline("SU"); //current value
		
		for (Segment segment : input.getData().getSegments()) {
			
			Airport origin = this.registers.getAirport(segment.getOrignIATA());
			Airport destination = this.registers.getAirport(segment.getDestinationIATA());
			Flight flight = origin.getOutcomeFlight(destination, airline);
			int distance = 0;
			int chargeCoeff = 100;
			int distanceCoeff = 100;
			String chargeStatus = "distance";
			ChargeRule rule = null;
						
			if (flight == null) {
				return null;
			} else {
				distance = flight.getDistance();
			}

			TicketDesignator designator = this.getTicketDesignator(segment);

			if (designator != null && designator.isToZeroMiles()) {
				distance = 0;

			} else if (!this.isEmpty(segment.getBookingClassCode())
					|| !this.isEmpty(segment.getFareBasisCode())) {

						rule = this.findRule(segment, flight.getCode());

						if (rule != null) {
							rule = this.findRuleForApply(segment, rule, flight.getCode());

							chargeCoeff = rule.getChargeCoeff();
							distanceCoeff = rule.getDistanceCoeff();
							chargeStatus = "full";

						}
					}

			result.add(PassengerChargeInfo.of(rule != null ? rule.isBasic() : false,
											  airline,
											  origin, 
											  destination, 
											  distance, 
											  chargeCoeff, 
											  distanceCoeff,
											  chargeStatus));
		}
		
		return result;
	}

	private TicketDesignator getTicketDesignator(Segment segment) {

		TicketDesignator result = null;

		if (!this.isEmpty(segment.getTicketDesignator())) {
			for (TicketDesignator designator : this.registers.getTicketDesignators()) {
				Pattern p = Pattern.compile(designator.getMask());
				Matcher m = p.matcher(segment.getTicketDesignator());
				if (m.matches()) {
					result = designator;
					break;
				}
			}
		}

		return result;
	}


	private ChargeRule findRule(Segment segment, String flightCode) {
		
		ChargeSearcher searcher = ChargeSearcher.of(this.registers);

		ChargeRule rule = searcher.findCase01(flightCode,
											  segment.getBookingClassCode(),
											  segment.getFareBasisCode());

		if (rule == null) {
			rule = searcher.findCase02(flightCode, segment.getFareBasisCode());
		}
		
		if (rule == null) {
			rule = searcher.findCase03(flightCode, segment.getBookingClassCode());
		}
		
		if (rule == null) {
			rule = searcher.findCase04(flightCode);
		}
		
		if (rule == null) {
			rule = searcher.findCase05(segment.getBookingClassCode(),
						     			segment.getFareBasisCode());
		}
		
		if (rule == null) {
			rule = searcher.findCase06(segment.getFareBasisCode());
		}

		if (rule == null) {
			rule = searcher.findCase07(segment.getBookingClassCode());
		}
		
		return rule;
	}


	private ChargeRule findRuleForApply(Segment segment, ChargeRule rule, String flightCode) {
		
		ChargeRule result = null;
		ChargeSearcher searcher = ChargeSearcher.of(this.registers);
		
		if (rule.getChargeCondition().equals("other")) {
			result = rule;
		} else if (rule.getChargeCondition().equals("byBookingClass")) {

			result = searcher.findCase08(flightCode, segment.getBookingClassCode());

			if (result == null) {
				result = searcher.findCase09(segment.getBookingClassCode());
			}

			if (result != null) {
				result = this.compareAndCopy(rule, result);
			}
		}

		return result;
	}

	private ChargeRule compareAndCopy(ChargeRule rule, ChargeRule baseRule) {

		ChargeRule result = baseRule;

		if (rule.getChargeCoeff() != 0) {
			result = ChargeRule.copy(baseRule);
			result.setDistanceCoeff(rule.getChargeCoeff() * result.getChargeCoeff() / 100);
		}

		return result;
	}

	private boolean isEmpty(String string) {
		return  string == null || string.isEmpty();
	}


}
