package com.retailer.rewardcalculator.service;

import com.retailer.rewardcalculator.dto.CustomerRewardsDTO;
import com.retailer.rewardcalculator.dto.MonthlyPointsDTO;
import com.retailer.rewardcalculator.dto.TransactionDTO;
import com.retailer.rewardcalculator.entity.CustomerDetails;
import com.retailer.rewardcalculator.entity.TransactionDetails;
import com.retailer.rewardcalculator.exception.CustomerNotFoundException;
import com.retailer.rewardcalculator.repository.CustomerDetailsRepository;
import com.retailer.rewardcalculator.repository.TransactionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerRewardService {

    @Autowired
    public CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    public TransactionDetailsRepository transactionDetailsRepository;


    /**
     * @param customerId
     * @param startDate
     * @param endDate
     * @return CustomerRewardsDTO which contains the customer details,transactionDetails
     * monthly reward point , total reward point
     */
    public CustomerRewardsDTO customerRewardCalculator(String customerId, LocalDate startDate, LocalDate endDate) {
        Optional<CustomerDetails> customerOptional = Optional.ofNullable(customerDetailsRepository.findByCustomerId(customerId)); // Use findById with the correct ID type

        if (customerOptional.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist with this customerId");

        CustomerDetails customerDetails = customerOptional.get();
        List<TransactionDetails> transactionDetails = new ArrayList<>();

        if (startDate == null && endDate == null)
            transactionDetails = transactionDetailsRepository.findByCustomerDetails(customerDetails);

        else if (startDate != null && endDate == null)
            transactionDetails = transactionDetailsRepository.findByCustomerDetailsAndTransactionDateGreaterThanEqual(customerDetails, startDate);

        else if (endDate != null && startDate == null)
            transactionDetails = transactionDetailsRepository.findByCustomerDetailsAndTransactionDateLessThanEqual(customerDetails, endDate);

        else if (startDate != null && endDate != null && !(endDate.isBefore(startDate)))
            transactionDetails = transactionDetailsRepository.findByCustomerDetailsAndTransactionDateBetween(customerDetails, startDate, endDate);

        else
            throw new InputMismatchException("End date cannot be before start date.");

        CustomerRewardsDTO customerRewardsDTO = new CustomerRewardsDTO();
        customerRewardsDTO.setName(customerDetails.getName());
        customerRewardsDTO.setCustomerId(customerDetails.getCustomerId());
        customerRewardsDTO.setTransactionDetails(convertToTransactionDTOs(transactionDetails));
        customerRewardsDTO.setMonthlyPoints(monthlyPointsCalculator(transactionDetails));
        customerRewardsDTO.setTotalRewardPoints(calculateTotalPoints(transactionDetails));

        return customerRewardsDTO;


    }

    /**
     * @param transactionDetails
     * @return total reward points of a customer
     */
    private int calculateTotalPoints(List<TransactionDetails> transactionDetails)
    {
        int count = 0;
        for (TransactionDetails txnDetails : transactionDetails)
            count += calculateRewardPoints(txnDetails.getTransactionAmount());
        return count;
    }


    /**
     * @param transactionDetails
     * @return List<MonthlyPointsDTO> contains monthly reward points details
     */
    private List<MonthlyPointsDTO> monthlyPointsCalculator(List<TransactionDetails> transactionDetails)
    {

        Map<String, MonthlyPointsDTO> monthlyPointsMap = new HashMap<>();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.US);
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("YYYY", Locale.US);
        for (TransactionDetails txnDetails : transactionDetails)
        {
            String month = monthFormatter.format(txnDetails.getTransactionDate());
            String year = yearFormatter.format(txnDetails.getTransactionDate());
            String key = month + "-" + year;

            int points = calculateRewardPoints(txnDetails.getTransactionAmount());

            if (monthlyPointsMap.containsKey(key))
            {
                MonthlyPointsDTO existing = monthlyPointsMap.get(key);
                existing.setRewardPoints(existing.getRewardPoints() + points);
                monthlyPointsMap.put(key, existing);
            } else
            {

                MonthlyPointsDTO monthlyPoints = new MonthlyPointsDTO(month, Integer.parseInt(year), points);
                monthlyPointsMap.put(key, monthlyPoints);
            }
        }
        return new ArrayList<>(monthlyPointsMap.values());

    }


    /**
     * @param transactionAmount
     * @return rewardPoints
     */
    private int calculateRewardPoints(int transactionAmount) {
        if (transactionAmount > 100)
            return ((transactionAmount - 100) * 2) + 50;
        return transactionAmount > 50 ? transactionAmount - 50 : 0;
    }


    /**
     * @param transactionDetails
     * @return list of transaction details
     */
    private List<TransactionDTO> convertToTransactionDTOs(List<TransactionDetails> transactionDetails)
    {
        return transactionDetails.stream().map(
                txnDetails ->
                {
                    TransactionDTO txnDTO = new TransactionDTO();
                    txnDTO.setTransactionId(txnDetails.getTransactionId());
                    txnDTO.setTransactionAmount(txnDetails.getTransactionAmount());
                    txnDTO.setTransactionDate(txnDetails.getTransactionDate());
                    return txnDTO;
                }).collect(Collectors.toList());

    }


}
