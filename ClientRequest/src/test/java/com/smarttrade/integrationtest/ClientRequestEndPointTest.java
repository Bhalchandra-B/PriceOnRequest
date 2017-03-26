package com.smarttrade.integrationtest;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientRequestEndPointTest {

	@Test
	public void testClientRequest() throws IOException {
		String postBody = loadJson("request_price.json");

		Response response = given().contentType("application/json").body(postBody).when()
				.post("http://localhost:8090/price").then().statusCode(HttpStatus.SC_OK).extract().response();

		assertEquals("200.0", response.path("price").toString());
		assertNotNull(response.path("date"));
	}

	protected static String loadJson(String jsonFileName) throws IOException {
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(jsonFileName);
		return IOUtils.toString(stream);
	}
}
