package com.retailer.rewardcalculator.controller;

import com.retailer.rewardcalculator.dto.TransactionDTO;
import com.retailer.rewardcalculator.service.TransactionDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TransactionDetailsControllerTest {
    @Mock
    private TransactionDetailsService transactionDetailsService;

    @InjectMocks
    private TransactionDetailsController transactionDetailsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddTransaction_success() {

        TransactionDTO transactionDTO = new TransactionDTO();
        when(transactionDetailsService.addTransaction(any(TransactionDTO.class))).thenReturn(new ResponseEntity<>("Transaction successful", HttpStatus.OK));


        ResponseEntity<String> response = transactionDetailsController.addTransaction(transactionDTO);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transaction successful", response.getBody());

    }
}
