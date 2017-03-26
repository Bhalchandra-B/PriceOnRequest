package com.smarttrade.integrationtest;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.response.Response;

//**To run the test 'PriceCalculation' microservice should be started before **// 

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceResponseEndPointTest {

	@Test
	public void testGetPriceOnRequest() throws IOException {
		String postBody = loadJson("request_price.json");

		Response response = given().contentType("application/json").body(postBody).when()
				.post("http://localhost:8090/price").then().statusCode(HttpStatus.SC_OK).extract().response();

		assertEquals("200.0", response.path("price").toString());
		assertNotNull(response.path("date"));
	}

	// RateLimit test for Client of type A
	@Test
	public void testRateLimitClientA() throws IOException, ParseException {
		SimpleDateFormat parseFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		String postBody = loadJson("Client_A.json");
		Response response = given().contentType("application/json").body(postBody).when()
				.post("http://localhost:8090/price").then().statusCode(HttpStatus.SC_OK).extract().response();

		Date date1 = parseFormat.parse(response.path("date").toString());

		response = given().contentType("application/json").body(postBody).when().post("http://localhost:8090/price")
				.then().statusCode(HttpStatus.SC_OK).extract().response();

		Date date2 = parseFormat.parse(response.path("date").toString());

		long diffTwoRequest = (date2.getTime() - date1.getTime()) / 1000;

		assertEquals(1, diffTwoRequest);
	}

	// RateLimit test for Client of type B
	@Test
	public void testRateLimitClientB() throws IOException, ParseException {
		SimpleDateFormat parseFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		String postBody = loadJson("Client_B.json");
		Response response = given().contentType("application/json").body(postBody).when()
				.post("http://localhost:8090/price").then().statusCode(HttpStatus.SC_OK).extract().response();

		Date date1 = parseFormat.parse(response.path("date").toString());

		response = given().contentType("application/json").body(postBody).when().post("http://localhost:8090/price")
				.then().statusCode(HttpStatus.SC_OK).extract().response();

		Date date2 = parseFormat.parse(response.path("date").toString());

		long diffTwoRequest = (date2.getTime() - date1.getTime()) / 1000;

		assertEquals(2, diffTwoRequest);
	}

	// RateLimit test for Client of type C
	@Test
	public void testRateLimitClientC() throws IOException, ParseException {
		SimpleDateFormat parseFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		String postBody = loadJson("Client_C.json");
		Response response = given().contentType("application/json").body(postBody).when()
				.post("http://localhost:8090/price").then().statusCode(HttpStatus.SC_OK).extract().response();

		Date date1 = parseFormat.parse(response.path("date").toString());

		response = given().contentType("application/json").body(postBody).when().post("http://localhost:8090/price")
				.then().statusCode(HttpStatus.SC_OK).extract().response();

		Date date2 = parseFormat.parse(response.path("date").toString());

		long diffTwoRequest = (date2.getTime() - date1.getTime()) / 1000;

		assertEquals(3, diffTwoRequest);
	}

	// RateLimit test for default Client
	@Test
	public void testRateLimitClientDefault() throws IOException, ParseException {
		SimpleDateFormat parseFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		String postBody = loadJson("Client_Default.json");
		Response response = given().contentType("application/json").body(postBody).when()
				.post("http://localhost:8090/price").then().statusCode(HttpStatus.SC_OK).extract().response();

		Date date1 = parseFormat.parse(response.path("date").toString());

		response = given().contentType("application/json").body(postBody).when().post("http://localhost:8090/price")
				.then().statusCode(HttpStatus.SC_OK).extract().response();

		Date date2 = parseFormat.parse(response.path("date").toString());

		long diffTwoRequest = (date2.getTime() - date1.getTime()) / 1000;

		assertEquals(4, diffTwoRequest);
	}

	protected static String loadJson(String jsonFileName) throws IOException {
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(jsonFileName);
		return IOUtils.toString(stream);
	}
}
