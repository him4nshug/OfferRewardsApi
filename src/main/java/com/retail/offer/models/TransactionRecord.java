package com.retail.offer.models;



import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class TransactionRecord {
	
@NonNull	
private String customerId;	
public String getCustomerId() {
	return customerId;
}
public void setCustomerId(String customerId) {
	this.customerId = customerId;
}
@NonNull
private double amount;

@NonNull
private String transactionDate;

public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public String getTransactionDate() {
	return transactionDate;
}
public void setTransactionDate(String transactionDate) {
	this.transactionDate = transactionDate;
}

}
