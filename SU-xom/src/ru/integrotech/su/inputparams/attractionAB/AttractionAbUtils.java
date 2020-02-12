package ru.integrotech.su.inputparams.attractionAB;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.PassengerChargeInfo;
import ru.integrotech.airline.core.flight.PassengerChargeInfo.Status;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.utils.StringMethods;

import java.util.ArrayList;
import java.util.List;

public class AttractionAbUtils {

	private static final String DEFAULT_TIER_LEVEL = "basic";
	private static final String AFL_IATA_CODE = "SU";

	public static AttractionAbUtils of(RegisterCache registers) {
		return new AttractionAbUtils(registers);
	}
	
	private final RegisterCache registers;
		
	private AttractionAbUtils(RegisterCache registers) {
		this.registers = registers;
	}
	
	public double getTierLevelFactor(AttractionAbInput input) {
		String passengerTierLevel = input.getData().getTierCode();
		if (StringMethods.isEmpty(passengerTierLevel)) {
			passengerTierLevel = DEFAULT_TIER_LEVEL;
		}
		return registers.getLoyaltyMap().get(passengerTierLevel).getFactor()/100.00;
	}

	public List<PassengerChargeInfo> toPassengerChargeInfo(AttractionAbInput input) {
		
		List<PassengerChargeInfo> result = new ArrayList<>();
		Airline airline = this.registers.getAirline(AFL_IATA_CODE);
		
		for (Segment segment : input.getData().getSegments()) {
			
			Airport origin = this.registers.getAirport(segment.getOriginIATA());
			Airport destination = this.registers.getAirport(segment.getDestinationIATA());
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

			result.add(PassengerChargeInfo.of(airline,
											  origin, 
											  destination,
											  fareCode,
											  bookingClassCode,
											  distance,
											  ticketDesignator,
											  status));
		}
		
		return result;
	}


}
