package com.auca.eduka.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auca.eduka.Domain.Shopkeeper;
import java.util.List;
import com.auca.eduka.Domain.GoodsCategory;


public interface ShopkeeperRepository extends JpaRepository<Shopkeeper, UUID>{
    @Query("""
        SELECT DISTINCT s FROM Shopkeeper s JOIN s.goodsCategories c 
        WHERE LOWER(c.name) = LOWER(:name)
        """)
    List<Shopkeeper> findByGoodsCategoryName(@Param("name") String name);
}
