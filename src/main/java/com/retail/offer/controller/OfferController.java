package com.retail.offer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.offer.bussiness.OfferRewardsBO;
import com.retail.offer.exception.OfferRewardsException;
import com.retail.offer.models.RewardsRequestVO;
import com.retail.offer.models.RewardsResponseVO;
import com.retail.offer.utility.OfferRewardsConstants;

@RestController
@RequestMapping(value="/offer")
public class OfferController {
	
	Logger logger = LoggerFactory.getLogger(OfferController.class);
	@Autowired
	OfferRewardsBO offerRewardsBO;
	
	 @Value("${offerRewardsApi.rewardsMultiplierAbove100}")
		private int rewardsMultiplierAbove100;
		 @Value("${offerRewardsApi.rewardsMultiplierAbove50}")
		private int rewardsMultiplierAbove50;

	@PostMapping(value="/rewards",produces = MediaType.APPLICATION_JSON_VALUE)
	public RewardsResponseVO calculateRewardPoints(@RequestBody RewardsRequestVO rewardsRequestVO) throws OfferRewardsException
	{
		
		//validate the input request 
		if(rewardsRequestVO==null )
		{
			logger.error("Input request is empty");
			throw new OfferRewardsException(OfferRewardsConstants.BAD_REQUEST, "Bad input data");
			
		}else if(rewardsRequestVO.getTransactions().isEmpty())
		{
			logger.error("transactiosn list cannot be empty");
			throw new OfferRewardsException(OfferRewardsConstants.BAD_REQUEST, "no transactions provided");
				
		}
		logger.info("Inside offer controller to calculate reward points");

		RewardsResponseVO rewardPoints=offerRewardsBO.calculateRewardPoints(rewardsRequestVO,rewardsMultiplierAbove100,rewardsMultiplierAbove50);
		
		return rewardPoints;
		
	}
}
