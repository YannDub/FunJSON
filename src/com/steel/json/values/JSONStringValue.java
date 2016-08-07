package com.steel.json.values;

import com.steel.json.JSONValue;
import com.steel.json.writers.Writer;

public class JSONStringValue extends JSONValue<String> {
	
	public JSONStringValue(String string) {
		this.t = string;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof JSONStringValue) {
			return this.t.equals(((JSONStringValue) obj).t);
		}
		return false;
	}
	
	public int hashCode() {
		return this.t.hashCode();
	}

	@Override
	public void accept(Writer writer) {
		writer.visitString(this);
	}
}
