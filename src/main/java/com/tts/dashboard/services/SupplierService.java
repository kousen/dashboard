package com.tts.dashboard.services;

import com.tts.dashboard.dao.ProductRepository;
import com.tts.dashboard.dao.SupplierRepository;
import com.tts.dashboard.entities.Product;
import com.tts.dashboard.entities.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierService {
    private SupplierRepository supplierRepository;
    private ProductRepository productRepository;

    @Autowired
    public SupplierService(SupplierRepository repository,
                           ProductRepository productRepository) {
        this.supplierRepository = repository;
        this.productRepository = productRepository;
    }

    public List<Product> getProductsFromServiceName(String name) {
        Optional<Supplier> supplier = supplierRepository.findByName(name);
        if (supplier.isPresent())
            return supplier.get().getProducts();
        else {
            return new ArrayList<>();
        }
    }

    public List<Product> getProductsFromService(Integer id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent())
            return supplier.get().getProducts();
        else {
            return new ArrayList<>();
        }
    }

    public Optional<Supplier> saveSupplierWithProduct(Integer id, String productName) {
        Optional<Supplier> optional = supplierRepository.findById(id);
        if (optional.isPresent()) {
            Supplier supplier = optional.get();
            Product product = new Product();
            product.setName(productName);
            supplier.getProducts().add(product);
            product.setSupplier(supplier);
            productRepository.save(product);
            supplierRepository.save(supplier);
        }
        return optional;
    }
}
