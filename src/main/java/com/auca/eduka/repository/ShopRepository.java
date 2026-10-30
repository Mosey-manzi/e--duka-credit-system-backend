package com.auca.eduka.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.auca.eduka.Domain.Shop;
public interface ShopRepository extends JpaRepository<Shop, UUID> {
    @Query ("""
        SELECT s FROM Shop s 
        WHERE NOT EXISTS 
        (SELECT k FROM Shopkeeper k WHERE k.shop =s)
        ORDER BY s.shopNumber
        """)
    List<Shop> findFreeShops();
    
    Optional<Shop> findByShopNumber(int shopNumber);

}
