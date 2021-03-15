package com.example.visibility.repository;

import com.example.visibility.model.Size;
import com.example.visibility.util.CsvUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
@Slf4j
public class SizeRepository {

    private Map<Integer, List<Size>> sizesByProduct;

    @PostConstruct
    private void init(){
        try {
            Path path = Paths.get(ClassLoader.getSystemResource("db/size.csv").toURI());
            List<Size> sizes = (List<Size>) CsvUtils.parseCsvToBean(path, Size.class);

            sizesByProduct = sizes
                    .stream()
                    .collect(groupingBy(Size::getProductId));

        } catch (Exception e) {
            log.error("Error loading sizes", e);
        }
    }

    public List<Size> findSizesByProductId(int productId){
        return sizesByProduct.getOrDefault(productId, new ArrayList<>());
    }
}
