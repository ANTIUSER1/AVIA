package ru.integrotech.airline.register;

import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.airline.SubTariff;
import ru.integrotech.airline.core.airline.Tariff;

/**
 * class for read data in JSON format from remote register
 *
 *  Can be used in Charge project
 *
 */

class TariffRecord {

    private String fare_group;

    private String svc_class_code;

    private String airline_code;

    private String fare_code;

    private String charge_coef;

    private String booking_class;

    private String weight_svc_class;

    private String weight_fare_group;

    ServiceClass toServiceClass() {
        return ServiceClass.of( this.svc_class_code,
                Integer.valueOf(this.weight_svc_class),
                this.fare_group,
                Integer.valueOf(this.weight_fare_group),
                this.fare_code,
                this.booking_class,
                Integer.valueOf(this.charge_coef));
    }

    Tariff toTariff() {
        return Tariff.of(   this.fare_group,
                Integer.valueOf(this.weight_fare_group),
                this.fare_code,
                this.booking_class,
                Integer.valueOf(this.charge_coef));
    }

    SubTariff toSubTariff() {
        return SubTariff.of(this.fare_code,
                this.booking_class,
                Integer.valueOf(this.charge_coef));
    }

    String getFareGroup() {
        return fare_group;
    }

    String getSvcClassCode() {
        return svc_class_code;
    }

    String getAirlineCode() {
        return airline_code;
    }

    String getFareCode() {
        return fare_code;
    }

    String getChargeCoef() {
        return charge_coef;
    }

    String getBookingClass() {
        return booking_class;
    }

    String getWeightSvcClass() {
        return weight_svc_class;
    }

    String getWeightFareGroup() {
        return weight_fare_group;
    }

    @Override
    public String toString() {
        return "TariffRecord{" +
                "fare_group='" + fare_group + '\'' +
                ", svc_class_code='" + svc_class_code + '\'' +
                ", airline_code='" + airline_code + '\'' +
                ", fare_code='" + fare_code + '\'' +
                ", charge_coef='" + charge_coef + '\'' +
                ", booking_class='" + booking_class + '\'' +
                ", weight_svc_class='" + weight_svc_class + '\'' +
                ", weight_fare_group='" + weight_fare_group + '\'' +
                '}';
    }
}
