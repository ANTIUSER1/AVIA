package ru.integrotech.airline.searcher;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.integrotech.airline.core.bonus.ChargeRule;
import ru.integrotech.airline.register.RegisterCache;

import static ru.integrotech.airline.core.bonus.ChargeRule.TARIFF_CONDITION.*;

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

	public ChargeRule findRuleWithFlightCode(String flightCode) {

        for (ChargeRule rule : this.rulesRegister) {
            if (!this.isEmpty(rule.getFlightCode())
                    && rule.getFlightCode().contains(flightCode)
                    && rule.getBookingClass().equals("Y")) {

                return rule;
            }
        }

        return null;
    }
	
	public ChargeRule findCases01_04(ChargeRule rule, String bookingClassCode, String fareBasicCode) {

	    ChargeRule result = this.findCase01(rule, bookingClassCode, fareBasicCode);

		if (result == null) {
		    result = this.findCase02(rule, fareBasicCode);
        }

        if (result == null) {
            result = this.findCase03(rule, bookingClassCode);
        }

        if (result == null) {
            result = this.findCase04(rule);
        }

		return result;
	}

    public ChargeRule findCases05_07(String bookingClassCode, String fareBasicCode) {

	    ChargeRule result = null;

        for (ChargeRule rule : this.rulesRegister) {
            result = this.findCase05(rule, bookingClassCode, fareBasicCode);

            if (result == null) {
                result = this.findCase06(rule, fareBasicCode);
            }

            if (result == null) {
                result = this.findCase07(rule, bookingClassCode);
            }

            if (result != null) break;
        }

        return result;
    }

    public ChargeRule findCases08_09(String flightCode, String bookingClassCode) {

        ChargeRule result = null;

        for (ChargeRule rule : this.rulesRegister) {

            result = this.findCase08(rule, flightCode, bookingClassCode);

            if (result == null) {
                result = this.findCase09(rule, bookingClassCode);
            }
        }

        return result;
    }

    private ChargeRule findCase01(ChargeRule rule, String bookingClassCode, String fareBasicCode) {

		if (this.isEmpty(rule.getBookingClass())
			&& rule.getBookingClass().equals(bookingClassCode)
			&& this.isFitsByMasks(fareBasicCode, rule.getTariffMasks())) {

			return rule;
		}

		return null;
	}
	
	private ChargeRule findCase02(ChargeRule rule, String fareBasicCode) {

			if (rule.isAnyBookingClass()
				&& this.isFitsByMasks(fareBasicCode, rule.getTariffMasks())) {

				return rule;
			}

		return null;
	}
	
	private ChargeRule findCase03(ChargeRule rule, String bookingClassCode) {

			if (this.isEmpty(rule.getBookingClass())
				&& rule.getBookingClass().equals(bookingClassCode)
				&& rule.getTariffCondition() == A) {

				return rule;
			}

		return null;
	}
	
	private ChargeRule findCase04(ChargeRule rule) {

			if (rule.isAnyBookingClass()
				&& rule.getTariffCondition() == A) {

				return rule;
			}

		return null;
	}

	private ChargeRule findCase05(ChargeRule rule, String bookingClassCode, String fareBasicCode) {

			if (this.isEmpty(rule.getFlightCode())
				&& this.isEmpty(rule.getBookingClass())
				&& rule.getBookingClass().equals(bookingClassCode)
				&& this.isFitsByMasks(fareBasicCode, rule.getTariffMasks())) {

				return rule;
			}

		return null;
	}
	
	private ChargeRule findCase06(ChargeRule rule, String fareBasicCode) {

			if (this.isEmpty(rule.getFlightCode())
				&& rule.isAnyBookingClass()
				&& this.isFitsByMasks(fareBasicCode, rule.getTariffMasks())) {

				return rule;
			}

		return null;
	}

	private ChargeRule findCase07(ChargeRule rule, String bookingClassCode) {

			if (this.isEmpty(rule.getFlightCode())
				&& this.isEmpty(rule.getBookingClass())
				&& rule.getBookingClass().equals(bookingClassCode)
				&& rule.getTariffCondition() == A) {

				return rule;
			}

		return null;
	}

	private ChargeRule findCase08(ChargeRule rule, String flightCode, String bookingClassCode) {
		
			if (!this.isEmpty(rule.getFlightCode())
				&& rule.getFlightCode().contains(flightCode)
				&& this.isEmpty(rule.getBookingClass())
				&& rule.getBookingClass().equals(bookingClassCode)) {

				return rule;
			}

		return null;
	}

	private ChargeRule findCase09(ChargeRule rule, String bookingClassCode) {

			if (this.isEmpty(rule.getFlightCode())
				&& this.isEmpty(rule.getBookingClass())
				&& rule.getBookingClass().equals(bookingClassCode)) {

				return rule;
			}

		return null;
	}


	private boolean isFitsByMasks(String value, List<String> masks) {

		boolean result = false;

        if (!this.isEmpty(masks) && !this.isEmpty(value)) {

            for (String mask : masks) {
                Pattern p = Pattern.compile(mask);
                Matcher m = p.matcher(value);
                if (m.matches()) {
                    result = true;
                    break;
                }
            }
        }
		
		return result;
	}


	private boolean isEmpty(String string) {
		return string == null || string.isEmpty();
	}

	private boolean isEmpty(List<String> string) {
		return  string == null || string.size() == 0;
	}
	 
	 

}
