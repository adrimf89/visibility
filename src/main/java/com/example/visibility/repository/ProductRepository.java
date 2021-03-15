package com.example.visibility.repository;

import com.example.visibility.model.Product;
import com.example.visibility.util.CsvUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
@Slf4j
public class ProductRepository {

    private Set<Product> productList;

    @PostConstruct
    private void init(){
        try {
            Path path = Paths.get(ClassLoader.getSystemResource("db/product.csv").toURI());
            productList = new TreeSet<>((List<Product>) CsvUtils.parseCsvToBean(path, Product.class));
        } catch (Exception e) {
            log.error("Error loading products", e);
        }
    }

    public Set<Product> findAll(){
        return productList;
    }


}
