package brack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import berk.Protect;
import berk.ScoreType;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ScoreType scoreType = ScoreType.create();
		System.out.println(scoreType);
		System.out.println(new Date());
	}

	private static List<Protect> makeProtects() {
		List<Protect> res = new ArrayList<>();
		for (int k = 0; k < 3 + 3 * Math.random(); k++) {
			long ttm = (long) (Long.MAX_VALUE * Math.random());
			Date d = new Date(ttm);
			String sn = "" + (10 * Math.random());
			String fn = "" + (100 * Math.random());
			Protect pr = new Protect(sn, fn, d);
			res.add(pr);
		}
		return res;
	}

}
