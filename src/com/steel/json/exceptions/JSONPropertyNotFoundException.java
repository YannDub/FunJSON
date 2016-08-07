package com.steel.json.exceptions;

public class JSONPropertyNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public JSONPropertyNotFoundException(String property) {
		System.err.println("The property '" + property + "' is not found");
	}

}
