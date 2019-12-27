package ru.integrotech.su.outputparams.spend;

import java.util.List;

public class ResultMilesSpendLk {

    public static ResultMilesSpendLk of(List<SpendLkRoute> routes) {
        return new ResultMilesSpendLk(routes);
    }

    private List<SpendLkRoute> awardRoutes;

    private ResultMilesSpendLk(List<SpendLkRoute> routes) {
        this.awardRoutes = routes;
    }

    private ResultMilesSpendLk() {
    }

    public List<SpendLkRoute> getAwardRoutes() {
        return awardRoutes;
    }

    public void setAwardRoutes(List<SpendLkRoute> routes) {
        this.awardRoutes = routes;
    }
}
