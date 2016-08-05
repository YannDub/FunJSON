package com.steel.json.values;

import com.steel.json.JSONValue;

public class JSONNullValue extends JSONValue<Object>{
	
	public JSONNullValue() {
		this.t = null;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JSONNullValue) {
			return ((JSONNullValue) obj).t == null;
		}
		return false;
	}
}
