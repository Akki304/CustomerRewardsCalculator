package com.retailer.rewardcalculator.repository;

import com.retailer.rewardcalculator.entity.CustomerDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDetailsRepository extends CrudRepository<CustomerDetails,Integer> {

    boolean existsByCustomerId(String customerId);

    CustomerDetails findByCustomerId(String customerId);
}
