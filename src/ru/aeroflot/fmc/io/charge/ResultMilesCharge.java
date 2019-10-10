package ru.aeroflot.fmc.io.charge;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResultMilesCharge [routes=");
		builder.append(routes);
		builder.append("]");
		return builder.toString();
	}

}
