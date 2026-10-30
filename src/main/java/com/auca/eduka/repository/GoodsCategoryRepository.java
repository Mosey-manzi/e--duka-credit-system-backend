package com.auca.eduka.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.auca.eduka.Domain.GoodsCategory;

public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, UUID> {
    @Query ("SELECT g FROM GoodsCategory g WHERE g.shopkeepers IS EMPTY")
    List<GoodsCategory> findUnusedCategories();
}
