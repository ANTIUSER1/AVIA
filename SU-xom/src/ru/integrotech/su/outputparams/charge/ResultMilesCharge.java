package ru.integrotech.su.outputparams.charge;

import java.util.ArrayList;
import java.util.List;

/*class is container for output params for charge*/
public class ResultMilesCharge {

    public static ResultMilesCharge of(List<ChargeRoute> routes) {
        return new ResultMilesCharge(routes);
    }

    private List<ChargeRoute> routes;

    private ResultMilesCharge(List<ChargeRoute> routes) {
        this.routes = routes;
    }

    public ResultMilesCharge() {
        this.routes = new ArrayList<>();
    }

    public List<ChargeRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<ChargeRoute> routes) {
        this.routes = routes;
    }
}
