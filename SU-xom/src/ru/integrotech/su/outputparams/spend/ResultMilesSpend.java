package ru.integrotech.su.outputparams.spend;

import java.util.List;

/**
 * container for ResultMilesSpend
 *
 * data( private List<SpendRoute> routes; )
 */
public class ResultMilesSpend {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param routes
	 * @return
	 */
	public static ResultMilesSpend of(List<SpendRoute> routes) {
		ResultMilesSpend res = new ResultMilesSpend();
		res.setAwardRoutes(routes);
		return res;
	}

	private List<SpendRoute> awardRoutes;

	public List<SpendRoute> getAwardRoutes() {
		return awardRoutes;
	}

	public void setAwardRoutes(List<SpendRoute> routes) {
		this.awardRoutes = routes;
	}
}
