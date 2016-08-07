package com.steel.json.writers;

import com.steel.json.JSON;
import com.steel.json.values.JSONArray;
import com.steel.json.values.JSONBooleanValue;
import com.steel.json.values.JSONNullValue;
import com.steel.json.values.JSONNumberValue;
import com.steel.json.values.JSONObject;
import com.steel.json.values.JSONStringValue;

public abstract class Writer {
	
	protected JSON json;
	
	public Writer(JSON json) {
		this.json = json;
	}
	
	public abstract String write();
	
	public abstract void visitArray(JSONArray arrayValue);
	
	public abstract void visitBoolean(JSONBooleanValue booleanValue);
	
	public abstract void visitNull(JSONNullValue nullValue);
	
	public abstract void visitNumber(JSONNumberValue numberValue);
	
	public abstract void visitObject(JSONObject objectValue);
	
	public abstract void visitString(JSONStringValue stringValue);
}
