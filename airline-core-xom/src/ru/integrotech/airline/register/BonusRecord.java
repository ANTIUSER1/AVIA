package ru.integrotech.airline.register;

import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.bonus.Bonus;

import java.util.Date;

/**
 * class for read data in JSON format from remote register
 *
 *  Used in Spend project
 *
 */

class BonusRecord {

    private String type;

    private String svc_class_code_1;

    private String svc_class_code_2;

    private int value;

    private String bonus_route_code;

    private String transferer_type;

    private int is_light_award;

    private Date valid_from;

    private Date valid_to;

    private boolean isLight() {
        return  this.is_light_award == 1;
    }

    String createRouteKey() {
        return this.bonus_route_code + this.transferer_type;
    }

    Bonus toBonus() {
        return Bonus.of(type,
                ServiceClass.SERVICE_CLASS_TYPE.valueOf(svc_class_code_1),
                svc_class_code_2.equals("-")? null : ServiceClass.SERVICE_CLASS_TYPE.valueOf(svc_class_code_2),
                value,
                this.isLight(),
                valid_from,
                valid_to);
    }

    String getType() {
        return type;
    }

    String getSvcClassCode1() {
        return svc_class_code_1;
    }

    String getSvcClassCode2() {
        return svc_class_code_2;
    }

    int getValue() {
        return value;
    }

    String getBonusRouteCode() {
        return bonus_route_code;
    }

    String getTransfererType() {
        return transferer_type;
    }

    int getIsLightAward() {
        return is_light_award;
    }

    Date getValidFrom() {
        return valid_from;
    }

    Date getValidTo() {
        return valid_to;
    }

    @Override
    public String toString() {
        return "BonusRecord{" +
                "type='" + type + '\'' +
                ", svc_class_code_1='" + svc_class_code_1 + '\'' +
                ", svc_class_code_2='" + svc_class_code_2 + '\'' +
                ", value=" + value +
                ", bonus_route_code='" + bonus_route_code + '\'' +
                ", transferer_type='" + transferer_type + '\'' +
                ", is_light_award=" + is_light_award +
                ", valid_from=" + valid_from +
                ", valid_to=" + valid_to +
                '}';
    }
}
