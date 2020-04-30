package ru.integrotech.su.outputparams.charge;

import java.util.ArrayList;
import java.util.List;

/**
 * class is container for output params for charge
 * 
 * <hr /< container for ResultMilesCharge
 *
 * data( private List<ChargeRoute> routes; )
 *
 * 
 */

public class ResultMilesCharge {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * Simply generates the instance
	 *
	 * @param routes
	 * @return
	 */
	public static ResultMilesCharge of(List<ChargeRoute> routes) {
		ResultMilesCharge res = new ResultMilesCharge();
		res.setRoutes(routes);
		return res;
	}

	private List<ChargeRoute> routes = new ArrayList<>();

	public List<ChargeRoute> getRoutes() {
		return routes;
	}

	public void setRoutes(List<ChargeRoute> routes) {
		this.routes = routes;
	}
}
