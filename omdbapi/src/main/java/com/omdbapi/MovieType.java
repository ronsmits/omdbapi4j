package com.omdbapi;

public enum MovieType {
	movie("movie"), episode("episode"), series("series");
	
	private String typestr;
	
	private MovieType(String typestr) {
		this.typestr = typestr;
	}
	
	@Override
	public String toString() {
		return typestr;
	}
}
