package com.example.visibility.repository;

import com.example.visibility.model.Stock;
import com.example.visibility.util.CsvUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StockRepository {

    private Map<Integer, Integer> stock;

    @PostConstruct
    private void init(){
        try {
            Path path = Paths.get(ClassLoader.getSystemResource("db/stock.csv").toURI());
            List<Stock> stockList = (List<Stock>) CsvUtils.parseCsvToBean(path, Stock.class);

            stock = stockList
                    .stream()
                    .collect(Collectors.toMap(Stock::getSizeId, Stock::getQuantity));
        } catch (Exception e) {
            log.error("Error loading sizes", e);
        }
    }

    public Integer findStockBySizeId(int sizeId){
        return stock.getOrDefault(sizeId, 0);
    }


}
