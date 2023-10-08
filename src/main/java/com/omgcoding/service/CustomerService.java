package com.omgcoding.service;

import com.omgcoding.dao.CustomerDao;
import com.omgcoding.dao.CustomerRegistrationRequest;
import com.omgcoding.dao.CustomerUpdateRequest;
import com.omgcoding.exception.DuplicatedResourceException;
import com.omgcoding.exception.RequestValidationException;
import com.omgcoding.exception.ResourceNotFoundException;
import com.omgcoding.model.Customer;
import com.omgcoding.repository.CustomerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerDao customerDao;
    private final CustomerRepository customerRepository;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao, CustomerRepository customerRepository) {
        this.customerDao = customerDao;
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Integer id) {
        return customerDao.selectCustomerById(id).orElseThrow(() -> new ResourceNotFoundException("customer with id [%s] not found".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        String email = customerRegistrationRequest.email();

        if (customerDao.existsPersonWithEmail(email)) {
            throw new DuplicatedResourceException("email already taken");
        }

        Customer customer = new Customer(customerRegistrationRequest.name(), customerRegistrationRequest.email(), customerRegistrationRequest.age());

        customerDao.insertCustomer(customer);
    }

    public void deleteCustomer(Integer id) {
        if (customerDao.existsPersonWithId(id)) {
            customerDao.deleteCustomerById(id);
        } else {
            throw new ResourceNotFoundException("customer with id [%s] not found".formatted(id));
        }
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest updateRequest) {
        Customer customer = this.getCustomer(customerId);
        boolean changes = false;
        if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())) {
            customer.setName(updateRequest.name());
            changes = true;
        }
        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())) {
            customer.setAge(updateRequest.age());
            changes = true;
        }
        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {
            if (customerDao.existsPersonWithEmail(updateRequest.email())) {
                throw new DuplicatedResourceException("email already taken");
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no changes found");
        }

        customerDao.updateCustomer(customer);
    }

    @PostConstruct
    private void postConstruct() {
        Customer alex = new Customer("Alex", "alex54532@gmail.com", 28);
        Customer angel = new Customer("Angel", "angel43456@gmail.com", 26);
        Customer karla = new Customer("Karla", "karl91287@gmail.com", 21);
        Customer gabriel = new Customer("Gabriel", "gab78934@gmail.com", 27);
        Customer james = new Customer("James", "james18723@gmail.com", 30);
        List<Customer> customers = List.of(alex, gabriel, james, karla, angel);
        customerRepository.saveAll(customers);
    }
}