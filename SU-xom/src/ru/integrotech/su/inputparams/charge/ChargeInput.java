package ru.integrotech.su.inputparams.charge;


import ru.integrotech.su.common.Airline;
import ru.integrotech.su.common.Location;

import static ru.integrotech.su.common.LocationType.*;

/*class is container for input params for charge*/
public class ChargeInput {

    public static ChargeInput of(String originType,
                                 String originCode,
                                 String destType,
                                 String destCode,
                                 String airlineCode,
                                 String tierLevelCode,
                                 boolean isRoundTrip) {

        ChargeInput chargeInput = new ChargeInput();
        chargeInput.setAirline(Airline.of(airlineCode));
        chargeInput.setOrigin(createLocation(originType, originCode));
        chargeInput.setDestination(createLocation(destType, destCode));
        chargeInput.setTierLevel(TierLevel.of(tierLevelCode));
        chargeInput.setRoundTrip(isRoundTrip);

        return chargeInput;
    }

    private static Location createLocation(String destType, String destCode) {

        Location result = null;

        if (destType == null) {
            return result;
        } else {
            destType = destType.toLowerCase();
        }

        if (valueOf(destType) == airport) {
            result = Location.ofAirport(destCode);
        }else if (valueOf(destType) == city) {
            result = Location.ofCity(destCode);
        }else if (valueOf(destType) == country) {
            result = Location.ofCountry(destCode);
        }else if (valueOf(destType) == region) {
            result = Location.ofRegion(destCode);
        }

        return result;
    }

    private Airline airline;

    private Location origin;

    private Location destination;

    private TierLevel tierLevel;

    private boolean isRoundTrip;

    private ChargeInput() {
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public TierLevel getTierLevel() {
        return tierLevel;
    }

    public void setTierLevel(TierLevel tierLevel) {
        this.tierLevel = tierLevel;
    }

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        isRoundTrip = roundTrip;
    }

}
