package ru.integrotech.airline.register;

class BonusRouteRecord {

    private String code;

    private String zone_from;

    private String zone_to;

    private String zone_via;

    private String transferer_type;

    String getNativeCode() {
        if (zone_via == null || zone_via.isEmpty()) {
            return String.format("%s%s%s", zone_from, zone_to, transferer_type);
        } else {
            return String.format("%s%s%s%s", zone_from, zone_via, zone_to, transferer_type);
        }
    }

    String getReverseCode() {
        if (zone_via != null && zone_via.isEmpty()) {
            return String.format("%s%s%s", zone_to, zone_from, transferer_type);
        } else {
            return String.format("%s%s%s%s", zone_to, zone_via, zone_from, transferer_type);
        }
    }

    String getCode() {
        return code;
    }

    String getZoneFrom() {
        return zone_from;
    }

    String getZoneTo() {
        return zone_to;
    }

    String getZoneVia() {
        return zone_via;
    }

    String getTransfererType() {
        return transferer_type;
    }

    boolean isEmptyCode() {
        return this.code == null || this.code.isEmpty();
    }

    @Override
    public String toString() {
        return "BonusRouteRecord{" +
                "code='" + code + '\'' +
                ", zone_from='" + zone_from + '\'' +
                ", zone_to='" + zone_to + '\'' +
                ", zone_via='" + zone_via + '\'' +
                ", transferer_type='" + transferer_type + '\'' +
                '}';
    }
}
