package ru.aeroflot.fmc.io.spend;

import java.util.List;

public class ResultMilesSpendLk {

    public static ResultMilesSpendLk of(List<SpendLkRoute> routes) {
        return new ResultMilesSpendLk(routes);
    }

    private List<SpendLkRoute> routes;

    private ResultMilesSpendLk(List<SpendLkRoute> routes) {
        this.routes = routes;
    }

    private ResultMilesSpendLk() {
    }

    public List<SpendLkRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<SpendLkRoute> routes) {
        this.routes = routes;
    }
}
