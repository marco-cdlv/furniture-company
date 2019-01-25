package com.furnitureCompany.customerservice.controllers;

import com.furnitureCompany.customerservice.model.Customer;
import com.furnitureCompany.customerservice.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @RequestMapping(value = "/ids/{customer_ids}", method = RequestMethod.GET)
    public List<Customer> getCustomerById(@PathVariable("customer_ids") List<Long> customerIds) {
        return customerService.getCustomersByIds(customerIds);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.PUT)
    public void updateCustomer(@PathVariable("customerId") Long customerId, @RequestBody Customer customer) {
        customerService.updateCustomer(customer, customerId);
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomerById(customerId);
    }
}
