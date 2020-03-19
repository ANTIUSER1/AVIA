package ru.integrotech.airline.core.info;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.airline.SubTariff;
import ru.integrotech.airline.core.airline.Tariff;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.location.Airport;

import java.util.*;

/**
 * Describes the flight of exact passenger in exact flight
 *
 * Used in Charge project
 *
 */

public class PassengerChargeInfo {

    public static List<PassengerChargeInfo> listOf(Flight flight,
                                                   List<ServiceClass> allowedClasses,
                                                   int factor,
                                                   boolean isRound,
                                                   Airline airline) {

        List<PassengerChargeInfo> result = new ArrayList<>();
        boolean isAfl = airline.getCode().equals("SU");

        for (ServiceClass serviceClass : allowedClasses) {
            for (Tariff tariff : PassengerChargeInfo.getTariffs(serviceClass)) {
                for (Map.Entry<Integer, List<SubTariff>> subTarifEntry : PassengerChargeInfo.getSubTariffs(tariff).entrySet()) {
                    Integer chargeCoff = subTarifEntry.getKey();
                    for (SubTariff subTariff : subTarifEntry.getValue()) {
                        PassengerChargeInfo passengerChargeInfo = new PassengerChargeInfo();
                        passengerChargeInfo.setOrigin(flight.getOrigin());
                        passengerChargeInfo.setDestination(flight.getDestination());
                        passengerChargeInfo.setAirline(airline);
                        passengerChargeInfo.setServiceClass(serviceClass);
                        passengerChargeInfo.setTariff(tariff);
                        passengerChargeInfo.setSubTariff(SubTariff.of(subTariff, isAfl));
                        passengerChargeInfo.setDistance(flight.getDistance());
                        passengerChargeInfo.setChargeCoeff(chargeCoff);
                        passengerChargeInfo.initFields(factor, isRound);
                        result.add(passengerChargeInfo);
                    }
                }
            }
        }
        return result;
    }

    public static PassengerChargeInfo of(Airport origin,
                                         Airport destination,
                                         Airline airline,
                                         ServiceClass serviceClass,
                                         Tariff tariff,
                                         SubTariff subTariff,
                                         int distance,
                                         int chargeCoeff) {

        PassengerChargeInfo result = new PassengerChargeInfo();
        result.setOrigin(origin);
        result.setDestination(destination);
        result.setAirline(airline);
        result.setServiceClass(serviceClass);
        result.setTariff(tariff);
        result.setSubTariff(subTariff);
        result.setDistance(distance);
        result.setChargeCoeff(chargeCoeff);
 		return result;
		
	}


    private static List<Tariff> getTariffs(ServiceClass serviceClass) {
        List<Tariff> result = new ArrayList<>(serviceClass.getTariffMap().values());
        Collections.sort(result, Collections.reverseOrder());
        return result;
    }

    private static Map<Integer, List<SubTariff>> getSubTariffs(Tariff tariff) {
        List<Integer> keys = new ArrayList<>(tariff.getSubTariffsMap().keySet());
        Collections.sort(keys, Collections.reverseOrder());
        Map<Integer, List<SubTariff>> result = new LinkedHashMap<>();
        for (Integer key : keys) {
            List<SubTariff> subTariffs = new ArrayList<>(tariff.getSubTariffsMap().get(key));
            Collections.sort(subTariffs, Collections.reverseOrder());
            result.put(key, subTariffs);
        }
        return result;
    }

    private Airport origin;

    private Airport destination;

    private Airline airline;

    private ServiceClass serviceClass;

    private Tariff tariff;

    private SubTariff subTariff;

    private int chargeCoeff;

    private int distance;

    private int milesCharged;

    private int milesQualifying;

    private int milesBonus;

    private int miles;

	public Airport getOrigin() {
        return origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public Airline getAirline() {
        return airline;
    }

    public ServiceClass getServiceClass() {
        return serviceClass;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public SubTariff getSubTariff() {
        return subTariff;
    }

    public int getDistance() {
        return distance;
    }

    public int getChargeCoeff() {
        return chargeCoeff;
    }

    public int getMilesCharged() {
        return milesCharged;
    }

    public void setMilesCharged(int milesCharged) {
        this.milesCharged = milesCharged;
    }

    public int getMilesQualifying() {
        return milesQualifying;
    }

    public void setMilesQualifying(int milesQualifying) {
        this.milesQualifying = milesQualifying;
    }

    public int getMilesBonus() {
        return milesBonus;
    }

    public void setMilesBonus(int milesBonus) {
        this.milesBonus = milesBonus;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public void setServiceClass(ServiceClass serviceClass) {
        this.serviceClass = serviceClass;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public void setSubTariff(SubTariff subTariff) {
        this.subTariff = subTariff;
    }

    public void setChargeCoeff(int chargeCoeff) {
        this.chargeCoeff = chargeCoeff;
    }

    private void initFields(int factor, boolean isRound) {

        if (factor < 0) return;

        boolean isInternational = ! this.origin.getCity().getCountry().equals(this.destination.getCity().getCountry());
        String milesLimitation = this.airline.getMinMilesLimit();
        this.miles = this.distance;

        if ((milesLimitation.equals("A")) || (milesLimitation.equals("I") && isInternational)) {

            int minBonusMiles = this.airline.getMinMilesCharge();

            if (this.miles < minBonusMiles) {
                this.miles = minBonusMiles;
            }

            this.milesQualifying = (int) Math.round((double) this.miles * this.chargeCoeff / 100);

            if (this.milesQualifying < minBonusMiles) {
                this.milesQualifying = minBonusMiles;
            }
        } else {
            this.milesQualifying = (int) Math.round((double) this.distance * this.chargeCoeff / 100);
        }

        if (this.chargeCoeff < 100) {
            this.milesBonus = (int) Math.round((double) this.milesQualifying * factor / 100);
        } else {
            this.milesBonus = (int) Math.round((double) this.miles * factor / 100);
        }

        if (isRound) {
            this.milesQualifying *= 2;
            this.milesBonus *= 2;
        }

        this.milesCharged = this.milesBonus + this.milesQualifying;
    }

	

}
