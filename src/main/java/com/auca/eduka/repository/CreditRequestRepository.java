package com.auca.eduka.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auca.eduka.Domain.CreditRequest;
import com.auca.eduka.Domain.CreditStatus; 

@Repository
public interface CreditRequestRepository extends JpaRepository<CreditRequest, UUID> {
    List<CreditRequest> findByStatusOrderByRequestDateAsc(CreditStatus status);
    List<CreditRequest> findByRequestDateBetweenOrderByRequestDateAsc(
        LocalDate start,
        LocalDate end);
}
