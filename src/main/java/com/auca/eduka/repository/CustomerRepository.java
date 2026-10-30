package com.auca.eduka.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auca.eduka.Domain.Customer;


@Repository 
public interface CustomerRepository extends JpaRepository<Customer, UUID>{
    List<Customer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query ("SELECT DISTINCT r.customer FROM CreditRequest r WHERE r.shopkeeper.id = :shopkeeperId")
    List<Customer> findCustomersOfShopkeeper(@Param("shopkeeperId") UUID shopkeeperId);
}
