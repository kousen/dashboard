package com.tts.dashboard.dao;

import com.tts.dashboard.entities.Product;
import com.tts.dashboard.entities.Supplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository dao;

    @Test
    public void findAllProducts() {
        List<Product> products = dao.findAll();
        assertTrue(products.size() >= 683);
    }

    @Test
    public void findProductsBySupplierName() {
        List<Product> products = dao.findAllBySupplierName("3M");
        products.forEach(System.out::println);
        assertEquals(11, products.size());
    }

    @Test
    public void fetchProductThenSupplier() {
        Optional<Product> optionalProduct = dao.findByName("Scotch");
        assertTrue(optionalProduct.isPresent());
    }
}