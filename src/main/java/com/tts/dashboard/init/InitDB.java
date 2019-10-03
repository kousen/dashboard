package com.tts.dashboard.init;

import com.tts.dashboard.dao.CategoryRepository;
import com.tts.dashboard.dao.ProductRepository;
import com.tts.dashboard.dao.SupplierRepository;
import com.tts.dashboard.entities.Category;
import com.tts.dashboard.entities.Product;
import com.tts.dashboard.entities.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class InitDB implements ApplicationRunner {
    @Autowired
    private ApplicationContext context;

    private CategoryRepository categoryRepository;
    private SupplierRepository supplierRepository;
    private ProductRepository productRepository;

    @Autowired
    public InitDB(CategoryRepository categoryRepository,
                  SupplierRepository supplierRepository,
                  ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
    }


    @Autowired
    public void run(ApplicationArguments args) {
        if (productRepository.count() > 0) return;

        productRepository.deleteAll();
        categoryRepository.deleteAll();
        supplierRepository.deleteAll();

        DataSource dataSource = context.getBean(DataSource.class);
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement pst = conn.prepareStatement("alter table products auto_increment = 1");
            pst.execute();
            pst = conn.prepareStatement("alter table categories auto_increment = 1");
            pst.execute();
            pst = conn.prepareStatement("alter table suppliers auto_increment = 1");
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Fill in categories
        try (Stream<String> lines = Files.lines(Paths.get("src/main/resources/categories.csv"))) {
            List<Category> categories = lines.map(
                    line -> new Category(replaceQuotes(line.split(",")[1])))
                    .collect(Collectors.toList());
            categoryRepository.saveAll(categories);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Fill in suppliers
        try (Stream<String> lines = Files.lines(Paths.get("src/main/resources/suppliers.csv"))) {
            List<Supplier> suppliers = lines.map(
                    line -> new Supplier(replaceQuotes(line.split(",",2)[1])))
                    .collect(Collectors.toList());
            supplierRepository.saveAll(suppliers);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Fill in products
        try (Stream<String> lines = Files.lines(Paths.get("src/main/resources/products.csv"))) {
            List<Product> products = lines.map(this::csvToProduct)
                    .collect(Collectors.toList());
            productRepository.saveAll(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String replaceQuotes(String name) {
        return name.replaceAll("\"","");
    }

    private Product csvToProduct(String line) {
        String[] values = line.split(",");
        int categoryId = Integer.parseInt(values[2]);
        int supplierId = Integer.parseInt(values[6]);

        Product p = new Product();
        p.setName(replaceQuotes(values[1]));
        p.setFullPrice(Double.parseDouble(values[3]));
        p.setSalePrice(Double.parseDouble(values[4]));
        p.setAvailability(Boolean.parseBoolean(values[5]));

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        optionalCategory.ifPresent(p::setCategory);

        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierId);
        optionalSupplier.ifPresent(p::setSupplier);

        return p;
    }
}
