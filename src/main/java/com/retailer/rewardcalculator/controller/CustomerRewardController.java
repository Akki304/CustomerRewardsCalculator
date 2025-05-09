package com.retailer.rewardcalculator.controller;

import com.retailer.rewardcalculator.dto.CustomerRewardsDTO;
import com.retailer.rewardcalculator.entity.CustomerDetails;
import com.retailer.rewardcalculator.service.CustomerDetailsService;
import com.retailer.rewardcalculator.service.CustomerRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/retailer")
public class CustomerRewardController
{
    @Autowired
    CustomerDetailsService customerDetailsService;
    @Autowired
    CustomerRewardService customerRewardService;

    /**
     * @param customerDetails
     */
    @PostMapping("/createCustomer")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerDetails customerDetails) {
        return customerDetailsService.saveCustomerDetails(customerDetails);

    }

    /**
     * @param customerId
     * @param startDate
     * @param endDate
     */
    @GetMapping("/rewardCalculator")
    public ResponseEntity<?> getRewardPoints(
            @RequestParam String customerId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        try {
            CustomerRewardsDTO customerTransactionDetailsDTO = customerRewardService.customerRewardCalculator(customerId, startDate, endDate);
            return new ResponseEntity<>(customerTransactionDetailsDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
