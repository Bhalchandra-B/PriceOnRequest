package com.smarttrade.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GateKeeper {

	private HashMap<String, Integer> ClientToRateMap;

	@Autowired
	public GateKeeper() throws IOException, JSONException {
		ClientToRateMap = new HashMap<String, Integer>();
		String jsonBody = loadJSon("static/ClientToRateLimit.json");
		ClientToRateMap = jsonToMap(jsonBody);
	}

	// Method to Apply rate limit as per client id
	// Recognizing the client on the basis of their productID's.
	// As productID is in format of ISIN, so it starts with some alphabets.
	// These alphabets is basis for applying rate limit.
	// Rate limit per client is given in json file under resource folder.
	public Integer ApplyRateLimitPerClient(ClientRequestEntity client) {

		String productId = client.getProductId();

		String typeOfClient = Character.toString(productId.charAt(0));

		if (ClientToRateMap.containsKey(typeOfClient))
			return ClientToRateMap.get(typeOfClient);
		else
			return ClientToRateMap.get("Default");
	}

	// Method to fill hashmap object form JSON file
	public HashMap<String, Integer> jsonToMap(String t) throws JSONException {

		HashMap<String, Integer> map = new HashMap<>();

		JSONObject jObject = new JSONObject(t);
		Iterator<?> keys = jObject.keys();

		while (keys.hasNext()) {
			String key = (String) keys.next();
			Integer value = (Integer) jObject.get(key);
			map.put(key, value);
		}

		return map;
	}

	// Function to load JSON
	public String loadJSon(String jsonPath) throws IOException {
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(jsonPath);
		return IOUtils.toString(stream);
	}
}
