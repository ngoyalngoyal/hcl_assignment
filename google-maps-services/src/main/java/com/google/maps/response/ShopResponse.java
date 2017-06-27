/**
 * 
 */
package com.google.maps.response;

/**
 * @author NGoyal
 *
 */
public class ShopResponse {

	private CurrentAddress currentAddress;
	private PreviousAddress previousAddress;
	/**
	 * @return the currentAddress
	 */
	public CurrentAddress getCurrentAddress() {
		return currentAddress;
	}
	/**
	 * @param currentAddress the currentAddress to set
	 */
	public void setCurrentAddress(CurrentAddress currentAddress) {
		this.currentAddress = currentAddress;
	}
	/**
	 * @return the previousAddress
	 */
	public PreviousAddress getPreviousAddress() {
		return previousAddress;
	}
	/**
	 * @param previousAddress the previousAddress to set
	 */
	public void setPreviousAddress(PreviousAddress previousAddress) {
		this.previousAddress = previousAddress;
	}
	
	
}
