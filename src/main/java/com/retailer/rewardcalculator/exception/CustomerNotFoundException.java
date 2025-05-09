package com.retailer.rewardcalculator.exception;

import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends RuntimeException
{

    public CustomerNotFoundException(String message)
    {
       super(message);
    }


}
