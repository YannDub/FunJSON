package com.steel.json.values;

import com.steel.json.JSONValue;

public class JSONNumberValue extends JSONValue<Double> {
	
	public JSONNumberValue(double number) {
		this.t = number;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JSONNumberValue) {
			return this.t.equals(((JSONNumberValue) obj).t);
		}
		return false;
	}
}
