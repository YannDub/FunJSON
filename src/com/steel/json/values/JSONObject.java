package com.steel.json.values;

import java.util.HashMap;
import java.util.Map.Entry;

import com.steel.json.JSONValue;
import com.steel.json.exceptions.JSONPropertyNotFoundException;
import com.steel.json.writers.Writer;

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

	@Override
	public void accept(Writer writer) {
		writer.visitObject(this);
	}
	
	public JSONValue<?> getProperty(String property) throws JSONPropertyNotFoundException {
		JSONValue<?> propertyValue = this.get().get(new JSONStringValue(property));
		if(propertyValue == null) throw new JSONPropertyNotFoundException(property);
		return propertyValue;
	}
}
