package ru.integrotech.su.outputparams.chargeLoyalty;

//
import ru.integrotech.airline.core.info.PassengerLoyaltyInfo;

public class ChargeLoyaltyResponse {

	public static ChargeLoyaltyResponse of(PassengerLoyaltyInfo info) {
		ChargeLoyaltyResponse result = new ChargeLoyaltyResponse();
		if (info.getErrCode() != null || info.getErrMessage() != null) {
			result.setErrCode(info.getErrCode());
			result.setErrMessage(info.getErrMessage());
		} else {
			result.setData(Data.of(info));
		}
		return result;
	}

	public static ChargeLoyaltyResponse of(String errCode, String errMessage) {
		ChargeLoyaltyResponse result = new ChargeLoyaltyResponse();
		result.setErrCode(errCode);
		result.setErrMessage(errMessage);
		return result;
	}

	private String errCode;

	private String errMessage;

	private Data data;

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

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
