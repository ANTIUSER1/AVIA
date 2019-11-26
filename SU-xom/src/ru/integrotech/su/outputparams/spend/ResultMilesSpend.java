package ru.integrotech.su.outputparams.spend;

import java.util.List;

public class ResultMilesSpend {

    public static ResultMilesSpend of(List<SpendRoute> routes) {
        return new ResultMilesSpend(routes);
    }

    private List<SpendRoute> routes;

    private ResultMilesSpend(List<SpendRoute> routes) {
        this.routes = routes;
    }

    private ResultMilesSpend() {
    }

    public List<SpendRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<SpendRoute> routes) {
        this.routes = routes;
    }
}
