package com.retailer.rewardcalculator.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.retailer.rewardcalculator.entity.CustomerDetails;

import java.time.LocalDate;

public class TransactionDTO
{
    private int transactionId;
    private int transactionAmount;
    @JsonIgnore
    private String customerId;
    @JsonIgnore
    private CustomerDetails customerDetails;
    private LocalDate transactionDate;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

}
