/**
 * 
 */
package com.google.maps.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author NGoyal
 *
 */
@Entity
@Table(name="shop")
public class ShopEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3415014488424945863L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name="shop_name",nullable=false,unique=true)
	private String shopName;
	
	@Column(name="shop_address",nullable=false)
	private String shopAddress;
	
	@Column(name="postal_code",nullable=false)
	private String postalCode;
	
	@Column(name="longitude")
	private String longitude;
	
	@Column(name="latitide")
	private String latitide;

	public ShopEntity(){
		
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

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

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitide
	 */
	public String getLatitide() {
		return latitide;
	}

	/**
	 * @param latitide the latitide to set
	 */
	public void setLatitide(String latitide) {
		this.latitide = latitide;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ShopEntity [id=" + id + ", shopName=" + shopName
				+ ", shopAddress=" + shopAddress + ", postalCode=" + postalCode
				+ ", longitude=" + longitude + ", latitide=" + latitide + "]";
	}
	
	
	

}
