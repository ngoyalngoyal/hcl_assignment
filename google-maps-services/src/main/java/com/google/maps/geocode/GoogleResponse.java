/**
 * 
 */
package com.google.maps.geocode;

import java.util.Arrays;

/**
 * @author NGoyal
 *
 */
public class GoogleResponse {

	private Result[] results ;
	private String status ; 
	private String error_message;
	
	/**
	 * @return the error_message
	 */
	public String getError_message() {
		return error_message;
	}

	/**
	 * @param error_message the error_message to set
	 */
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public Result[] getResults() {
		return results; 
		}
	
	public void setResults(Result[] results) {
		this.results = results; 
		} 
	
	public String getStatus() { 
		
		return status; 
		} 
	
	public void setStatus(String status) {
		this.status = status; 
		}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GoogleResponse [results=" + Arrays.toString(results)
				+ ", status=" + status + ", error_message=" + error_message
				+ "]";
	}
	
	
}
