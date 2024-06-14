package com.fitdo.model.dto;

public class SearchCondition {

	private String key = "none";
	private String word;
	
	public SearchCondition() {}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
}
