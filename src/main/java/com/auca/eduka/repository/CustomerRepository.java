package com.auca.eduka.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auca.eduka.Domain.Customer;


@Repository 
public interface CustomerRepository extends JpaRepository<Customer, UUID>{
    List<Customer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}
