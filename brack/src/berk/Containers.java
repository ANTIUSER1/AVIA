package berk;

import java.util.Set;
import java.util.TreeSet;

public class Containers {

	private Set<Vect> vectSet = new TreeSet<>();

	private Containers(Set<Vect> vs) {
		vectSet = vs;
	}

	public static Containers make(Set<Vect> vs) {
		return new Containers(vs);
	}

	public Set<Vect> getVectSet() {
		return vectSet;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Containers [vectSet=");
		builder.append(vectSet);
		builder.append("]");
		return builder.toString();
	}

}
