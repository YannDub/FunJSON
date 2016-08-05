package com.steel.json.values;

import java.util.List;

import com.steel.json.JSONValue;

public class JSONArray extends JSONValue<JSONValue<?>[]>{
	
	public JSONArray(JSONValue<?>[] t) {
		this.t = t;
	}
	
	public JSONArray(List<JSONValue<?>> t) {
		this.t = new JSONValue<?>[t.size()];
		int i = this.t.length - 1;
		if(i >= 0) {
			for(JSONValue<?> each : t) {
				this.t[i] = each;
				i--;
			}
		}
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JSONArray) {
			boolean b = true;
			for(int i = 0; i < this.t.length; i++) {
				b = b && this.t[i].equals(((JSONArray) obj).t[i]);
			}
			return b || (this.t.length == 0 && ((JSONArray) obj).t.length == 0);
		}
		return false;
	}
}
