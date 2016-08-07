package com.steel.json.writers;

import java.util.Map.Entry;

import com.steel.json.JSON;
import com.steel.json.JSONValue;
import com.steel.json.values.JSONArray;
import com.steel.json.values.JSONBooleanValue;
import com.steel.json.values.JSONNullValue;
import com.steel.json.values.JSONNumberValue;
import com.steel.json.values.JSONObject;
import com.steel.json.values.JSONStringValue;
import com.steel.parser.JSONToken;

public class JSONWriter extends Writer {
	
	private String result;
	
	public JSONWriter(JSON json) {
		super(json);
		this.result = "";
	}

	@Override
	public String write() {
		this.visitObject(json.getJsonObject());
		return this.result;
	}

	@Override
	public void visitArray(JSONArray arrayValue) {
		result += JSONToken.JSON_BRACKET_OPEN;
		for(int i = 0; i < arrayValue.get().length - 1; i++) {
			arrayValue.get()[i].accept(this);
			result += JSONToken.JSON_SEPARATOR;
		}
		arrayValue.get()[arrayValue.get().length - 1].accept(this);
		result += JSONToken.JSON_BRACKET_CLOSE;
	}

	@Override
	public void visitBoolean(JSONBooleanValue booleanValue) {
		result += booleanValue.get();
	}

	@Override
	public void visitNull(JSONNullValue nullValue) {
		result += "null";
	}

	@Override
	public void visitNumber(JSONNumberValue numberValue) {
		result += numberValue.get();
	}

	@Override
	public void visitObject(JSONObject objectValue) {
		result += JSONToken.JSON_CURLY_OPEN;
		int size = objectValue.get().entrySet().size();
		int i = 0;
		for(Entry<JSONStringValue, JSONValue<?>> c : objectValue.get().entrySet()) {
			i++;
			c.getKey().accept(this);
			result += JSONToken.JSON_ELEMENTS_SEPARATOR;
			c.getValue().accept(this);
			if(i != size) result += JSONToken.JSON_SEPARATOR;
		}
		result += JSONToken.JSON_CURLY_CLOSE;
	}

	@Override
	public void visitString(JSONStringValue stringValue) {
		result += JSONToken.JSON_QUOTE + stringValue.get() + JSONToken.JSON_QUOTE;
	}
	
	
}
