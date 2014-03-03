package org.ronsmits.omdbapi;

public class OmdbConnectionErrorException extends Exception {
	private static final long serialVersionUID = -611277057268763964L;

	public OmdbConnectionErrorException(String message) {
		super(message);
	}

}
