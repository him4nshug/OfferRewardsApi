package com.retail.offer.models;

import java.util.HashMap;

import lombok.Data;

@Data
public class CustomerRewardPointsVO {

	private String customerID;
	private HashMap<String,Integer> monthlyRewardPoints;
	private int aggregateRewardPoints;
	
	
	public int getAggregateRewardPoints() {
		return aggregateRewardPoints;
	}
	public void setAggregateRewardPoints(int aggregateRewardPoints) {
		this.aggregateRewardPoints = aggregateRewardPoints;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public HashMap<String, Integer> getRewardPointsPerMonth() {
		return monthlyRewardPoints;
	}
	public void setRewardPointsPerMonth(HashMap<String, Integer> rewardPointsPerMonth) {
		this.monthlyRewardPoints = rewardPointsPerMonth;
	}
	@Override
	public String toString() {
		return "CustomerRewardPointsVO [customerID=" + customerID + ", monthlyRewardPoints=" + monthlyRewardPoints
				+ ", aggregateRewardPoints=" + aggregateRewardPoints + "]";
	}
	
	
}
