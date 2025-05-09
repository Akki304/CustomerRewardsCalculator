package com.retailer.rewardcalculator.service;

import com.retailer.rewardcalculator.dto.TransactionDTO;
import com.retailer.rewardcalculator.entity.CustomerDetails;
import com.retailer.rewardcalculator.entity.TransactionDetails;
import com.retailer.rewardcalculator.repository.CustomerDetailsRepository;
import com.retailer.rewardcalculator.repository.TransactionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionDetailsService
{

    @Autowired
    TransactionDetailsRepository transactionDetailsRepository;
    @Autowired
    CustomerDetailsRepository customerDetailsRepository;


    /**
     * @param transactionDTO
     * @return
     */
    public ResponseEntity<String> addTransaction(TransactionDTO transactionDTO)
    {
        Optional<CustomerDetails> customerDetails = Optional.ofNullable(customerDetailsRepository.findByCustomerId(transactionDTO.getCustomerId()));
        if (customerDetails.isEmpty())
            return new ResponseEntity<>("Customer does not exist", HttpStatus.NOT_FOUND);

        transactionDTO.setCustomerDetails(customerDetails.get());
        transactionDetailsRepository.save(toEntity(transactionDTO));
        return new ResponseEntity<>("Transaction sucessfully added", HttpStatus.OK);
    }

    /**
     * @param dto
     * @return
     */
    public TransactionDetails toEntity(TransactionDTO dto)
    {
        if (dto == null)
            return null;

        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setTransactionAmount(dto.getTransactionAmount());
        transactionDetails.setCustomerDetails(dto.getCustomerDetails());
        transactionDetails.setTransactionDate(dto.getTransactionDate());
        return transactionDetails;
    }
}
