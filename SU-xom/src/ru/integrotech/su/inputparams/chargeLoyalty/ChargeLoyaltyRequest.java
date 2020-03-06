package ru.integrotech.su.inputparams.chargeLoyalty;

import javax.xml.bind.annotation.XmlTransient;

public class ChargeLoyaltyRequest {

    public static ChargeLoyaltyRequest of(String paymentType, int tariffSum, String accountingCode) {
        ChargeLoyaltyRequest result = new ChargeLoyaltyRequest();
        result.setPaymentType(paymentType);
        result.setData(Data.of(tariffSum, accountingCode));
        return result;
    }

    private String paymentType;

    private Data data;

    private ChargeLoyaltyRequest() {
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType.toLowerCase();
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @XmlTransient
    public int getTariffSum() {
        return this.data.getTariffSum();
    }

    @XmlTransient
    public String getAccountingCode() {
        return  this.data.getAccountingCode();
    }
}
