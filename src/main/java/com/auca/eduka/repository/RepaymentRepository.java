package com.auca.eduka.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auca.eduka.Domain.CreditRequest;
import com.auca.eduka.Domain.Repayment;

public interface RepaymentRepository extends JpaRepository<Repayment, UUID> {

    @Query("SELECT d FROM Repayment d WHERE d.creditRequest.customer.nationalId = :nationalId ORDER BY d.paymentDate DESC")
    List<Repayment> findByCustomerNationalId(@Param("nationalId") String nationalId);

    boolean existsByCreditRequest(CreditRequest creditRequest);
}
