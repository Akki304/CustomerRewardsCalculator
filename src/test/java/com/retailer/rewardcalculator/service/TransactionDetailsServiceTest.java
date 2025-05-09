package com.retailer.rewardcalculator.service;

import com.retailer.rewardcalculator.dto.TransactionDTO;
import com.retailer.rewardcalculator.entity.CustomerDetails;
import com.retailer.rewardcalculator.entity.TransactionDetails;
import com.retailer.rewardcalculator.repository.CustomerDetailsRepository;
import com.retailer.rewardcalculator.repository.TransactionDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class TransactionDetailsServiceTest {
    @Mock
    private CustomerDetailsRepository customerDetailsRepository;

    @Mock
    private TransactionDetailsRepository transactionDetailsRepository;

    @InjectMocks
    private TransactionDetailsService transactionDetailsService; // Corrected to RewardsController

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testAddTransaction() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(1);
        transactionDTO.setTransactionAmount(101);
        transactionDTO.setTransactionDate(LocalDate.now());
        transactionDTO.setCustomerId("cus1");
        CustomerDetails details = new CustomerDetails();
        details.setCustomerId("cus1");
        details.setName("test123");


        Mockito.when(customerDetailsRepository.findByCustomerId("cus1")).thenReturn(details);
        assertEquals("Transaction sucessfully added", transactionDetailsService.addTransaction(transactionDTO).getBody());
        verify(transactionDetailsRepository).save(any(TransactionDetails.class));
    }

    @Test
    public void testCustomerNotFound() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(1);
        transactionDTO.setTransactionAmount(101);
        transactionDTO.setTransactionDate(LocalDate.now());
        transactionDTO.setCustomerId("cus1");
        CustomerDetails details = new CustomerDetails();
        details.setCustomerId("cus1");
        details.setName("test123");


        Mockito.when(customerDetailsRepository.findByCustomerId("cus2")).thenReturn(details);
        assertEquals("Customer does not exist", transactionDetailsService.addTransaction(transactionDTO).getBody());
        verify(transactionDetailsRepository, Mockito.never()).save(any(TransactionDetails.class));
    }



}
