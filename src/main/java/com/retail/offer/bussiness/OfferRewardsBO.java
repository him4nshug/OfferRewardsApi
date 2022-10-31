package com.retail.offer.bussiness;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.retail.offer.exception.OfferRewardsException;
import com.retail.offer.models.CustomerRewardPointsVO;
import com.retail.offer.models.RewardsRequestVO;
import com.retail.offer.models.RewardsResponseVO;
import com.retail.offer.models.TransactionRecord;
import com.retail.offer.utility.OfferRewardsConstants;

@Component
public class OfferRewardsBO {
	
	

	public static final String YYYY_MM_DD = "yyyy/MM/dd";

	Logger logger = LoggerFactory.getLogger(OfferRewardsBO.class);

	DateTimeFormatter dateformat = DateTimeFormatter.ofPattern(YYYY_MM_DD);

	/*
	 * calculates reward points 
	 * for each customer for each month and aggregate total reward points
	 * for each purchase made by the customer 
	 */
	public RewardsResponseVO calculateRewardPoints(RewardsRequestVO rewardsRequestVO, int rewardsMultiplierAbove100, int rewardsMultiplierAbove50) throws OfferRewardsException {
		List<TransactionRecord> transactions = rewardsRequestVO.getTransactions();
		int storedRewardPoints = 0;
		Map<String, CustomerRewardPointsVO> customerPointsMapping = new HashMap<>();
		/*
		 * populating hashmap with customer id as key and CustomerRewardsPointsVO as
		 * value as a key-value pair
		 */
		for (TransactionRecord transaction : transactions) {

			logger.info("Calculating rewards for customerid : {}", transaction.getCustomerId());

			CustomerRewardPointsVO custRewardPointsVO = null;
			String customerID = transaction.getCustomerId();

			// storing month and respecting total amount spent in that month
			HashMap<String, Integer> monthlyRewardPointsMap = new HashMap<String, Integer>();

			// get reward points for each purchase
			int rewardPointsPerPurchase = getRewardPoints(transaction.getAmount(),rewardsMultiplierAbove100,rewardsMultiplierAbove50);

			if (customerPointsMapping.containsKey(customerID)) {

				// fetch the existing VO
				custRewardPointsVO = (CustomerRewardPointsVO) customerPointsMapping.get(customerID);
				LocalDate transactionDate = null;

				transactionDate = getFormattedDate(transaction);

				if (transactionDate.getMonthValue() != 0) {
					String month = String.valueOf(transactionDate.getMonth());

					// fetch exisiting map for monthlyrewards points
					monthlyRewardPointsMap = custRewardPointsVO.getRewardPointsPerMonth();

					if (monthlyRewardPointsMap.containsKey(month)) {
						storedRewardPoints = monthlyRewardPointsMap.get(month);
						storedRewardPoints = storedRewardPoints + rewardPointsPerPurchase;
						monthlyRewardPointsMap.put(month, storedRewardPoints);

					} else {
						monthlyRewardPointsMap.put(month, rewardPointsPerPurchase);
					}
				}
				custRewardPointsVO.setCustomerID(customerID);
				custRewardPointsVO.setRewardPointsPerMonth(monthlyRewardPointsMap);
				customerPointsMapping.put(customerID, custRewardPointsVO);
			} else {
				CustomerRewardPointsVO newCustRewardPointsVO = new CustomerRewardPointsVO();

				LocalDate transactionDate = getFormattedDate(transaction);
				if (transactionDate.getMonthValue() != 0) {
					String month = String.valueOf(transactionDate.getMonth());

					if (monthlyRewardPointsMap.containsKey(month)) {
						storedRewardPoints = monthlyRewardPointsMap.get(month);
						storedRewardPoints = storedRewardPoints + rewardPointsPerPurchase;
						monthlyRewardPointsMap.put(month, storedRewardPoints);

					} else {
						monthlyRewardPointsMap.put(month, rewardPointsPerPurchase);
					}
				}

				newCustRewardPointsVO.setCustomerID(customerID);
				newCustRewardPointsVO.setRewardPointsPerMonth(monthlyRewardPointsMap);
				customerPointsMapping.put(customerID, newCustRewardPointsVO);

			}

		}

		customerPointsMapping.forEach((K, V) -> logger.debug("customer-reward points map {}:::{}", K, V.toString()));
		/*
		 * iterating through each entry in the customersTransactionMap and calculating
		 * rewards for each month
		 */

		RewardsResponseVO rewardsResponseVO = new RewardsResponseVO();

		List<CustomerRewardPointsVO> customerRewardsPointVOList = new ArrayList<CustomerRewardPointsVO>();

		for (Map.Entry<String, CustomerRewardPointsVO> entry : customerPointsMapping.entrySet()) {

			CustomerRewardPointsVO customerRewardPoints = entry.getValue();
			HashMap<String, Integer> monthlyRewardPointsMap = new HashMap<String, Integer>();
			monthlyRewardPointsMap = customerRewardPoints.getRewardPointsPerMonth();

			int aggregateRewardPoints = 0;
			for (Map.Entry entry1 : monthlyRewardPointsMap.entrySet()) {
				int rewardPoints = (int) entry1.getValue();
				aggregateRewardPoints = aggregateRewardPoints + rewardPoints;

			}

			customerRewardPoints.setAggregateRewardPoints(aggregateRewardPoints);
			customerRewardsPointVOList.add(customerRewardPoints);
			rewardsResponseVO.setCustomerRewardPointsVO(customerRewardsPointVOList);
		}

		logger.debug("Reward points calculated : {}", rewardsResponseVO.toString());

		return rewardsResponseVO;

	}

	/**
	 * @param transaction
	 * @return
	 * @throws OfferRewardsException
	 */
	private LocalDate getFormattedDate(TransactionRecord transaction)
			throws OfferRewardsException {
		LocalDate transactionDate = null;
		try {
			transactionDate = LocalDate.parse(transaction.getTransactionDate(), dateformat);
		} catch (DateTimeParseException e) {
			logger.error(
					"Exception occured while parsing the date for customer id : {} : format provided : {} : format Required : {}",
					transaction.getCustomerId(), transaction.getTransactionDate(), dateformat);
			throw new OfferRewardsException("500", "Parsing exception while reading the transactionDate");
		}
		return transactionDate;
	}
	
	
	/**
	 * @param monthyTransactionAmount
	 * @param rewardsMultiplierAbove50 
	 * @param rewardsMultiplierAbove100 
	 * @return
	 */
	public int getRewardPoints(double TransactionAmount, int rewardsMultiplierAbove100, int rewardsMultiplierAbove50) {
		int rewardPoints = 0;
		if (TransactionAmount > 100) {
			double above100dollarAmount = TransactionAmount - 100;
			rewardPoints = (int) (above100dollarAmount * rewardsMultiplierAbove100) + OfferRewardsConstants.REWARD_VALUE_50;

		}
		if (TransactionAmount > 50 && TransactionAmount < 100) {
			double dollarAmount = TransactionAmount - 50;
			rewardPoints = (int) (dollarAmount * rewardsMultiplierAbove50);
		}
		if (TransactionAmount < 50)
			return rewardPoints;

		return (int) rewardPoints;
	}


	
}
