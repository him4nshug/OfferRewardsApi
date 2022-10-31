package com.retail.offer.models;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class RewardsRequestVO {
	

private List<TransactionRecord> transactions;

public List<TransactionRecord> getTransactions() {
	return transactions;
}

public void setTransactions(List<TransactionRecord> transactions) {
	this.transactions = transactions;
}
}
