package com.game4u.arwinebottle.net;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 对Gson的方法做了一层包装
 * 
 * @author luowenjie
 * 
 */
public class GsonObject {
	JsonObject jsonData = null;

	public GsonObject(JsonObject jsonData) {
		if (jsonData == null) {
			throw new RuntimeException("jsonData is null");
		}
		this.jsonData = jsonData;
	}

	public JsonObject getAsJsonObject() {
		return jsonData;
	}

	public boolean isnull(String key) {
		if (jsonData.has(key)) {
			if (!jsonData.get(key).isJsonNull()) {
				return false;
			}
		}
		return true;
	}

	public int getInt(String key, int defaultvalue) {
		if (jsonData.has(key)) {
			defaultvalue = jsonData.get(key).getAsInt();
		}
		return defaultvalue;
	}

	public long getLong(String key, long defaultvalue) {
		if (jsonData.has(key)) {
			defaultvalue = jsonData.get(key).getAsLong();
		}
		return defaultvalue;
	}

	public float getFloat(String key, float defaultvalue) {
		if (jsonData.has(key)) {
			defaultvalue = jsonData.get(key).getAsFloat();
		}
		return defaultvalue;
	}

	public double getDouble(String key, double defaultvalue) {
		if (jsonData.has(key)) {
			defaultvalue = jsonData.get(key).getAsDouble();
		}
		return defaultvalue;
	}

	public String getString(String key) {
		if (jsonData.has(key)) {
			String value = jsonData.get(key).getAsString();
			return value;
		}
		return null;
	}

	public String getString(String key, String defaultvalue) {
		if (jsonData.has(key)) {
			defaultvalue = jsonData.get(key).getAsString();
		}
		return defaultvalue;
	}

	public boolean getBoolean(String key, boolean defaultvalue) {
		if (jsonData.has(key)) {
			defaultvalue = jsonData.get(key).getAsBoolean();
		}
		return defaultvalue;
	}

	public GsonObject getGsonObject(String key) {
		if (jsonData.has(key)) {
			JsonObject value = jsonData.get(key).getAsJsonObject();
			if (value != null) {
				return new GsonObject(value);
			}
		}
		return null;
	}

	public JsonObject getJsonObject(String key) {
		if (jsonData.has(key)) {
			JsonObject value = jsonData.get(key).getAsJsonObject();
			return value;
		}
		return null;
	}

	public JsonArray getJsonArray(String key) {
		if (jsonData.has(key)) {
			JsonArray value = jsonData.get(key).getAsJsonArray();
			return value;
		}
		return null;
	}
	
    public <T> List<T> getAsList(Gson gson, String key, Class<T> elementClass){
		
		List<T> list = new ArrayList<T>();
		
		JsonArray jsonArray = this.getJsonArray(key);
		if(jsonArray != null){
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonElement jsonElement = jsonArray.get(i);
				list.add(gson.fromJson(jsonElement, elementClass));
			}
		}
		
		return list;
	}
	
//	public final static GsonObject getJsonStrAsGsonObject(String data){
//		GsonObject dataGson = new GsonObject(gsonWithoutNull.fromJson(data, JsonObject.class));
//		return dataGson;
//	}
	
	@Override
	public String toString() {
		if (jsonData != null) {
			return jsonData.toString();
		} else {
			return "";
		}
	}

	public static void main(String[] args) {
		String qids = "1,2,3,";
		System.out.println(qids.substring(0, qids.length()-1));
	}
}
