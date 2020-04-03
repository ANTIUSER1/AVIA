package ru.integrotech.su.inputparams.attractionAB;

import java.util.ArrayList;
import java.util.List;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.info.PassengerMilesInfo;
import ru.integrotech.airline.core.info.PassengerMilesInfo.Status;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.utils.StringMethods;

/**
 * util class for different methods for attractionABInput data used for
 * transform attractionABInput data to ru.integrotech.airline.core data not
 * opening attractionABInput data to visible namespace area
 *
 * data (private final RegisterCache registers;)
 */

public class AttractionAbUtils {

	private static final String DEFAULT_TIER_LEVEL = "basic";

    /**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param registers
	 * @return
	 */

	public static AttractionAbUtils of(RegisterCache registers) {
		AttractionAbUtils res = new AttractionAbUtils();
		res.setRegisters(registers);
		return res;
	}

	private RegisterCache registers;

	private void setRegisters(RegisterCache registers) {
		this.registers = registers;
	}

	public double getTierLevelFactor(AttractionAbInput input) {
		String passengerTierLevel = input.getData().getTierCode();
		if (StringMethods.isEmpty(passengerTierLevel)) {
			passengerTierLevel = DEFAULT_TIER_LEVEL;
		}
		return registers.getLoyaltyMap().get(passengerTierLevel).getFactor() / 100.00;
	}

	public List<PassengerMilesInfo> toPassengerChargeInfo(
			AttractionAbInput input) {

		List<PassengerMilesInfo> result = new ArrayList<>();

		for (Segment segment : input.getData().getSegments()) {

			Airline airline = this.registers.getAirline(segment.getAirlineIATA());
			Airport origin = this.createAirport(segment.getOriginIATA());
			Airport destination = this.createAirport(segment.getDestinationIATA());
			Flight flight = null;
			if (origin != null) {
				flight = origin.getOutcomeFlight(destination, airline);
			}
			int distance = 0;
			Status status = null;
			String ticketDesignator = segment.getTicketDesignator();
			String fareCode = segment.getFareBasisCode();
			String bookingClassCode = segment.getBookingClassCode();

			if (flight != null) {
				distance = flight.getDistance();
			} else {
				status = Status.nodata;
			}

			result.add(PassengerMilesInfo.of(airline, origin, destination,
					fareCode, bookingClassCode, distance, ticketDesignator,
					status));
		}

		return result;
	}

	private Airport createAirport(String IATACode) {
	    Airport result = null;
	    if (this.registers.getAirport(IATACode) != null) {
	        result = this.registers.getAirport(IATACode);
        } else {
	        result = new Airport();
	        result.setCode(IATACode);
        }
	    return result;
    }

}
