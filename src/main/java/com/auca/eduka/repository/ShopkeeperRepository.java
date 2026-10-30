package com.auca.eduka.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.auca.eduka.Domain.Shopkeeper;

public interface ShopkeeperRepository extends JpaRepository<Shopkeeper, UUID>{

}
