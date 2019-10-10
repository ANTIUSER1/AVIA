package ru.aeroflot.fmc.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.aeroflot.fmc.io.charge.ChargeBuilder;
import ru.aeroflot.fmc.io.charge.ChargeRoute;
import ru.aeroflot.fmc.model.airline.Airline;
import ru.aeroflot.fmc.model.flight.Flight;
import ru.aeroflot.fmc.model.flight.FlightCarrier;
import ru.aeroflot.fmc.model.flight.Route;

public class Test {
	public static void main(String[] args) {

		String from = "ABA";
		String fromType = "airport";
		String to = "DEL";
		String toType = "airport";
		String airlineCode = "SU";

		ChargeBuilder chargeBuilder = ChargeBuilder.of();
		List<Route> rlt = routesListMaker(from, fromType, to, toType,
				airlineCode, chargeBuilder);

		List<ChargeRoute> chargeRouteList = new ArrayList<>(); //
		List<Flight> flightList = new ArrayList<>();

		chargeRouteList = chargeBuilder.getChargeRoutes(from, fromType, to,
				toType, airlineCode, "gold", false);
		for (ChargeRoute crt : chargeRouteList) {
			System.out.println(crt);
		}
		/*
		 * System.out.println(chargeRouteList); ResultMilesCharge rmc =
		 * ResultMilesCharge.of(chargeRouteList);
		 */

		// System.err.println(rmc);

		/**
		 * Map<Flight, Map<Airline, FlightCarrier>> mpFLTs =
		 * mapOfFligftGenerator(rlt); for (Map.Entry<Flight, Map<Airline,
		 * FlightCarrier>> entry : mpFLTs .entrySet()) { //
		 * System.out.println("Key: " + entry.getKey() + " Value: " + //
		 * entry.getValue()); Map<Airline, FlightCarrier> carrAir =
		 * entry.getValue(); for (Map.Entry<Airline, FlightCarrier> mapOfEntry :
		 * carrAir .entrySet()) { // System.out.println("Key: " + entry.getKey()
		 * + " Value: " + // entry.getValue());
		 * System.err.println(mapOfEntry.getValue().getPassengerCharges()); }
		 * 
		 * }
		 */

		/**
		 * for (Route rt : rlt) { Set<Airline> airlineSET = rt.getAirlines();
		 * System.err.println(airlineSET); for (Airline arl : airlineSET) {
		 * ChargeRoute chargeRoute = ChargeRoute.of(rt, arl);
		 * System.out.println("   " + arl == null);
		 * chargeRouteList.add(chargeRoute); } } ResultMilesCharge rmch =
		 * ResultMilesCharge.of(chargeRouteList); System.out.println(rmch);
		 */

	}

	private static Map<Flight, Map<Airline, FlightCarrier>> mapOfFligftGenerator(
			List<Route> rlt) {
		Map<Flight, Map<Airline, FlightCarrier>> mapOfFlights = new HashMap<>();

		int K = 0, N = 0;
		for (Route rt : rlt) {
			List<Flight> flightList = rt.getFlights();
			K++;
			for (Flight flt : flightList) {
				mapOfFlights.put(flt, flt.getCarriers());
				N++;
			}
			// System.out.println("    . ");
			return mapOfFlights;
		}
		// System.out.println(mapOfFlights);
		return mapOfFlights;
	}

	private static List<Route> routesListMaker(String from, String fromType,
			String to, String toType, String airlineCode,
			ChargeBuilder chargeBuilder) {
		List<Route> roteList = roteList = chargeBuilder.getRoutesBuilder()
				.getRoutes(from, fromType, to, toType, airlineCode);

		// System.out.println("*** ****   ROTES    ***");

		int k = 0;
		for (Route rt : roteList) {
			// System.err.println(k + "   : " + rt.toString());
			k++;
		}
		// Route: ALA LED SVO 2 638 mi afl: MAR1R1A, R1R1MAA skyteam: MER1R1S,
		// R1R1MES
		// System.out.println("SIZE: " + roteList.size());
		return roteList;
	}

}
