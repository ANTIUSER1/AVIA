package ru.integrotech.su.outputparams.attractionAB;

import javax.xml.bind.annotation.XmlType;

import ru.integrotech.airline.core.flight.PassengerChargeInfo;

@XmlType(name = "OutputSegment")
public class Segment {

    public static Segment of(PassengerChargeInfo info){
        Segment result = new Segment();
        result.setOriginIATA(info.getOrigin().getCode());
        result.setDestinationIATA(info.getDestination().getCode());
        result.setStatus(info.getStatus());
        result.setMiles(info.getTotalBonusMiles());
        return result;
    }

    private String originIATA;

    private String destinationIATA;

    private PassengerChargeInfo.Status status;

    private int miles;

    Segment() {
    }

    public String getOriginIATA() {
        return originIATA;
    }

    public void setOriginIATA(String originIATA) {
        this.originIATA = originIATA;
    }

    public String getDestinationIATA() {
        return destinationIATA;
    }

    public void setDestinationIATA(String destinationIATA) {
        this.destinationIATA = destinationIATA;
    }

    public PassengerChargeInfo.Status getStatus() {
        return status;
    }

    public void setStatus(PassengerChargeInfo.Status status) {
        this.status = status;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }
}
