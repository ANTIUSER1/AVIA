package ru.integrotech.airline.core.flight;



import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.airline.SubTariff;
import ru.integrotech.airline.core.airline.Tariff;
import ru.integrotech.airline.core.location.Airport;

import java.util.*;

public class PassengerCharge {

    public static List<PassengerCharge> listOf(Flight flight, List<ServiceClass> allowedClasses, int factor, boolean isRound, Airline airline) {
        List<PassengerCharge> result = new ArrayList<>();
        boolean isAfl = airline.getCode().equals("SU");

        for (ServiceClass serviceClass : allowedClasses) {
            for (Tariff tariff : PassengerCharge.getTariffs(serviceClass)) {
                for (Map.Entry<Integer, List<SubTariff>> subTarifEntry : PassengerCharge.getSubTariffs(tariff).entrySet()) {
                    Integer chargeCoff = subTarifEntry.getKey();
                    for (SubTariff subTariff : subTarifEntry.getValue()) {
                        PassengerCharge passengerCharge = new PassengerCharge(flight.getOrigin(),
                                flight.getDestination(),
                                airline,
                                serviceClass,
                                tariff,
                                SubTariff.of(subTariff, isAfl),
                                flight.getDistance(),
                                chargeCoff);
                        passengerCharge.initFields(factor, isRound);
                        result.add(passengerCharge);
                    }
                }
            }
        }
        return result;
    }

    public static PassengerCharge of(Airport origin, Airport destination,
			Airline airline, ServiceClass serviceClass, Tariff tariff,
			SubTariff subTariff, int distance, int chargeCoeff) {
 		return new PassengerCharge(origin, destination, airline, serviceClass, tariff, subTariff, distance, chargeCoeff);
		
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

    private final Airport origin;

    private final Airport destination;

    private final Airline airline;

    private final ServiceClass serviceClass;

    private final Tariff tariff;

    private final SubTariff subTariff;

    private final int chargeCoeff;

    private int distance;

    private int milesCharged;

    private int milesQualifying;

    private int milesBonus;

    private int miles;

    private PassengerCharge(Airport origin, Airport destination, Airline airline, ServiceClass serviceClass, Tariff tariff, SubTariff subTariff, int distance, int chargeCoeff) {
        this.origin = origin;
        this.destination = destination;
        this.airline = airline;
        this.serviceClass = serviceClass;
        this.tariff = tariff;
        this.subTariff = subTariff;
        this.distance = distance;
        this.chargeCoeff = chargeCoeff;
    }

   
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
