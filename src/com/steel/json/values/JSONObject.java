package com.steel.json.values;

import java.util.HashMap;
import java.util.Map.Entry;

import com.steel.json.JSONValue;

public class JSONObject extends JSONValue<HashMap<JSONStringValue, JSONValue<?>>> {
	
	public JSONObject(HashMap<JSONStringValue, JSONValue<?>> t) {
		this.t = t;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject) obj;
			
			boolean b = true;
			for(Entry<JSONStringValue, JSONValue<?>> entry : this.t.entrySet()) {
				b = b && jsonObject.t.containsKey(entry.getKey()) && jsonObject.t.get(entry.getKey()).equals(entry.getValue());
			}
			return b;
		}
		
		return false;
	}
}
