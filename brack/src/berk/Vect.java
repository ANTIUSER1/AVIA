package berk;

public class Vect implements Comparable {

	private double x;
	private double y;
	private double z;

	private Vect(double X, double Y, double Z) {
		x = X;
		y = Y;
		z = Z;
	}

	public static Vect make(double X, double Y, double Z) {
		return new Vect(X, Y, Z);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vect [x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", z=");
		builder.append(z);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if (o instanceof Vect) {
			Vect v = (Vect) o;
			if (x < v.x) {
				return -1;
			} else if (x > v.x) {
				return 1;
			} else
				return 0;
		} else
			return Integer.MAX_VALUE;
	}

}
