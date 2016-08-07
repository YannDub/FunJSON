package com.steel.json.values;

import com.steel.json.JSONValue;
import com.steel.json.writers.Writer;

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

	@Override
	public void accept(Writer writer) {
		writer.visitNull(this);
	}
}
