package com.retailer.rewardcalculator.dto;

public class MonthlyPointsDTO
{
    private String monthName;
    private int year;
    private int rewardPoints;

    public MonthlyPointsDTO(String monthName, int year, int rewardPoints)
    {
        this.monthName = monthName;
        this.year = year;
        this.rewardPoints = rewardPoints;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
}
