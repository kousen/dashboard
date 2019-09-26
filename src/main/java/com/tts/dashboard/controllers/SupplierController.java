package com.tts.dashboard.controllers;

import com.tts.dashboard.entities.Product;
import com.tts.dashboard.services.SupplierService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
    private SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public List<Product> findProductsFromService(@PathVariable int id) {
        return service.getProductsFromService(id);
    }
}
