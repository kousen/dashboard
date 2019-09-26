package com.tts.dashboard.dao;

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

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class SupplierRepositoryTest {
    @Autowired
    private SupplierRepository dao;

    @Test
    public void findByName() {
        Optional<Supplier> supplier = dao.findByName("3M");
        supplier.ifPresent(s -> System.out.println(s.getProducts()));
    }

}