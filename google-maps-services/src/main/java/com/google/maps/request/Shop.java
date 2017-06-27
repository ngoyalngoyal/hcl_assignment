/**
 * 
 */
package com.google.maps.request;

import io.swagger.annotations.ApiParam;

/**
 * @author NGoyal
 *
 */
public class Shop {

	@ApiParam(name="shopName",required=true)
	private String shopName;
	
	@ApiParam(name="shopAddress",required=true)
	private String shopAddress;
	
	@ApiParam(name="postalCode",required=true)
	private String postalCode;
	
	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}
	/**
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	/**
	 * @return the shopAddress
	 */
	public String getShopAddress() {
		return shopAddress;
	}
	/**
	 * @param shopAddress the shopAddress to set
	 */
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}
	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	
}
