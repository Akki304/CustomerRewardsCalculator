package com.retailer.rewardcalculator.repository;

import com.retailer.rewardcalculator.entity.CustomerDetails;
import com.retailer.rewardcalculator.entity.TransactionDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionDetailsRepository extends CrudRepository<TransactionDetails,Integer> {


    List<TransactionDetails> findByCustomerDetails(CustomerDetails customerDetails);

    List<TransactionDetails> findByCustomerDetailsAndTransactionDateGreaterThanEqual(CustomerDetails customerDetails, LocalDate startDate);

    List<TransactionDetails> findByCustomerDetailsAndTransactionDateLessThanEqual(CustomerDetails customerDetails, LocalDate endDate);

    List<TransactionDetails> findByCustomerDetailsAndTransactionDateBetween(CustomerDetails customerDetails, LocalDate startDate, LocalDate endDate);
}
