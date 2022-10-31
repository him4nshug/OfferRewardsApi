package com.retail.offer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.retail.offer.exception.OfferRewardsException;
import com.retail.offer.models.ErrorResponseVO;
import com.retail.offer.utility.OfferRewardsConstants;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	@Autowired
	ErrorResponseVO errorVO;
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		return super.handleNoHandlerFoundException(ex, headers, status, request);
	}

	
	@ExceptionHandler(value=OfferRewardsException.class)
	protected ResponseEntity<ErrorResponseVO> handleOfferRewardsExceptions(OfferRewardsException ex) {
		
		
		if(ex.getErrorCode().equals(OfferRewardsConstants.BAD_REQUEST))
		{
			 errorVO=new ErrorResponseVO(ex.getErrorCode(), ex.getErrorDescription());
		}
		return new ResponseEntity<ErrorResponseVO>(errorVO, HttpStatus.BAD_REQUEST);
	}

	

}
