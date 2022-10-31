package com.retail.offer.exception;

public class OfferRewardsException extends Exception {

	private String errorCode;
	private String errorDescription;
	
	
	
	
	/**
	 * @param errorDescription
	 */
	public OfferRewardsException(String errorDescription) {
		super();
		this.errorDescription = errorDescription;
	}
	
	/**
	 * @param errorCode
	 * @param errorDescription
	 */
	public OfferRewardsException(String errorCode, String errorDescription) {
		super();
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
}
