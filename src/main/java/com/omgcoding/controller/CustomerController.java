package com.omgcoding.controller;

import com.omgcoding.dao.CustomerRegistrationRequest;
import com.omgcoding.service.CustomerService;
import com.omgcoding.dao.CustomerUpdateRequest;
import com.omgcoding.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("{customerId}")
    public Customer getCustomerById(@PathVariable Integer customerId) {
        return customerService.getCustomer(customerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        customerService.addCustomer(request);
    }

    @DeleteMapping("{customerId}")
    public void removeCustomer(@PathVariable Integer customerId) {
        customerService.deleteCustomer(customerId);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable Integer customerId, @RequestBody CustomerUpdateRequest updateRequest) {
        customerService.updateCustomer(customerId, updateRequest);
    }
}
