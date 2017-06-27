/**
 * 
 */
package com.google.maps.exception;

/**
 * @author NGoyal
 *
 */
public class GenericException extends RuntimeException {

	private String message;
	private String code;
	/**
	 * @param message
	 * @param code
	 */
	public GenericException(String message, String code) {
		this.message = message;
		this.code = code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	
}
