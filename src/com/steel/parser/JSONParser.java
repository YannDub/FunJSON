package com.steel.parser;

import java.util.ArrayList;
import java.util.HashMap;

import com.funparser.parser.Parser;
import com.steel.json.JSONValue;
import com.steel.json.values.JSONArray;
import com.steel.json.values.JSONBooleanValue;
import com.steel.json.values.JSONNullValue;
import com.steel.json.values.JSONNumberValue;
import com.steel.json.values.JSONObject;
import com.steel.json.values.JSONStringValue;

public class JSONParser {
	
	/**
	 * Parse zero or plus spaces
	 * @return a parser of list of spaces
	 */
	public static Parser<ArrayList<Character>> spaces() {
		return Parser.zeroOrPlus(Parser.character(' '));
	}
	
	/**
	 * Parse all character except the character in the list
	 * @param list the list of characters to don't parse
	 * @return a parser of a character not contain on the list
	 */
	public static Parser<Character> allExcept(ArrayList<Character> list) {
		return Parser.charCond(c -> !list.contains(c));
	}
	
	/**
	 * Parse a json string format
	 * @return a parser of a json string format without the '"'
	 */
	public static Parser<String> jsonString() {
		ArrayList<Character> list = new ArrayList<Character>();
		list.add(JSONToken.JSON_QUOTE);
		list.add(' ');
		return Parser.character(JSONToken.JSON_QUOTE)
				.bind(e1 -> JSONParser.spaces()
				.bind(x -> Parser.oneOrPlus(JSONParser.allExcept(list))
				.bind(cs -> JSONParser.spaces()
				.bind(e2 -> Parser.character(JSONToken.JSON_QUOTE)
				.bind(r -> {
					String s = "";
					for(Character c : cs) {
						s = c + s;
					}
					return Parser.succesful(s);})))));
	}
	
	/**
	 * Parse a json boolean value
	 * @return a parser of a json boolean value
	 */
	public static Parser<JSONValue<?>> parseBoolean() {
		return Parser.bool()
				.bind(b -> Parser.succesful(new JSONBooleanValue(b)));
	}
	
	/**
	 * Parse a json number value
	 * @return a parser of a json boolean value
	 */
	public static Parser<JSONValue<?>> parseNumber() {
		return Parser.number()
				.bind(f -> Parser.succesful(new JSONNumberValue(f)));
	}
	
	/**
	 * Parse a json string value
	 * @return a parser of a json string value
	 */
	public static Parser<JSONValue<?>> parseString() {
		return jsonString().bind(s -> Parser.succesful(new JSONStringValue(s)));
	}
	
	/**
	 * Parse a json null value
	 * @return a parser of a json null value
	 */
	public static Parser<JSONValue<?>> parseNull() {
		return Parser.string("null").bind(s -> Parser.succesful(new JSONNullValue()));
	}
	
	/**
	 * Parse a parser and get a list of the result of this parser zero or plus times separated by a comma
	 * @param p the parser to parse zero or plus
	 * @return a list of the result of the parser p
	 */
	public static <T> Parser<ArrayList<T>> zeroOrPlusComma(Parser<T> p) {
		return oneOrPlusComma(p).alternate(Parser.succesful(new ArrayList<T>()));
	}
	
	/**
	 * Parse a parser and get a list of the result of this parser one or plus times separated by a comma
	 * @param p the parser to parse zero or plus
	 * @return a list of the result of the parser p
	 */
	public static <T> Parser<ArrayList<T>> oneOrPlusComma(Parser<T> p) {
		return p.bind(r -> zeroOrPlusComma(p)
				.bind(rs -> spaces()
				.bind(tmp -> Parser.character(JSONToken.JSON_SEPARATOR)
				.bind(s -> spaces()
				.bind(tmp2 -> oneOrPlusComma(p)
				.bind(rs1 -> {
					rs1.addAll(rs);
					rs1.add(r);
					return Parser.succesful(rs1);
				}))).alternate(Parser.succesful(rs).bind(r1 -> {r1.add(r); return Parser.succesful(r1);})))));
	}
	
	/**
	 * Parse an array json value
	 * @return a parser of an array json value
	 */
	public static Parser<JSONValue<?>> parseArray() {
		return Parser.character(JSONToken.JSON_BRACKET_OPEN).bind(s -> zeroOrPlusComma(value())
				.bind(rs -> Parser.character(JSONToken.JSON_BRACKET_CLOSE)
				.bind(s1 -> Parser.succesful(new JSONArray(rs)))));
	}
	
	/**
	 * Parse a json pair
	 * @return a parser of an association between json string value and json value
	 */
	private static Parser<HashMap<JSONStringValue, JSONValue<?>>> parsePair() {
		HashMap<JSONStringValue, JSONValue<?>> map = new HashMap<JSONStringValue, JSONValue<?>>();
		return parseString()
				.bind(s -> spaces()
				.bind(spc -> Parser.character(JSONToken.JSON_ELEMENTS_SEPARATOR)
				.bind(pts -> spaces()
				.bind(spc1 -> value()
				.bind(r -> {
					map.put((JSONStringValue) s, r);
					return Parser.succesful(map);
				})))));
	}
	
	/**
	 * Parse a json object
	 * @return a parser of a json object
	 */
	public static Parser<JSONValue<?>> parseObject() {
		HashMap<JSONStringValue, JSONValue<?>> map = new HashMap<JSONStringValue, JSONValue<?>>();
		return Parser.character(JSONToken.JSON_CURLY_OPEN)
				.bind(s -> spaces()
				.bind(spc -> zeroOrPlusComma(parsePair())
				.bind(rs -> spaces()
				.bind(spc1 -> Parser.character(JSONToken.JSON_CURLY_CLOSE)
				.bind(f -> {
					for(HashMap<JSONStringValue, JSONValue<?>> each : rs) {
						map.putAll(each);
					}
					return Parser.succesful(new JSONObject(map));
				})))));
	}
	
	/**
	 * Parse a json value
	 * @param <T>
	 * @return a parser of a json value
	 */
	public static Parser<JSONValue<?>> value() {
		Parser<JSONValue<?>> string = JSONParser.parseString();
		Parser<JSONValue<?>> bool = JSONParser.parseBoolean();
		Parser<JSONValue<?>> nullObj = JSONParser.parseNull();
		Parser<JSONValue<?>> number = JSONParser.parseNumber();
		Parser<JSONValue<?>> array = JSONParser.parseArray();
		Parser<JSONValue<?>> object = JSONParser.parseObject();
		
		return string.alternate(number).alternate(bool).alternate(nullObj).alternate(array).alternate(object);
	}
	
	/**
	 * Parse a json string
	 * @param json the string to parse
	 * @return the json value
	 */
	public static JSONValue<?> json(String json) {
		return Parser.parse(value(), json).get().getResult();
	}
}
