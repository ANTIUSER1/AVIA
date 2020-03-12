package ru.integrotech.su.inputparams.chargeLoyalty;

class Data {

    static Data of(int tariffSum, String accountingCode) {
        Data result = new Data();
        result.setTariffSum(tariffSum);
        result.setAccountingCode(accountingCode);
        return result;
    }

    private int tariffSum;

    private String accountingCode;

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
        this.accountingCode = accountingCode.toUpperCase();
    }
}
