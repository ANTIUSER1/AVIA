package ru.integrotech.airline.register;

/**
 * class for read data in JSON format from remote register
 *
 *  Can be used in Spend project
 *
 */

class ServiceClassLimitRecord {

    private String svc_class_code;

    private String ap_from_code;

    private String ap_to_code;

    private String airline_code;

    String getSvcClassCode() {
        return svc_class_code;
    }

    String getApFromCode() {
        return ap_from_code;
    }

    String getApToCode() {
        return ap_to_code;
    }

    String getAirlineCode() {
        return airline_code;
    }

    @Override
    public String toString() {
        return "ServiceClassLimitRecord{" +
                "svc_class_code='" + svc_class_code + '\'' +
                ", ap_from_code='" + ap_from_code + '\'' +
                ", ap_to_code='" + ap_to_code + '\'' +
                ", airline_code='" + airline_code + '\'' +
                '}';
    }
}
