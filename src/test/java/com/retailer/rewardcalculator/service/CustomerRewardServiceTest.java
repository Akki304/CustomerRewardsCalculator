package com.retailer.rewardcalculator.service;

import com.retailer.rewardcalculator.entity.CustomerDetails;
import com.retailer.rewardcalculator.entity.TransactionDetails;
import com.retailer.rewardcalculator.exception.CustomerNotFoundException;
import com.retailer.rewardcalculator.repository.CustomerDetailsRepository;
import com.retailer.rewardcalculator.repository.TransactionDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class CustomerRewardServiceTest {

    @Mock
    private CustomerDetailsRepository customerDetailsRepository;

    @Mock
    private TransactionDetailsRepository transactionDetailsRepository;

    @InjectMocks
    private CustomerRewardService customerRewardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testRewardCalculatorWithOnlyCustomerId() {
        CustomerDetails existingCustomer = customerDetials();
        List<TransactionDetails> listOfTransactionetails = transactionDetailsList();
        Mockito.when(customerDetailsRepository.findByCustomerId("cus1")).thenReturn(existingCustomer);
        Mockito.when(transactionDetailsRepository.findByCustomerDetails(existingCustomer)).thenReturn(listOfTransactionetails);
        assertEquals(104, customerRewardService.customerRewardCalculator("cus1", null, null).getTotalRewardPoints());
    }

    @Test
    public void testRewardCalculatorWithStartDateAndCustomerId() {
        CustomerDetails existingCustomer = customerDetials();

        List<TransactionDetails> listOfTransactionetails = transactionDetailsList();
        Mockito.when(customerDetailsRepository.findByCustomerId("cus1")).thenReturn(existingCustomer);
        Mockito.when(transactionDetailsRepository.findByCustomerDetailsAndTransactionDateGreaterThanEqual(existingCustomer, LocalDate.now())).thenReturn(listOfTransactionetails);
        assertEquals(0, customerRewardService.customerRewardCalculator("cus1", LocalDate.of(2024, 1, 5), null).getTotalRewardPoints());
    }

    @Test
    public void testRewardCalculatorWithEndDateAndCustomerId() {
        CustomerDetails existingCustomer = customerDetials();

        List<TransactionDetails> listOfTransactionetails = transactionDetailsList();
        Mockito.when(customerDetailsRepository.findByCustomerId("cus1")).thenReturn(existingCustomer);
        Mockito.when(transactionDetailsRepository.findByCustomerDetailsAndTransactionDateLessThanEqual(existingCustomer, LocalDate.now())).thenReturn(listOfTransactionetails);
        assertEquals(0, customerRewardService.customerRewardCalculator("cus1", null, LocalDate.of(2024, 1, 20)).getTotalRewardPoints());

    }

    @Test
    public void testRewardCalculatorWithStartDateEnDateAndCustomerId() {
        CustomerDetails existingCustomer = customerDetials();

        List<TransactionDetails> listOfTransactionetails = transactionDetailsList();
        Mockito.when(customerDetailsRepository.findByCustomerId("cus1")).thenReturn(existingCustomer);
        Mockito.when(transactionDetailsRepository.findByCustomerDetailsAndTransactionDateGreaterThanEqual(existingCustomer, LocalDate.now())).thenReturn(listOfTransactionetails);
        assertEquals(0, customerRewardService.customerRewardCalculator("cus1", LocalDate.of(2024, 1, 5), LocalDate.of(2024, 1, 20)).getTotalRewardPoints());
    }

    @Test
    public void testCustomerNotFoundException() {
        CustomerDetails existingCustomer = customerDetials();
        List<TransactionDetails> listOfTransactionetails = transactionDetailsList();
        Mockito.when(customerDetailsRepository.findByCustomerId("cus1")).thenReturn(existingCustomer);
        Mockito.when(transactionDetailsRepository.findByCustomerDetails(existingCustomer)).thenReturn(listOfTransactionetails);
        assertThrows(CustomerNotFoundException.class, () -> {
            customerRewardService.customerRewardCalculator(null, null, null);
        });
        verify(transactionDetailsRepository, Mockito.never()).save(any(TransactionDetails.class));
    }

    private CustomerDetails customerDetials() {
        CustomerDetails existingCustomer = new CustomerDetails();
        existingCustomer.setCustomerId("cus1");
        List<TransactionDetails> details = transactionDetailsList();
        existingCustomer.setTransactions(details);
        return existingCustomer;
    }

    private TransactionDetails createTransactionDetails(int transactionId, int amount, LocalDate date) {
        TransactionDetails transaction = new TransactionDetails();
        transaction.setTransactionId(transactionId);
        transaction.setTransactionAmount(amount);
        transaction.setTransactionDate(date);
        return transaction;
    }

    private List<TransactionDetails> transactionDetailsList() {
        List<TransactionDetails> listOfTransactionDetails = new ArrayList<>();
        listOfTransactionDetails.add(createTransactionDetails(1, 124, LocalDate.of(2024, 1, 15)));
        ;
        listOfTransactionDetails.add(createTransactionDetails(1, 52, LocalDate.of(2024, 1, 10)));
        listOfTransactionDetails.add(createTransactionDetails(1, 54, LocalDate.of(2024, 1, 5)));
        return listOfTransactionDetails;
    }
}
