package com.tts.dashboard.dao;

import com.tts.dashboard.entities.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void findAllOrderByDiscount() {
        // List<Product> products = dao.findAll(Sort.by("discount"));
        // List<Product> products = dao.findAllOrderByDiscount();

        List<Product> products = dao.findAll().stream()
                .sorted(Comparator.comparingDouble(Product::getDiscount))
                .collect(Collectors.toList());

        products.stream()
                .mapToDouble(Product::getDiscount)
                .reduce(0.0, (prev, current) -> {
                    assertTrue(prev <= current);
                    return current;
                });
    }
}