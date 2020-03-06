package ru.integrotech.su.outputparams.chargeLoyalty;

import ru.integrotech.airline.core.info.PassengerLoyaltyInfo;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.utils.NumberMethods;
import ru.integrotech.su.exceptions.UnsupportedParamException;
import ru.integrotech.su.inputparams.chargeLoyalty.ChargeLoyaltyRequest;

import java.util.Map;

public class ChargeLoyaltyBuilder {

    private static final String[] REGISTER_NAMES = new String[]{"localLoyaltyLevelCode"};

    public static ChargeLoyaltyBuilder of(RegisterCache cache) {
        ChargeLoyaltyBuilder result = new ChargeLoyaltyBuilder();
        result.cache = cache;
        return result;
    }

    public static String[] getRegisterNames() {
        return REGISTER_NAMES;
    }

    private RegisterCache cache;

    private ChargeLoyaltyBuilder() {
    }

    public ChargeLoyaltyResponse buildResponse(ChargeLoyaltyRequest request) throws UnsupportedParamException {
        PassengerLoyaltyInfo info = this.createInfo(request);
        this.initFields(info);
        return ChargeLoyaltyResponse.of(info);
    }

    private void initFields(PassengerLoyaltyInfo info) throws UnsupportedParamException {
        if (info.getPaymentType() == PassengerLoyaltyInfo.PAYMENT_TYPE.points) {
            info.setStatus(PassengerLoyaltyInfo.STATUS.illegal);
            info.setPoints(0);
        } else if (info.getPaymentType() == PassengerLoyaltyInfo.PAYMENT_TYPE.cash) {
            info.setStatus(PassengerLoyaltyInfo.STATUS.legal);
            info.setPoints(this.getPercent(info.getTariffSum(), this.getPercentVal(info)));
        }
    }
    
    public PassengerLoyaltyInfo createInfo(ChargeLoyaltyRequest request) throws UnsupportedParamException {
        int tariffSum = request.getTariffSum();
        PassengerLoyaltyInfo result = null;
        if (tariffSum > 0) {
            result = PassengerLoyaltyInfo.of(request.getPaymentType(), request.getTariffSum(), request.getAccountingCode());
        } else {
            String msg = String.format("%s %s", "Parameter tariffSum must be > 0 but received", tariffSum);
            throw new UnsupportedParamException(msg);
        }
        return result;
    }

    public int getPercent(int value, int percent) {
        return NumberMethods.getPercent(value, percent);
    }
    
    public int getPercentVal(PassengerLoyaltyInfo info) throws UnsupportedParamException {
    	Map<String, Integer> map = this.cache.getLoyaltyLevelCodeMap();
    	String accountingCode = info.getAccountingCode();
        int result = 0;
        if (map.containsKey(accountingCode)) {
            result = map.get(accountingCode);
        } else {
            String msg = String.format("%s %s %s", "Accounting code", accountingCode, "not found");
            throw new UnsupportedParamException(msg);
        }
        return result;
    }

}
