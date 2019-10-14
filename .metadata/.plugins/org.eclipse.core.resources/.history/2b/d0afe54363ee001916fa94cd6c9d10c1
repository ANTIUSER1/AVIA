package berk;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class CNVgson {
	private CNVgson() {
	}

	public static CNVgson create() {
		return new CNVgson();

	}

	public String conv(Protect pp) {
		Gson GBilder = new Gson();
		JsonElement elem = GBilder.toJsonTree(pp);
		return elem.getAsString();

	}

	public String conv(ScoreType sc) {
		Gson GBilder = new Gson();
		JsonElement elem = GBilder.toJsonTree(sc);
		return GBilder.toJson(sc);

	}
}
