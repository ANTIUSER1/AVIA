package ru.integrotech.airline.searcher;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.integrotech.airline.core.bonus.ChargeRule;
import ru.integrotech.airline.register.RegisterCache;

/**
 * algorithm about:
 * https://confluence.ramax.ru/pages/viewpage.action?pageId=54108215
 */

public class ChargeSearcher {
	
	public static ChargeSearcher of(RegisterCache cache) {
		return new ChargeSearcher(cache);
	}
		
	private final List<ChargeRule> rulesRegister;

	private ChargeSearcher(RegisterCache registerCache) {
		this.rulesRegister = registerCache.getChargeRules();
	}
	
	public ChargeRule findCase01(String flightCode, String bookingClassCode, String fareBasicCode) {
		
		for (ChargeRule rule : this.rulesRegister) {
			if (!this.isEmpty(rule.getFlightCode())
				&& rule.getFlightCode().equals(flightCode)
				&& !this.isEmpty(rule.getBookingClass())
				&& rule.getBookingClass().equals(bookingClassCode)
				&& this.isFitsByMasks(fareBasicCode, rule.getTariffMasks())) {

				return rule;
			}
		}
		
		return null;
	}
	
	public ChargeRule findCase02(String flightCode, String fareBasicCode) {

		for (ChargeRule rule : this.rulesRegister) {
			if (!this.isEmpty(rule.getFlightCode())
				&& rule.getFlightCode().equals(flightCode)
				&& rule.isAnyBookingClass()
				&& this.isFitsByMasks(fareBasicCode, rule.getTariffMasks())) {

				return rule;
			}
		}
		
		return null;
	}
	
	public ChargeRule findCase03(String flightCode, String bookingClassCode) {

		for (ChargeRule rule : this.rulesRegister) {
			if (!this.isEmpty(rule.getFlightCode())
				&& rule.getFlightCode().equals(flightCode)
				&& !this.isEmpty(rule.getBookingClass())
				&& rule.getBookingClass().equals(bookingClassCode)
				&& rule.getTariffCondition().equals("anyOne")) {

				return rule;
			}
		}
		
		return null;
	}
	
	public ChargeRule findCase04(String flightCode) {

		for (ChargeRule rule : this.rulesRegister) {
			if (!this.isEmpty(rule.getFlightCode())
				&& rule.getFlightCode().equals(flightCode)
				&& rule.isAnyBookingClass()
				&& rule.getTariffCondition().equals("anyOne")) {

				return rule;
			}
		}
		
		return null;
	}

	public ChargeRule findCase05(String bookingClassCode, String fareBasicCode) {

		for (ChargeRule rule : this.rulesRegister) {
			if (this.isEmpty(rule.getFlightCode())
				&& !this.isEmpty(rule.getBookingClass())
				&& rule.getBookingClass().equals(bookingClassCode)
				&& this.isFitsByMasks(fareBasicCode, rule.getTariffMasks())) {

				return rule;
			}
		}

		return null;
	}
	
	public ChargeRule findCase06(String fareBasicCode) {

		for (ChargeRule rule : this.rulesRegister) {
			if (this.isEmpty(rule.getFlightCode())
				&& rule.isAnyBookingClass()
				&& this.isFitsByMasks(fareBasicCode, rule.getTariffMasks())) {

				return rule;
			}
		}

		return null;
	}

	public ChargeRule findCase07(String bookingClassCode) {

		for (ChargeRule rule : this.rulesRegister) {
			if (this.isEmpty(rule.getFlightCode())
				&& !this.isEmpty(rule.getBookingClass())
				&& rule.getBookingClass().equals(bookingClassCode)
				&& rule.getTariffCondition().equals("anyOne")) {

				return rule;
			}
		}

		return null;
	}

	public ChargeRule findCase08(String flightCode, String bookingClassCode) {
		
		for (ChargeRule rule : this.rulesRegister) {
			if (!this.isEmpty(rule.getFlightCode())
				&& rule.getFlightCode().equals(flightCode)
				&& !this.isEmpty(rule.getBookingClass())
				&& rule.getBookingClass().equals(bookingClassCode)) {

				return rule;
			}
		}
		
		return null;
	}

	public ChargeRule findCase09(String bookingClassCode) {

		for (ChargeRule rule : this.rulesRegister) {
			if (this.isEmpty(rule.getFlightCode())
				&& !this.isEmpty(rule.getBookingClass())
				&& rule.getBookingClass().equals(bookingClassCode)) {

				return rule;
			}
		}

		return null;
	}


	private boolean isFitsByMasks(String value, List<String> masks) {
		
		boolean result = false;
		
		for (String mask : masks) {
			 Pattern p = Pattern.compile(mask);  
		     Matcher m = p.matcher(value);  
		     if (m.matches()) {
		    	 result = true;
		    	 break;
		     }
		}
		
		return result;
	}


	private boolean isEmpty(String string) {
		return  string == null || string.isEmpty();
	}
	 
	 

}
