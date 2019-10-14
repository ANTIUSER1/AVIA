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
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
