package com.retailer.rewardcalculator.service;

import com.retailer.rewardcalculator.controller.CustomerRewardController;
import com.retailer.rewardcalculator.entity.CustomerDetails;
import com.retailer.rewardcalculator.entity.TransactionDetails;
import com.retailer.rewardcalculator.repository.CustomerDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class CustomerDetailsServiceTest {

    @Mock
    private CustomerDetailsRepository customerDetailsRepository;

    @InjectMocks
    private CustomerDetailsService customerDetailsService; // Corrected to RewardsController

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCustomerAlreayExist()
    {
        CustomerDetails existingCustomer = new CustomerDetails();
        existingCustomer.setCustomerId("123");
        Mockito.when(customerDetailsRepository.existsByCustomerId("123")).thenReturn(true);

        CustomerDetails newCustomer = new CustomerDetails();
        newCustomer.setCustomerId("123");
        assertEquals("Already a customer exist with this customerID",customerDetailsService.saveCustomerDetails(newCustomer).getBody());
    }
    @Test
    public void testANewCustomer()
    {
        CustomerDetails newCustomer = new CustomerDetails();
        newCustomer.setCustomerId("121");
        List<TransactionDetails> transactions = new ArrayList<>();
        TransactionDetails transaction1 = new TransactionDetails();
        transaction1.setTransactionId(1);
        transaction1.setTransactionAmount(120);
        transactions.add(transaction1);

        TransactionDetails transaction2 = new TransactionDetails();
        transaction2.setTransactionId(2);
        transaction2.setTransactionAmount(60);
        transactions.add(transaction2);
        newCustomer.setTransactions(transactions);
        Mockito.when(customerDetailsRepository.existsByCustomerId("123")).thenReturn(false);

        assertEquals("User sucessfully created",customerDetailsService.saveCustomerDetails(newCustomer).getBody());

        verify(customerDetailsRepository).save(newCustomer);
    }

}
