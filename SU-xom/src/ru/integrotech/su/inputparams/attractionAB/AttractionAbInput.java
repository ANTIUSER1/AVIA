package ru.integrotech.su.inputparams.attractionAB;

/**
 *
 * container for attractionABRequest (request body for attractionAB project)
 *
 * data ( private Data data; private String lang; )
 */

public class AttractionAbInput {

	private Data data;

	private String lang;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}
