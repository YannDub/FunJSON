package com.steel.json;

import static org.junit.Assert.*;

import org.junit.Test;

import com.steel.json.exceptions.JSONPropertyNotFoundException;

public class TestJSON {

	@Test
	public void testGetPropertyWhenFindable() throws JSONPropertyNotFoundException {
		JSON json = new JSON("{\"foo\":42}");
		assertEquals(42.0, json.getProperty("foo").get());
	}
	
	@Test(expected=JSONPropertyNotFoundException.class)
	public void testGetPropertyWhenNotFound() throws JSONPropertyNotFoundException {
		JSON json = new JSON("{}");
		json.getProperty("foo");
	}

}
