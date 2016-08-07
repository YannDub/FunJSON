package com.steel.json.values;

import com.steel.json.JSONValue;
import com.steel.json.writers.Writer;

public class JSONBooleanValue extends JSONValue<Boolean> {
	
	public JSONBooleanValue(boolean b) {
		this.t = b;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JSONBooleanValue) {
			return this.t.equals(((JSONBooleanValue) obj).t);
		}
		return false;
	}

	@Override
	public void accept(Writer writer) {
		writer.visitBoolean(this);
	}
}
