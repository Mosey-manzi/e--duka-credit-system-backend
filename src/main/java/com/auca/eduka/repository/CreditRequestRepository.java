package com.auca.eduka.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auca.eduka.Domain.CreditRequest;
import com.auca.eduka.Domain.CreditStatus; 
import com.auca.eduka.Domain.Shopkeeper;

@Repository
public interface CreditRequestRepository extends JpaRepository<CreditRequest, UUID> {
    List<CreditRequest> findByStatusOrderByRequestDateAsc(CreditStatus status);
    List<CreditRequest> findByRequestDateBetweenOrderByRequestDateAsc(
        LocalDate start,
        LocalDate end);
    
    boolean existsByShopkeeperAndRequestDateAndStatusNot(Shopkeeper shopkeeper, LocalDate requestDate, CreditStatus status);

    @Query(
        "SELECT r.status, COUNT(r) FROM CreditRequest r GROUP BY r.status"
    )
    List<Object[]> countByStatusGroup();

    @Modifying 
    @Query("""
        UPDATE CreditRequest r SET r.status = :declined
        WHERE r.shopkeeper.id = :shopkeeperId AND r.requestDate = :date AND r.status <> :disbursed
        """)

    int cancelDay(@Param("shopkeeperId") UUID shopkeeperId,
                @Param ("date") LocalDate date,
                @Param ("declined") CreditStatus declined,
                @Param ("disbursed") CreditStatus disbursed            
                );
}
