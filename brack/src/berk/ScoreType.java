package berk;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class ScoreType {

	private String s = "";
	private Set<Protect> protectSet = new TreeSet<>();

	private ScoreType() {
	}

	public static ScoreType create() {
		StringBuffer sbf = new StringBuffer();
		ScoreType res = new ScoreType();
		for (int k = 0; k < 3 + 3 * Math.random(); k++) {

			long ttm = (long) (Long.MAX_VALUE * Math.random() / 10000);
			Date d = new Date(ttm);
			String sn = "" + ((long) (10 * Math.random()));
			String fn = "" + ((long) (1000 * Math.random()));
			Protect pr = new Protect(sn, fn, d);
			res.protectSet.add(pr);
			sbf.append((long) (1000 * Math.random()) + "=");
		}
		res.s = sbf.toString();
		return res;
	}

	@Override
	public String toString() {
		return "ScoreType [s=" + s + "," + System.lineSeparator()
				+ "      protectSet=" + protectSet + "]";
	}

}
