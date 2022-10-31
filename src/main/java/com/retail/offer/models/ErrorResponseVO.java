package com.retail.offer.models;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ErrorResponseVO {
private String errorCode;
private String errorDescription;

/**
 * 
 */
public ErrorResponseVO() {
	super();
	// TODO Auto-generated constructor stub
}
/**
 * @param errorCode
 */
public ErrorResponseVO(String errorCode) {
	super();
	this.errorCode = errorCode;
}
/**
 * @param errorCode
 * @param errorDescription
 */
public ErrorResponseVO(String errorCode, String errorDescription) {
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
