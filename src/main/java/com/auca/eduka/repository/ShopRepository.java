package com.auca.eduka.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.auca.eduka.Domain.Shop;
public interface ShopRepository extends JpaRepository<Shop, UUID> {


}
