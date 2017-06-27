/**
 * 
 */
package com.google.maps.geocode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.constant.Constants;

/**
 * @author NGoyal
 *
 * Geocode request URL. Here see we are passing "json" it means we will get 
 * the output in JSON format. You can also pass "xml" instead of "json" 
 * for XML output. For XML output URL will be  "http://maps.googleapis.com/maps/api/geocode/xml"; 
 */

@Component
public class AddressConverter {

	private final Logger logger = LoggerFactory.getLogger(AddressConverter.class);
	
	
	/*  Here the fullAddress String is in format like * "address,city,state,zipcode".
	 *  Here address means "street number + route" * .
	 */ 
	public GoogleResponse convertToLatLong(String fullAddress) throws IOException { 
		
		logger.info("convertToLatLong method called");
		/* * Create an java.net.URL object by passing the request URL in * constructor. 
		 * Here you can see I am converting the fullAddress String * in UTF-8 format. 
		 * You will get Exception if you don't convert your * address in UTF-8 format. 
		 * Perhaps google loves UTF-8 format. :) In * parameter we also need to pass "sensor" parameter. 
		 * sensor (required * parameter) � Indicates whether or not the geocoding request comes 
		 * from a device with a location sensor. This value must be either true * or false. 
		 */
		
		URL url = new URL(Constants.GEOCODE_API_URL + "?address=" + URLEncoder.encode(fullAddress, "UTF-8") + "&key=" + Constants.API_KEY + "&sensor=false"); 
		
		// Open the Connection 
		logger.info("opening the https connection to hit geocode api");
		URLConnection conn = url.openConnection(); 
		InputStream in = conn.getInputStream() ; 
		ObjectMapper mapper = new ObjectMapper(); 
		GoogleResponse response = (GoogleResponse)mapper.readValue(in,GoogleResponse.class);
		in.close(); 
		
		logger.info("method is returning the geocode api response");
		return response;
		
	}
	
}
