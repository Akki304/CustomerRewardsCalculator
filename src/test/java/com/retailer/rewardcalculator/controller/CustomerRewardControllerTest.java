package com.retailer.rewardcalculator.controller;

import com.retailer.rewardcalculator.dto.CustomerRewardsDTO;
import com.retailer.rewardcalculator.entity.CustomerDetails;
import com.retailer.rewardcalculator.service.CustomerDetailsService;
import com.retailer.rewardcalculator.service.CustomerRewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerRewardControllerTest {
    @Mock
    private CustomerDetailsService customerDetailsService;
    @Mock
    private CustomerRewardService customerRewardService;
    @InjectMocks
    private CustomerRewardController customerRewardController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCustomer_success() {
        CustomerDetails customerDetails = new CustomerDetails();
        when(customerDetailsService.saveCustomerDetails(customerDetails)).thenReturn(new ResponseEntity<>("Success", HttpStatus.OK));


        ResponseEntity<String> response = customerRewardController.createCustomer(customerDetails);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());

    }

    @Test
    public void testGetRewardPoints_success() {

        String customerId = "123";
        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now();
        CustomerRewardsDTO rewardsDTO = new CustomerRewardsDTO();
        when(customerRewardService.customerRewardCalculator(customerId, startDate, endDate)).thenReturn(rewardsDTO);


        ResponseEntity<?> response = customerRewardController.getRewardPoints(customerId, startDate, endDate);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(rewardsDTO, response.getBody());
    }

    @Test
    public void testGetRewardPoints_exception() {

        String customerId = "123";
        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now();
        String errorMessage = "Error calculating rewards";
        when(customerRewardService.customerRewardCalculator(customerId, startDate, endDate))
                .thenThrow(new RuntimeException(errorMessage));


        ResponseEntity<?> response = customerRewardController.getRewardPoints(customerId, startDate, endDate);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
        verify(customerRewardService).customerRewardCalculator(customerId, startDate, endDate);
    }

}
