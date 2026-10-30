package com.auca.eduka.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.auca.eduka.Domain.GoodsCategory;

public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, UUID> {

}
