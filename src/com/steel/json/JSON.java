package com.steel.json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.steel.json.values.JSONObject;
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
}
