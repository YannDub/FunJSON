package com.steel.json;

public abstract class JSONValue<T> {
	
	protected T t;
	
	public void set(T t) {
		this.t= t;
	}
	
	public T get() {
		return this.t;
	}
	
}
