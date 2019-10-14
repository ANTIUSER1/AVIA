package brack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import berk.Containers;
import berk.Protect;
import berk.ScoreType;
import berk.Vect;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ScoreType scoreType = ScoreType.create();
		System.out.println(scoreType);
		System.out.println(new Date());

		Set<Vect> vss = new TreeSet<>();
		for (int k = 0; k < 4; k++) {
			double x = Math.random();
			double y = Math.random();
			double z = Math.random();
			Vect v = Vect.make(z, y, z);
			System.err.println(v);
			vss.add(v);
		}
		Containers cont = Containers.make(vss);
		System.out.println(cont);
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
