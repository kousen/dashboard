package com.tts.dashboard.services;

import com.tts.dashboard.entities.Product;
import com.tts.dashboard.entities.Supplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SupplierServiceTest {
    @Autowired
    private SupplierService service;

    @Test
    public void findProductsByServiceName() {
        List<Product> products = service.getProductsFromServiceName("3M");
        products.forEach(System.out::println);
        assertEquals(11, products.size());
    }

    @Test
    public void findProductsByService() {
        List<Product> products = service.getProductsFromService(1);
        products.forEach(System.out::println);
        assertEquals(11, products.size());
    }

    @Test
    public void saveSupplierAndProduct() {
        Optional<Supplier> opt = service.saveSupplierWithProduct(1, "Widget");
        opt.ifPresent(supplier -> System.out.println(supplier.getProducts()));
    }
}