package com.steel.json.writer;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.steel.json.JSON;
import com.steel.json.JSONValue;
import com.steel.json.values.JSONArray;
import com.steel.json.values.JSONBooleanValue;
import com.steel.json.values.JSONNullValue;
import com.steel.json.values.JSONNumberValue;
import com.steel.json.values.JSONObject;
import com.steel.json.values.JSONStringValue;
import com.steel.json.writers.JSONWriter;

public class TestJSONWriter {
	
	private HashMap<JSONStringValue, JSONValue<?>> properties;
	private JSONObject object;
	private JSON json;
	
	@Before
	public void setUp() {
		this.properties = new HashMap<JSONStringValue, JSONValue<?>>();
		this.object = new JSONObject(this.properties);
		this.json = new JSON(this.object);
	}
	
	private void testOutput(String expected) {
		assertEquals(expected, new JSONWriter(json).write());
	}
	
	@Test
	public void testVisitArray() {
		JSONStringValue[] strings = new JSONStringValue[3];
		strings[0] = new JSONStringValue("foo");
		strings[1] = new JSONStringValue("bar");
		strings[2] = new JSONStringValue("timoleon");
		JSONArray array = new JSONArray(strings);
		this.properties.put(new JSONStringValue("strings"), array);
		this.testOutput("{\"strings\":[\"foo\",\"bar\",\"timoleon\"]}");
	}

	@Test
	public void testVisitBoolean() {
		JSONBooleanValue bool = new JSONBooleanValue(true);
		this.properties.put(new JSONStringValue("bool"), bool);
		this.testOutput("{\"bool\":true}");
	}

	@Test
	public void testVisitNull() {
		JSONNullValue nil = new JSONNullValue();
		this.properties.put(new JSONStringValue("nil"), nil);
		this.testOutput("{\"nil\":null}");
	}

	@Test
	public void testVisitNumber() {
		JSONNumberValue number = new JSONNumberValue(42);
		this.properties.put(new JSONStringValue("number"), number);
		this.testOutput("{\"number\":42.0}");
	}

	@Test
	public void testVisitObject() {
		JSONStringValue string = new JSONStringValue("bar");
		HashMap<JSONStringValue, JSONValue<?>> subProperties = new HashMap<>();
		subProperties.put(new JSONStringValue("foo"), string);
		JSONObject subObject = new JSONObject(subProperties);
		this.properties.put(new JSONStringValue("subObject"), subObject);
		this.testOutput("{\"subObject\":{\"foo\":\"bar\"}}");
	}

	@Test
	public void testVisitString() {
		JSONStringValue string = new JSONStringValue("bar");
		this.properties.put(new JSONStringValue("foo"), string);
		this.testOutput("{\"foo\":\"bar\"}");
	}

}
