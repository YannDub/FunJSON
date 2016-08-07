package com.steel.json;

import com.steel.json.exceptions.JSONPropertyNotFoundException;
import com.steel.json.writers.Writer;

public abstract class JSONValue<T> {
	
	protected T t;
	
	public void set(T t) {
		this.t= t;
	}
	
	public T get() {
		return this.t;
	}
	
	public abstract void accept(Writer writer);
	
	public JSONValue<?> getProperty(String property) throws JSONPropertyNotFoundException {
		throw new JSONPropertyNotFoundException(property);
	}
	
}
