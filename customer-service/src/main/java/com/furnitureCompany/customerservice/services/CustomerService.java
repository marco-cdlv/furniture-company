package com.furnitureCompany.customerservice.services;

import com.furnitureCompany.customerservice.events.source.SimpleSourceBean;
import com.furnitureCompany.customerservice.model.Customer;
import com.furnitureCompany.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

//    @Autowired
//    SimpleSourceBean simpleSourceBean;

    public List<Customer> getCustomers() {
        return StreamSupport.stream(customerRepository.findAll()
                .spliterator(), false).collect(Collectors.toList());
    }

    public Customer getCustomerById(Long customerId) {
        if (customerId == null) return null;
        return customerRepository.findCustomersByCustomerId(customerId);
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
        //simpleSourceBean.publishCustomerChange("SAVE", customer.getFullName());
    }

    public void updateCustomer(Customer customer, Long customerId) {
        Customer customerToUpdate = customerRepository.findCustomersByCustomerId(customerId);

        customerToUpdate.setPersonalIdNumber(customer.getPersonalIdNumber());
        customerToUpdate.setFullName(customer.getFullName());
        customerToUpdate.setAddress(customer.getAddress());
        customerToUpdate.setPhoneNumber(customer.getPhoneNumber());

        customerRepository.save(customerToUpdate);
        //simpleSourceBean.publishCustomerChange("UPDATE", customer.getFullName());
    }

    public void deleteCustomerById(Long customerId) {
        customerRepository.deleteByCustomerId(customerId);
        //simpleSourceBean.publishCustomerChange("DELETE", String.valueOf(customerId));
    }

    public List<Customer> getCustomersByIds(List<Long> customerIds) {
        return customerRepository.findCustomersByCustomerIdIn(customerIds);
    }

    public void addCustomers(List<Customer> customers) {
        customerRepository.saveAll(customers);
    }
}
