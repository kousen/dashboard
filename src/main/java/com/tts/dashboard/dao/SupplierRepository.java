package com.tts.dashboard.dao;

import com.tts.dashboard.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier,Integer> {
    Optional<Supplier> findByName(String name);
}
