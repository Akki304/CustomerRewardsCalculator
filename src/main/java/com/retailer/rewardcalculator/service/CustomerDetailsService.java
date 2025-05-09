package com.retailer.rewardcalculator.service;

import com.retailer.rewardcalculator.entity.CustomerDetails;
import com.retailer.rewardcalculator.entity.TransactionDetails;
import com.retailer.rewardcalculator.repository.CustomerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService {

    @Autowired
    CustomerDetailsRepository customerDetailsRepository;


    /**
     * @param customerDetails  which contains all the details of the customer
     * @return a string with httpStatus code
     */
    public ResponseEntity<String> saveCustomerDetails(CustomerDetails customerDetails)
    {
        if (customerDetailsRepository.existsByCustomerId(customerDetails.getCustomerId()))
            return new ResponseEntity<>("Already a customer exist with this customerID", HttpStatus.BAD_REQUEST);
        if (customerDetails.getTransactions() != null) {
            for (TransactionDetails transaction : customerDetails.getTransactions()) {
                transaction.setCustomerDetails(customerDetails);

            }
        }


        customerDetailsRepository.save(customerDetails);
        return new ResponseEntity<>("User sucessfully created", HttpStatus.OK);
    }
}
