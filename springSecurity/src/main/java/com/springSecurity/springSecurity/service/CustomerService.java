package com.springSecurity.springSecurity.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springSecurity.springSecurity.model.Customer;
import com.springSecurity.springSecurity.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer create(final Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer readById(final long id) throws Exception {
        Optional<Customer> opCustomer = customerRepository.findById(id);
        if (opCustomer.isEmpty())
            throw new Exception("Cannot find any record with id " + id);
        return customerRepository.findById(id).get();
    }

    public List<Customer> readAll() {
        return customerRepository.findAll().stream().collect(Collectors.toList());
    }

    public Customer update (final Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteById (final long id) {
        customerRepository.deleteById(id);
    }
}
