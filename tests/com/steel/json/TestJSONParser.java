package com.steel.json;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.junit.Test;

import com.funparser.parser.Parser;
import com.funparser.parser.ParserResult;
import com.steel.json.JSONValue;
import com.steel.json.values.JSONArray;
import com.steel.json.values.JSONBooleanValue;
import com.steel.json.values.JSONNullValue;
import com.steel.json.values.JSONNumberValue;
import com.steel.json.values.JSONObject;
import com.steel.json.values.JSONStringValue;
import com.steel.parser.JSONParser;

public class TestJSONParser {

	@Test
	public void testSpaces() {
		ArrayList<Character> list = new ArrayList<Character>();
		Optional<ParserResult<ArrayList<Character>>> result = Parser.parse(JSONParser.spaces(), "   aaa");
		
		for(int i = 0; i < 3; i++) {
			list.add(' ');
		}
		
		assertEquals(list, result.get().getResult());
		
		result = Parser.parse(JSONParser.spaces(), "aaa");
		list = new ArrayList<Character>();
		assertEquals(list, result.get().getResult());
	}
	
	@Test
	public void testAllExcept() {
		ArrayList<Character> list = new ArrayList<Character>();
		list.add('a');
		Optional<ParserResult<Character>> result = Parser.parse(JSONParser.allExcept(list), "ac");
		assertEquals(Optional.empty(), result);
		
		list = new ArrayList<Character>('b');
		result = Parser.parse(JSONParser.allExcept(list), "ac");
		assertEquals('a', (char) result.get().getResult());
	}
	
	@Test
	public void testJsonString() {
		Optional<ParserResult<String>> result = Parser.parse(JSONParser.jsonString(), "\" Test \"");
		assertEquals("Test", result.get().getResult());
		
		result = Parser.parse(JSONParser.jsonString(), "\" Test ");
		assertEquals(Optional.empty(), result);
	}
	
	@Test
	public void testJsonBooleanValue() {
		Optional<ParserResult<JSONValue<?>>> result = Parser.parse(JSONParser.parseBoolean(), "true");
		assertEquals(new JSONBooleanValue(true), result.get().getResult());
		
		result = Parser.parse(JSONParser.parseBoolean(), "false");
		assertEquals(new JSONBooleanValue(false), result.get().getResult());
		
		result = Parser.parse(JSONParser.parseBoolean(), "null");
		assertEquals(Optional.empty(), result);
	}
	
	@Test
	public void testJsonNumberValue() {
		Optional<ParserResult<JSONValue<?>>> result = Parser.parse(JSONParser.parseNumber(), "0");
		assertEquals(new JSONNumberValue(0), result.get().getResult());
		
		result = Parser.parse(JSONParser.parseNumber(), "1.5");
		assertEquals(new JSONNumberValue(1.5), result.get().getResult());
		
		result = Parser.parse(JSONParser.parseNumber(), "null");
		assertEquals(Optional.empty(), result);
	}
	
	@Test
	public void testJsonStringValue() {
		Optional<ParserResult<JSONValue<?>>> result = Parser.parse(JSONParser.parseString(), "\"foo\"");
		assertEquals(new JSONStringValue("foo"), result.get().getResult());
		
		result = Parser.parse(JSONParser.parseString(), "\"bar\"");
		assertEquals(new JSONStringValue("bar"), result.get().getResult());
		
		result = Parser.parse(JSONParser.parseString(), "null");
		assertEquals(Optional.empty(), result);
	}
	
	@Test
	public void testJsonNullValue() {
		Optional<ParserResult<JSONValue<?>>> result = Parser.parse(JSONParser.parseNull(), "null");
		assertEquals(new JSONNullValue(), result.get().getResult());
		
		result = Parser.parse(JSONParser.parseNull(), "foo");
		assertEquals(Optional.empty(), result);
	}
	
	@Test
	public void testJsonArrayValue() {
		Optional<ParserResult<JSONValue<?>>> result = Parser.parse(JSONParser.parseArray(), "[42, \"Foo\", true]");
		JSONValue<?>[] values = {
			new JSONNumberValue(42),
			new JSONStringValue("Foo"),
			new JSONBooleanValue(true)
		};
		JSONArray expected = new JSONArray(values);
		assertEquals(expected, result.get().getResult());
		
		result = Parser.parse(JSONParser.parseArray(), "[]");
		values = new JSONValue<?>[0];
		expected = new JSONArray(values);
		assertEquals(expected, result.get().getResult());
		
		result = Parser.parse(JSONParser.parseArray(), "a[]");
		assertEquals(Optional.empty(), result);
	}
	
	@Test
	public void testJsonObjectValue() {
		Optional<ParserResult<JSONValue<?>>> result = Parser.parse(JSONParser.parseObject(), "{\"foo\" : 42, \"bar\" : true}");
		HashMap<JSONStringValue, JSONValue<?>> map = new HashMap<JSONStringValue, JSONValue<?>>();
		map.put(new JSONStringValue("foo"), new JSONNumberValue(42));
		map.put(new JSONStringValue("bar"), new JSONBooleanValue(true));
		JSONObject expected = new JSONObject(map);
		assertEquals(expected, result.get().getResult());
	}
	
	@Test
	public void testJsonValue() {
		Optional<ParserResult<JSONValue<?>>> result = Parser.parse(JSONParser.value(), "foo");
		assertEquals(Optional.empty(), result);
		
		result = Parser.parse(JSONParser.value(), "0");
		assertEquals(new JSONNumberValue(0), result.get().getResult());
		
		result = Parser.parse(JSONParser.value(), "\"foo\"");
		assertEquals(new JSONStringValue("foo"), result.get().getResult());
		
		result = Parser.parse(JSONParser.value(), "true");
		assertEquals(new JSONBooleanValue(true), result.get().getResult());
		
		result = Parser.parse(JSONParser.parseArray(), "[42, \"Foo\", true]");
		JSONValue<?>[] values = {
			new JSONNumberValue(42),
			new JSONStringValue("Foo"),
			new JSONBooleanValue(true)
		};
		JSONArray expected = new JSONArray(values);
		assertEquals(expected, result.get().getResult());
	}

}
