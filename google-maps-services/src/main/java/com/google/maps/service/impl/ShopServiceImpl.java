/**
 * 
 */
package com.google.maps.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.constant.Constants;
import com.google.maps.geocode.AddressConverter;
import com.google.maps.geocode.DistanceCalculator;
import com.google.maps.geocode.GoogleResponse;
import com.google.maps.geocode.Result;
import com.google.maps.model.ShopEntity;
import com.google.maps.repository.ShopEntityRepository;
import com.google.maps.request.Shop;
import com.google.maps.response.CurrentAddress;
import com.google.maps.response.GenericResponse;
import com.google.maps.response.PreviousAddress;
import com.google.maps.response.ShopDetails;
import com.google.maps.response.ShopResponse;
import com.google.maps.service.ShopService;

/**
 * @author NGoyal
 *
 */
@Service
public class ShopServiceImpl implements ShopService {

	private final Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);
	
	@Autowired
	ShopEntityRepository shopEntityRepository;
	
	@Autowired
	AddressConverter addressConverter;
	
	@Autowired
	DistanceCalculator distanceCalculator;
	
	/*
	 * (non-Javadoc)
	 * @see com.google.maps.service.ShopService#findShopByName(java.lang.String)
	 */
	@Override
	public ShopEntity findShopByName(String shopName) {
		
		return shopEntityRepository.findByShopName(shopName);
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.maps.service.ShopService#findShopByLatitudeAndLongitude(java.lang.String, java.lang.String)
	 */
	@Override
	public ShopDetails findShopByLatitudeAndLongitude(String latitude,
			String longitude) throws Exception{
		List<ShopEntity> shopEntity =  (List<ShopEntity>) shopEntityRepository.findAll();
		
		Map<Double,ShopEntity> hashMap = new HashMap<>();
		
		for (ShopEntity shopEntity2 : shopEntity) {
			
			double distance = distanceCalculator.distance(Double.valueOf(latitude), Double.valueOf(longitude), Double.valueOf(shopEntity2.getLatitide()), Double.valueOf(shopEntity2.getLongitude()), "N");
			hashMap.put(distance,shopEntity2);
		}
		
		List<Double> lonLatDistance = new ArrayList<>();
		
		for (Map.Entry<Double, ShopEntity> entry : hashMap.entrySet()) {
			
			lonLatDistance.add(entry.getKey());
		}
		
		Collections.sort(lonLatDistance);
		
		Double shortestDistance = lonLatDistance.get(0);
		
		ShopEntity shopEnt = hashMap.get(shortestDistance);
		
		ShopDetails shopDetails = new ShopDetails();
		
		shopDetails.setLatitude(shopEnt.getLatitide());
		shopDetails.setLongitude(shopEnt.getLongitude());
		shopDetails.setShopAddress(shopEnt.getShopAddress());
		shopDetails.setShopName(shopEnt.getShopName());
		shopDetails.setShopPostalCode(shopEnt.getPostalCode());
		
		return shopDetails;
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.google.maps.service.ShopService#saveShopDetails(com.google.maps.request.Shop)
	 */
	@Override
	public <T> GenericResponse<T> saveShopDetails(Shop shop) throws IOException {
		
		logger.info("calling saveShopDetails method");
		GenericResponse<T> genericResponse = new GenericResponse<T>();
		ShopResponse shopResponse = new ShopResponse();
		
		//calling google geoCode api to get latitude and longitude
		GoogleResponse googleResponse = addressConverter.convertToLatLong(shop.getShopAddress());
		
		logger.info("google response fetched" + googleResponse);
		
		if(googleResponse.getStatus().equals(Constants.OK)){
			
			logger.info("successfully get latitude and longitide from google geocode api");
			String latitude = "";
			String longitude="";
			
			for(Result result : googleResponse.getResults()){
				
				latitude = result.getGeometry().getLocation().getLat();
				longitude = result.getGeometry().getLocation().getLng();
				
				logger.info("latitude:" +latitude + "longitude:" + longitude);
			}
			
			ShopEntity shopEnt = findShopByName(shop.getShopName());
			
			if(shopEnt!=null){
				
				logger.info("shop already exist so updating the current shop details with old one which ws added by another user");
				//Creating json response because shop already exist and we are overriding the details so
				// before overriding we are setting old shop details as well as new shop details to response
				PreviousAddress previousAddress = new PreviousAddress();
				ShopDetails previousShopDetails = new ShopDetails();
				
				previousShopDetails.setLatitude(shopEnt.getLatitide());
				previousShopDetails.setLongitude(shopEnt.getLongitude());
				previousShopDetails.setShopAddress(shopEnt.getShopAddress());
				previousShopDetails.setShopName(shopEnt.getShopName());
				previousShopDetails.setShopPostalCode(shopEnt.getPostalCode());
				previousAddress.setShop(previousShopDetails);
				
				CurrentAddress currentAddress = new CurrentAddress();
				ShopDetails currentShopDetails = new ShopDetails();
				
				currentShopDetails.setLatitude(latitude);
				currentShopDetails.setLongitude(longitude);
				currentShopDetails.setShopAddress(shop.getShopAddress());
				currentShopDetails.setShopName(shop.getShopName());
				currentShopDetails.setShopPostalCode(shop.getPostalCode());
				currentAddress.setShop(currentShopDetails);
				
				shopResponse.setCurrentAddress(currentAddress);
				shopResponse.setPreviousAddress(previousAddress);
				
				shopEnt.setLatitide(latitude);
				shopEnt.setLongitude(longitude);
				shopEnt.setPostalCode(shop.getPostalCode());
				shopEnt.setShopAddress(shop.getShopAddress());
				
				logger.info("saving the following shop entity object" + shopEnt);
				shopEntityRepository.save(shopEnt);
				
				genericResponse.setCode(Constants.SUCCESS_CODE);
				genericResponse.setMessage(Constants.SUCCESS_MESSAGE);
				genericResponse.setData((T) shopResponse);
				
			}else{
				logger.info("Adding the shop as its not exist in database");
				ShopEntity shopEntity = new ShopEntity();
				
				shopEntity.setShopName(shop.getShopName());
				shopEntity.setShopAddress(shop.getShopAddress());
				shopEntity.setPostalCode(shop.getPostalCode());
				shopEntity.setLatitide(latitude);
				shopEntity.setLongitude(longitude);
				
				logger.info("following shop entity object:" + shopEntity);
				shopEntityRepository.save(shopEntity);
				
				//After successful save create response
				genericResponse.setCode(Constants.SUCCESS_CODE);
				genericResponse.setMessage(Constants.NEW_SHOP_ADDED);
				
			}
			
			return genericResponse;
			
		}else{
			logger.error("unbale to fetch the latitude and longitude from google geocode api due to : " +googleResponse.getStatus());
			genericResponse.setCode(Constants.ERROR_CODE);
			genericResponse.setMessage(googleResponse.getStatus());
			return genericResponse;
		}
		
		
		
	}

}
