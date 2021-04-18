package com.idstaa.rocket.demo.result;

import java.util.ArrayList;
import java.util.List;

public class AppResultList<T> extends BaseAppResult {
	private List<T> results = new ArrayList<T>();

	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
}
