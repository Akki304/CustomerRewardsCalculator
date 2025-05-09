package com.retailer.rewardcalculator.controller;

import com.retailer.rewardcalculator.dto.TransactionDTO;
import com.retailer.rewardcalculator.entity.TransactionDetails;
import com.retailer.rewardcalculator.service.TransactionDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/retailer")
public class TransactionDetailsController
{
    @Autowired
    TransactionDetailsService transactionDetailsService;

    /**
     * @param transactionDTO
     */
    @PostMapping("/addTransaction")
    public ResponseEntity<String> addTransaction(@RequestBody TransactionDTO transactionDTO) {
        return transactionDetailsService.addTransaction(transactionDTO);
    }
}
