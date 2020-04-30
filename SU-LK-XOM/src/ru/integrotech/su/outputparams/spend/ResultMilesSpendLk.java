package ru.integrotech.su.outputparams.spend;

import java.util.List;

/**
 * class for build spendLKResponse from spendLKRequest
 *
 * data(private SpendBuilder spendBuilder;)
 *
 */
public class ResultMilesSpendLk {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param spendBuilder
	 * @return
	 */
	public static ResultMilesSpendLk of(List<SpendLkRoute> routes) {
		ResultMilesSpendLk res = new ResultMilesSpendLk();
		res.setAwardRoutes(routes);
		return res;
	}

	private List<SpendLkRoute> awardRoutes;

	public List<SpendLkRoute> getAwardRoutes() {
		return awardRoutes;
	}

	public void setAwardRoutes(List<SpendLkRoute> routes) {
		this.awardRoutes = routes;
	}
}
