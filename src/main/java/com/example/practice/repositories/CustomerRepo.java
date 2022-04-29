package com.example.practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.practice.models.Customer;

public interface CustomerRepo extends JpaRepository<Customer,Integer>{

}
