/**
 * 
 */
package com.google.maps.service;

import java.io.IOException;

import com.google.maps.model.ShopEntity;
import com.google.maps.request.Shop;
import com.google.maps.response.GenericResponse;
import com.google.maps.response.ShopDetails;

/**
 * @author NGoyal
 *
 */
public interface ShopService {

	public ShopEntity findShopByName(String shopName);
	
	public ShopDetails findShopByLatitudeAndLongitude(String latitude, String longitude) throws Exception;
	
	public <T> GenericResponse<T> saveShopDetails(Shop shop) throws IOException;
}
