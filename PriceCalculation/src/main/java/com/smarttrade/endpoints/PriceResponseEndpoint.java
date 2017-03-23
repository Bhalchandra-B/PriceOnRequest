package com.smarttrade.endpoints;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smarttrade.domain.ClientRequestEntity;
import com.smarttrade.domain.PriceResponseEntity;

@RestController
@RequestMapping("/price")
public class PriceResponseEndpoint {

	@Autowired
	private PriceResponseEntity priceEntity;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=UTF-8")
	public PriceResponseEntity priceCalculation(@RequestBody ClientRequestEntity clientRequest) {

		return getCurrentPriceWithDate(clientRequest);
	}

	private PriceResponseEntity getCurrentPriceWithDate(ClientRequestEntity clientRequest) {
		priceEntity.setPrice(200.00);
		priceEntity.setDate(new Date());
		return priceEntity;
	}
}
