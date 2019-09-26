package com.tts.dashboard;

import com.tts.dashboard.dao.CategoryRepository;
import com.tts.dashboard.dao.ProductRepository;
import com.tts.dashboard.dao.SupplierRepository;
import com.tts.dashboard.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class DashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashboardApplication.class, args);
    }

}
