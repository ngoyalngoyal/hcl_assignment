/**
 * 
 */
package com.google.maps.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.constant.Constants;
import com.google.maps.exception.GenericException;
import com.google.maps.request.Shop;
import com.google.maps.response.GenericResponse;
import com.google.maps.response.ShopDetails;
import com.google.maps.service.ShopService;

/**
 * @author NGoyal
 *
 */



@RestController
@RequestMapping("/shop")
@Api(value = "ShopController", description = "Operations include to add a shop and to fetch any shop information via longitude and latitude by passing in url ")
public class ShopController {

	private final Logger logger = LoggerFactory.getLogger(ShopController.class);

	@Autowired
	ShopService shopService;

	/**
	 * 
	 * @param shop
	 * @return
	 * @throws IOException
	 * @throws GenericException
	 * 
	 * This method will add a new shop if its not exist in database but if teh current shop already existed
	 * in database means added by another user then we will update the shop details and return both shop details as response.
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Add Shop Information")
	public <T> GenericResponse<T> addShop(@RequestBody Shop shop)
			throws IOException, GenericException {

		logger.info("add shop method called");

		try {
			return shopService.saveShopDetails(shop);
		} catch (Exception e) {

			throw new GenericException(Constants.ERROR_CODE, e.getMessage());
		}

	}

	/**
	 * 
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws GenericException
	 * 
	 *  This method will fetch shop details by latitude and longitude which passed 
	 *  in request as request param attributes
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Fetch shop details by longitude and latitude")
	public <T> GenericResponse<T> getShopDetailsByLatitudeLongitude(
			@RequestParam(value = "latitude") String latitude,
			@RequestParam(value = "longitude") String longitude)
			throws GenericException {

		logger.info("getShopDetailsByLatitudeLongitude method called");
		GenericResponse<T> genericResponse = new GenericResponse<T>();
		
		if(StringUtils.isEmpty(longitude) || StringUtils.isEmpty(latitude) ){
			genericResponse.setCode(Constants.ERROR_CODE);
			genericResponse.setMessage(Constants.LAT_LONG_EMPTY);
			return genericResponse;
		}

		try {
			ShopDetails shopDetails = shopService
					.findShopByLatitudeAndLongitude(latitude, longitude);

			genericResponse.setCode(Constants.SUCCESS_CODE);
			genericResponse.setMessage(Constants.SUCCESS_MESSAGE);
			genericResponse.setData((T) shopDetails);

			logger.info("returning the success response of the method");
			return genericResponse;
		} catch (Exception e) {

			throw new GenericException(Constants.ERROR_CODE, e.getMessage());
		}

	}

	/*
	 * Exception handler resolver which will catch all GenericExceptions which occured in ShopController
	 */
	@ExceptionHandler(GenericException.class)
	public <T> GenericResponse<T> catchExceptions(GenericException g){
		
		GenericResponse<T> genericResponse = new GenericResponse<T>();
		
		genericResponse.setCode(g.getCode());
		genericResponse.setMessage(g.getMessage());
		
		return genericResponse;
		
	}
}
