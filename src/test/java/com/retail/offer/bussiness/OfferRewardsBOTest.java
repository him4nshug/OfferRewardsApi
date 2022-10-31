package com.retail.offer.bussiness;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.offer.exception.OfferRewardsException;
import com.retail.offer.models.RewardsRequestVO;
import com.retail.offer.models.RewardsResponseVO;
import com.retail.offer.models.TransactionRecord;

@SpringBootTest
@ActiveProfiles("test")
public class OfferRewardsBOTest {

	@Autowired
	OfferRewardsBO offerRewardsBO;
	
	
	
	@Test
	public void test_calculateRewards_for_valid_test_data() throws OfferRewardsException
	{
		RewardsRequestVO rewardsRequestVO=new RewardsRequestVO();
		List<TransactionRecord> transactions=new ArrayList<TransactionRecord>();
		TransactionRecord transactionVO1=new TransactionRecord();
		transactionVO1.setCustomerId("1");
		transactionVO1.setAmount(120);
		transactionVO1.setTransactionDate("2022/10/15");
		transactions.add(transactionVO1);
		
		//setting transactions made
		rewardsRequestVO.setTransactions(transactions);
		
		RewardsResponseVO actualRewardPoints=offerRewardsBO.calculateRewardPoints(rewardsRequestVO,2,1);
		
		assertEquals(90, actualRewardPoints.getCustomerRewardPointsVO().get(0).getAggregateRewardPoints());
		
	}
	
	@Test
	public void test_calculateRewards_for_invalid_date_format_data() throws OfferRewardsException
	{
		RewardsRequestVO rewardsRequestVO=new RewardsRequestVO();
		List<TransactionRecord> transactions=new ArrayList<TransactionRecord>();
		TransactionRecord transactionVO1=new TransactionRecord();
		transactionVO1.setCustomerId("1");
		transactionVO1.setAmount(120);
		transactionVO1.setTransactionDate("20221015");
		transactions.add(transactionVO1);
		
		//setting transactions made
		rewardsRequestVO.setTransactions(transactions);
		
		assertThrowsExactly(OfferRewardsException.class,()-> offerRewardsBO.calculateRewardPoints(rewardsRequestVO,2,1));
		assertThatExceptionOfType(Exception.class);
		
	}
	
	@Test
	public void test_calculateRewards_larger_test_data() throws OfferRewardsException, StreamReadException, DatabindException, IOException
	{
		RewardsRequestVO rewardsRequestVO=new RewardsRequestVO();
		File request1 = new File("src/test/resources/TransactionRecord.json");
		ObjectMapper objectMapper=new ObjectMapper();
	  rewardsRequestVO=objectMapper.readValue(request1, RewardsRequestVO.class);
		RewardsResponseVO actualRewardPoints=offerRewardsBO.calculateRewardPoints(rewardsRequestVO,2,1);
		
		RewardsResponseVO expectedResponseVO=new RewardsResponseVO();
		File response1 = new File("src/test/resources/ExpectedRewardsResponseVO.json");
		expectedResponseVO=objectMapper.readValue(response1, RewardsResponseVO.class);
		assertThatNoException();
		assertNotNull(actualRewardPoints);
		assertEquals(expectedResponseVO.getCustomerRewardPointsVO().get(0).getAggregateRewardPoints(), actualRewardPoints.getCustomerRewardPointsVO().get(0).getAggregateRewardPoints());
		assertEquals(expectedResponseVO.getCustomerRewardPointsVO().get(1).getAggregateRewardPoints(), actualRewardPoints.getCustomerRewardPointsVO().get(1).getAggregateRewardPoints());
		assertEquals(expectedResponseVO.getCustomerRewardPointsVO().get(2).getAggregateRewardPoints(), actualRewardPoints.getCustomerRewardPointsVO().get(2).getAggregateRewardPoints());
		System.out.println(actualRewardPoints.toString());
		
	}
}
