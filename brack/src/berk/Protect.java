package berk;

import java.util.Date;

public class Protect implements Comparable {

	private String name = "";
	private String fam = "";
	private Date date;

	public Protect(String name, String fam, Date date) {
		super();
		this.name = name;
		this.fam = fam;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFam() {
		return fam;
	}

	public void setFam(String fam) {
		this.fam = fam;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Protect [name=" + name + ", fam=" + fam + ", date=" + date
				+ "]";
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if (o instanceof Protect) {
			return this.date.compareTo(((Protect) o).date);
		}

		return -Integer.MAX_VALUE;
	}
}
