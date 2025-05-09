package com.retailer.rewardcalculator.dto;

import com.retailer.rewardcalculator.entity.CustomerDetails;

import java.util.List;

public class CustomerRewardsDTO
{
    private String name;
    private String customerId;
    private List<TransactionDTO> transactionDetails;
    private List<MonthlyPointsDTO> monthlyPoints;
    private int totalRewardPoints;

    public int getTotalRewardPoints() {
        return totalRewardPoints;
    }

    public void setTotalRewardPoints(int totalRewardPoints) {
        this.totalRewardPoints = totalRewardPoints;
    }

    public List<TransactionDTO> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<TransactionDTO> listOfTransactionDetails) {
        this.transactionDetails = listOfTransactionDetails;
    }

    public List<MonthlyPointsDTO> getMonthlyPoints() {
        return monthlyPoints;
    }

    public void setMonthlyPoints(List<MonthlyPointsDTO> listOfMonthlyPoints) {
        this.monthlyPoints = listOfMonthlyPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
