package ru.integrotech.su.outputparams.spend;

import java.util.List;

public class ResultMilesSpend {

    public static ResultMilesSpend of(List<SpendRoute> routes) {
        return new ResultMilesSpend(routes);
    }

    private List<SpendRoute> awardRoutes;

    private ResultMilesSpend(List<SpendRoute> routes) {
        this.awardRoutes = routes;
    }

    private ResultMilesSpend() {
    }

    public List<SpendRoute> getAwardRoutes() {
        return awardRoutes;
    }

    public void setAwardRoutes(List<SpendRoute> routes) {
        this.awardRoutes = routes;
    }
}
