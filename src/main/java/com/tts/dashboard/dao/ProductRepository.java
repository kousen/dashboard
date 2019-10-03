package com.tts.dashboard.dao;

import com.tts.dashboard.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findAllBySupplierName(String name);
    Optional<Product> findByName(String name);

    // List<Product> findAllOrderByDiscount();
}
