package com.steel.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.steel.json.exceptions.JSONPropertyNotFoundException;
import com.steel.json.values.JSONObject;
import com.steel.json.writers.JSONWriter;
import com.steel.parser.JSONParser;

public class JSON {
	
	private JSONObject jsonObject;
	
	public JSON(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	public JSON(String json) {
		this.jsonObject = (JSONObject) JSONParser.json(json);
	}
	
	public JSON(File file) throws IOException {
		if(file.exists()) {
			byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
			String tmp = new String(encoded);
			tmp.replaceAll("[\r\n]+", "");
			this.jsonObject = (JSONObject) JSONParser.json(tmp);
		}
	}
	
	public void write(File file) throws IOException {
		FileWriter fw = new FileWriter(file);
		String json = new JSONWriter(this).write();
		fw.write(json);
		fw.close();
	}
	
	public JSONObject getJsonObject() {
		return this.jsonObject;
	}
	
	public JSONValue<?> getProperty(String property) throws JSONPropertyNotFoundException {
		return this.jsonObject.getProperty(property);
	}
}
