package com.omdbapi;

public class OmdbSyntaxErrorException extends Exception {
	private static final long serialVersionUID = 4600503908695642106L;

	public OmdbSyntaxErrorException(String message) {
		super(message);
	}


}
