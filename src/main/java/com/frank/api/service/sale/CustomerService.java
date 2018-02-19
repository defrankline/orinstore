package com.frank.api.service.sale;

import com.frank.api.model.pos.Customer;
import com.frank.api.repository.pos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id){
        return customerRepository.findOne(id);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Page<Customer> getPaginatedCustomers(Integer page, Integer perPage){
        return customerRepository.findAll(new PageRequest(page,perPage));
    }

    public void deleteCustomer(Customer customer){
        customerRepository.delete(customer);
    }
}
