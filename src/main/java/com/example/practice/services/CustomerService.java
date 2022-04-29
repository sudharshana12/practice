package com.example.practice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.practice.models.Customer;
import com.example.practice.repositories.CustomerRepo;

@Service
public class CustomerService  {
	@Autowired
	CustomerRepo cr;
	
	
	
	public void save(Customer customer) {
		cr.save(customer);
	}

	public void saveAll(List<Customer> cs) {
		cr.saveAll(cs);
	}
}
