package ru.integrotech.su.outputparams.charge;


import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.FlightCarrier;
import ru.integrotech.airline.core.flight.PassengerCharge;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.register.RegisterLoader;
import ru.integrotech.su.inputparams.charge.ChargeInput;
import ru.integrotech.su.inputparams.route.RoutesInput;
import ru.integrotech.su.outputparams.route.RoutesBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*this class takes input params (ChargeInput) and returns back output params (ResultMilesCharge)
 *  logic is:
 *
 * var 1: ResultMilesCharge result = buildResult(ChargeInput chargeInput)
 *
 * var 2: 1. List<Route> routes = getRoutes(ChargeInput chargeInput)
 *           int factor = getFactor(ChargeInput chargeInput);
 *           boolean isRound = chargeInput.isRoundTrip()
 *        2. ODM rules with(routes, factor, isRound) - instead of method initFields(int factor, boolean isRound)
  *          in class PassengerCharge
 *        3. ResultMilesCharge result = buildResult(List<Route> routes, ChargeInput chargeInput)
 */
public class ChargeBuilder {

    public static ChargeBuilder of(RegisterCache cache) {
        return new ChargeBuilder(cache);
    }

    private RegisterCache cache;

    private RoutesBuilder routesBuilder;

    private ChargeBuilder(RegisterCache cache) {
        this.cache = cache;
        this.routesBuilder = RoutesBuilder.of(cache);
    }

    /*method for use in ODM*/
    public ResultMilesCharge buildResult(ChargeInput chargeInput) {
        List<ChargeRoute> routes = this.getChargeRoutes(chargeInput);
        return ResultMilesCharge.of(routes);
    }

    /*method for use in ODM*/
    public ResultMilesCharge buildResult(List<Route> routes, ChargeInput chargeInput) {
        List<ChargeRoute> chargeRoutes = this.buildChargeRoutes(routes, chargeInput);
        return ResultMilesCharge.of(chargeRoutes);
    }

    /*method for use in TESTS*/
    public List<ChargeRoute> getChargeRoutes(ChargeInput chargeInput) {
        List<Route> routes = this.getRoutes(chargeInput);
        this.buildPassengerCharges(routes, chargeInput, true);
        return this.buildChargeRoutes(routes, chargeInput);
    }

    public List<Route> getRoutes(ChargeInput chargeInput) {
        RoutesInput routesInput = RoutesInput.of(chargeInput);
        List<Route> routes = this.routesBuilder.getRoutes(routesInput);
        this.buildPassengerCharges(routes, chargeInput, false);
        return routes;
    }

    public int getFactor(ChargeInput chargeInput) {

        String tierLevelCode = null;

        if (chargeInput.getTierLevel() != null) {
            tierLevelCode = chargeInput.getTierLevel().getTierLevelCode();
        }

        return this.cache.getLoyaltyMap().get(tierLevelCode).getFactor();
    }

    private List<ChargeRoute> buildChargeRoutes(List<Route> routes, ChargeInput chargeInput) {

        String airlineCode = this.getAirlineCode(chargeInput);
        Airline airline = this.cache.getAirline(airlineCode);

        List<ChargeRoute> result = new ArrayList<>();

        for (Route route : routes) {
            result.add(ChargeRoute.of(route, airline));
        }

        Collections.sort(result);
        return result;
    }

    private void buildPassengerCharges(List<Route> routes, ChargeInput chargeInput, boolean initFields) {

        for (Route route : routes) {
            this.buildPassengerCharges(route, chargeInput, initFields);
        }
    }

    private void buildPassengerCharges(Route route, ChargeInput chargeInput, boolean initFields) {

        String airlineCode = this.getAirlineCode(chargeInput);
        Airline airline = this.cache.getAirline(airlineCode);

        if (airline != null) {
            for (Flight flight : route.getFlights(airline)) {
                this.buildPassengerCharges(flight, chargeInput, airline, initFields);
            }
        } else {
            for (Flight flight : route.getFlights()) {
                this.buildPassengerCharges(flight, chargeInput, initFields);
            }
        }
    }

    private void buildPassengerCharges(Flight flight, ChargeInput chargeInput, Airline airline, boolean initFields) {

        boolean isRound = chargeInput.isRoundTrip();
        int factor;
        if (initFields) {
            factor = this.getFactor(chargeInput);
        } else {
            factor = -1;
        }

        this.buildPassengerCharges(flight.getCarriers().get(airline), flight, factor, isRound);
    }

    private void buildPassengerCharges(Flight flight, ChargeInput chargeInput, boolean initFields) {

        boolean isRound = chargeInput.isRoundTrip();
        int factor;
        if (initFields) {
            factor = this.getFactor(chargeInput);
        } else {
            factor = -1;
        }

        for (FlightCarrier carrier : flight.getCarriers().values()) {
            this.buildPassengerCharges(carrier, flight, factor, isRound);
        }
    }

    private void buildPassengerCharges(FlightCarrier carrier, Flight flight, int factor, boolean isRound) {
        carrier.setPassengerCharges(PassengerCharge.listOf(flight, factor, isRound, carrier.getCarrier()));
    }

    private String getAirlineCode(ChargeInput chargeInput) {
        String result = null;
        if (chargeInput.getAirline() != null) {
            result = chargeInput.getAirline().getAirlineCode();
        }
        return result;
    }

}
