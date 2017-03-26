package com.smarttrade.endpoints;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.util.concurrent.RateLimiter;
import com.smarttrade.domain.ClientRequestEntity;
import com.smarttrade.domain.GateKeeper;
import com.smarttrade.domain.PriceResponseEntity;

@RestController
@RequestMapping("/price")
public class PriceResponseEndpoint {

	@Autowired
	private PriceResponseEntity priceEntity;

	private SimpleDateFormat dateFormat;
	private RateLimiter rateLimiter;

	private GateKeeper gateKeeper;

	public PriceResponseEndpoint() throws IOException, JSONException {
		rateLimiter = RateLimiter.create(1.0);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		gateKeeper = new GateKeeper();
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=UTF-8")
	public PriceResponseEntity priceCalculation(@RequestBody ClientRequestEntity clientRequest) {
		Integer limitForRequest = gateKeeper.ApplyRateLimitPerClient(clientRequest);
		rateLimiter.acquire(limitForRequest);
		return getCurrentPriceWithDate(clientRequest);
	}

	private PriceResponseEntity getCurrentPriceWithDate(ClientRequestEntity clientRequest) {
		priceEntity.setPrice(200.00);
		priceEntity.setDate(dateFormat.format(new Date()));
		return priceEntity;
	}
}
