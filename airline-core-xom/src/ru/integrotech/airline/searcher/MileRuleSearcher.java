package ru.integrotech.airline.searcher;

import ru.integrotech.airline.core.bonus.MilesRule;
import ru.integrotech.airline.core.flight.PassengerChargeInfo;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.utils.StringMethods;

import java.util.List;

import static ru.integrotech.airline.core.flight.PassengerChargeInfo.*;

public class MileRuleSearcher {

    public static MileRuleSearcher of(RegisterCache cache) {
        return new MileRuleSearcher(cache.getMilesRules());
    }

    private final List<MilesRule> rulesRegister;

    private MileRuleSearcher(List<MilesRule> rulesRegister) {
        this.rulesRegister = rulesRegister;
    }

    public void findMileRules(List<PassengerChargeInfo> charges) {

        for (PassengerChargeInfo info : charges) {

            if (info.getStatus() == Status.nodata) {
                info.setTotalBonusMiles(0);
                continue;
            }

            MilesRule foundRule = this.findByTicketDesignator(info);
            if (foundRule != null) {
                info.setDistanceCoeff(foundRule.getBonusPercent()/100.00);
                info.setStatus(Status.full);
                continue;
            }

            foundRule = this.findByFare(info);
            if (foundRule != null) {
                info.setDistanceCoeff(foundRule.getBonusPercent()/100.00);
                info.setStatus(Status.full);
                continue;
            }

            foundRule = this.findByBookingClass(info);
            if (foundRule != null) {
                info.setDistanceCoeff(foundRule.getBonusPercent()/100.00);
                info.setStatus(Status.full);
                continue;
            }

            //if rule was not found
            info.setStatus(Status.nodata);
            info.setTotalBonusMiles(0);
        }
    }

    private MilesRule findByTicketDesignator(PassengerChargeInfo info) {

        MilesRule result = null;
        String designator = info.getTickedDesignator();

        if (!StringMethods.isEmpty(designator)) {

            for (MilesRule rule : this.rulesRegister) {
                if (rule.getRuleType() == MilesRule.RULE_TYPE.TD) {
                    if (StringMethods.isFitsByRegexMasks(designator, rule.getTickedDesignatorMasks())) {
                        if (this.fitsByIkmRule(rule, info)) {
                            result = rule;
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }

    private MilesRule findByFare(PassengerChargeInfo info) {
        MilesRule result = null;
        String fareCode = info.getFareCode();

        if (!StringMethods.isEmpty(fareCode)) {

            for (MilesRule rule : this.rulesRegister) {
                if (rule.getRuleType() == MilesRule.RULE_TYPE.PF || rule.getRuleType() == MilesRule.RULE_TYPE.SF) {
                    if (StringMethods.isFitsByRegexMasks(fareCode, rule.getFareCodeMasks())) {
                        if (this.fitsByIkmRule(rule, info)) {
                            result = rule;
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }

    private MilesRule findByBookingClass(PassengerChargeInfo info) {
        MilesRule result = null;
        String bookingClassCode = info.getBookingClassCode();

        if (!StringMethods.isEmpty(bookingClassCode)) {

            for (MilesRule rule : this.rulesRegister) {
                 if (bookingClassCode.equals(rule.getBookingClassCode())) {
                     if (this.fitsByIkmRule(rule, info)) {
                         result = rule;
                         break;
                     }
                 }
            }
        }

        return result;
    }

    private boolean fitsByIkmRule(MilesRule rule, PassengerChargeInfo info) {

        boolean result = false;

        if (rule.getIkmRule() == null) {

            result = true;

        } else if (rule.getIkmRule() == MilesRule.IKM_RULE.IN
                && rule.getAirportPairs().contains(info.getFlightCode())) {

            result = true;

        } else if (rule.getIkmRule() == MilesRule.IKM_RULE.EX
                && !rule.getAirportPairs().contains(info.getFlightCode())){

            result = true;
        }

        return result;

    }


}