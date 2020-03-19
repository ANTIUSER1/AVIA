package ru.integrotech.airline.searcher;

import ru.integrotech.airline.core.bonus.MilesRule;
import ru.integrotech.airline.core.info.PassengerMilesInfo;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.utils.StringMethods;

import java.util.ArrayList;
import java.util.List;

import static ru.integrotech.airline.core.info.PassengerMilesInfo.*;

/**
 * class contains logic for search miles rules by given parameters
 *
 * Can be used in all projects
 *
 */

public class MileRuleSearcher {

    public static MileRuleSearcher of(RegisterCache cache) {
        MileRuleSearcher result = new MileRuleSearcher();
        result.setRulesRegister(cache.getMilesRules());
        return result;
    }

    private List<MilesRule> rulesRegister;

    public List<MilesRule> getRulesRegister() {
        return rulesRegister;
    }

    public void setRulesRegister(List<MilesRule> rulesRegister) {
        this.rulesRegister = rulesRegister;
    }

    public void findMileRules(List<PassengerMilesInfo> charges) {

        for (PassengerMilesInfo info : charges) {

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

    private MilesRule findByTicketDesignator(PassengerMilesInfo info) {

        List<MilesRule> result = new ArrayList<>();
        String designator = info.getTickedDesignator();

        if (!StringMethods.isEmpty(designator)) {

            for (MilesRule rule : this.rulesRegister) {
                if (rule.getRuleType() == MilesRule.RULE_TYPE.TD) {
                    if (StringMethods.isFitsByRegexMasks(designator, rule.getTickedDesignatorMasks())) {
                        if (this.fitsByIkmRule(rule, info)) {
                            result.add(rule);
                        }
                    }
                }
            }
        }

        return this.findRuleWithMinMiles(result);
    }

    private MilesRule findByFare(PassengerMilesInfo info) {

        List<MilesRule> result = new ArrayList<>();
        String fareCode = info.getFareCode();

        if (!StringMethods.isEmpty(fareCode)) {

            for (MilesRule rule : this.rulesRegister) {
                if (rule.getRuleType() == MilesRule.RULE_TYPE.PF || rule.getRuleType() == MilesRule.RULE_TYPE.SF) {
                    if (StringMethods.isFitsByRegexMasks(fareCode, rule.getFareCodeMasks())) {
                        if (this.fitsByIkmRule(rule, info)) {
                            result.add(rule);
                        }
                    }
                }
            }
        }

        return this.findRuleWithMinMiles(result);
    }

    private MilesRule findByBookingClass(PassengerMilesInfo info) {

        List<MilesRule> result = new ArrayList<>();
        String bookingClassCode = info.getBookingClassCode();

        if (!StringMethods.isEmpty(bookingClassCode)) {

            for (MilesRule rule : this.rulesRegister) {
                 if (bookingClassCode.equals(rule.getBookingClassCode())) {
                     if (this.fitsByIkmRule(rule, info)) {
                         result.add(rule);
                     }
                 }
            }
        }

        return this.findRuleWithMinMiles(result);
    }

    private boolean fitsByIkmRule(MilesRule rule, PassengerMilesInfo info) {

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

    private MilesRule findRuleWithMinMiles(List<MilesRule> milesRules) {
        MilesRule result = null;
        if (milesRules != null && milesRules.size() != 0) {

            if (milesRules.size() == 1) {
                result = milesRules.get(0);

            } else {
                int minMiles = Integer.MAX_VALUE;
                for (MilesRule rule : milesRules) {
                    if (rule.getBonusPercent() < minMiles) {
                        minMiles = rule.getBonusPercent();
                        result = rule;
                    }
                }
            }
        }
        return result;
    }


}
