package com.furnitureCompany.customerservice.repository;

import com.furnitureCompany.customerservice.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository <Customer, String> {
    Customer findCustomersByCustomerId(Long customerId);
    void deleteByCustomerId(Long customerId);
    List<Customer> findCustomersByCustomerIdIn(List<Long> customerIds);
}
