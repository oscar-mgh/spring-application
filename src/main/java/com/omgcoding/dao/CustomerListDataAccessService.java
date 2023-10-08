package com.omgcoding.dao;

import com.omgcoding.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao {
    private static final List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer alex = new Customer(1, "Alex", "alex5432@gmail.com", 28);
        Customer gabriela = new Customer(2, "Gabriela", "gab7894@gmail.com", 27);
        Customer angel = new Customer(3, "Angel", "angel456@gmail.com", 26);
        customers.add(alex);
        customers.add(gabriela);
        customers.add(angel);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customers.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customers.stream().anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        return customers.stream().anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public void deleteCustomerById(Integer customerId) {
        customers.stream().filter(c -> c.getId().equals(customerId)).findFirst().ifPresent(customers::remove);
    }

    @Override
    public void updateCustomer(Customer update) {
        customers.add(update);
    }
}
