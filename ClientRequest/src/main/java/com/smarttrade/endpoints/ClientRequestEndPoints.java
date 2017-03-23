package com.smarttrade.endpoints;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

@RestController
@RequestMapping("/smarttrade")
public class ClientRequestEndPoints {

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public String request() throws ClientProtocolException, IOException {

		String jsonBody = loadJSon("static/request_price.json");

		Response response = given().contentType("application/json").body(jsonBody).when()
				.post("http://localhost:8090/price").then().statusCode(HttpStatus.SC_OK).extract().response();

		Object obj = JsonPath.from(response.getBody().asString()).get("");

		return obj.toString();
	}

	public String loadJSon(String jsonPath) throws IOException {
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(jsonPath);
		return IOUtils.toString(stream);
	}
}
