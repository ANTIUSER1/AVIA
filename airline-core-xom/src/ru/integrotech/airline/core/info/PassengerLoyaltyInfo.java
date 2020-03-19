package ru.integrotech.airline.core.info;

/**
 * Describes the flight of exact passenger in exact flight
 *
 * Used in ChargeLoyalty (PKL) project
 *
 */

public class PassengerLoyaltyInfo {

    public static PassengerLoyaltyInfo of(String paymentType, int tariffSum, String accountingCode) {
        PassengerLoyaltyInfo result = new PassengerLoyaltyInfo();
        try {
            PAYMENT_TYPE payment_type = PAYMENT_TYPE.valueOf(paymentType);
            result.setPaymentType(payment_type);
            result.setTariffSum(tariffSum);
            result.setAccountingCode(accountingCode);
        } catch (IllegalArgumentException e) {
            result.setErrCode("[payment type] parameter is incorrect");
            result.setErrMessage(e.getMessage());
        }

        return result;
    }

    private PAYMENT_TYPE paymentType;

    private int tariffSum;

    private String accountingCode;

    private STATUS status;

    private int points;
    
    private String errCode;

    private String errMessage;

    private int percent;

    
    public PAYMENT_TYPE getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PAYMENT_TYPE paymentType) {
        this.paymentType = paymentType;
    }

    public int getTariffSum() {
        return tariffSum;
    }

    public void setTariffSum(int tariffSum) {
        this.tariffSum = tariffSum;
    }

    public String getAccountingCode() {
        return accountingCode;
    }

    public void setAccountingCode(String accountingCode) {
        this.accountingCode = accountingCode;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public enum PAYMENT_TYPE {
        cash,
        points
    }

    public enum STATUS {
        legal,
        illegal
    }
}
