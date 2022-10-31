package com.retail.offer.models;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;


@Data
public class RewardsResponseVO {
	
	List<CustomerRewardPointsVO> customerRewardPointsVO;
	

	@Override
	public String toString() {
		return "RewardsResponseVO [customerRewardPointsVO=" + customerRewardPointsVO + "]";
	}

	public List<CustomerRewardPointsVO> getCustomerRewardPointsVO() {
		return customerRewardPointsVO;
	}

	public void setCustomerRewardPointsVO(List<CustomerRewardPointsVO> customerRewardPointsVO) {
		this.customerRewardPointsVO = customerRewardPointsVO;
	}
}
