package com.example.visibility.service;

import com.example.visibility.model.Product;
import com.example.visibility.model.Size;
import com.example.visibility.repository.ProductRepository;
import com.example.visibility.repository.SizeRepository;
import com.example.visibility.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class VisibilityService {

    private final ProductRepository productRepository;

    private final SizeRepository sizeRepository;

    private final StockRepository stockRepository;

    public List<Integer> getVisibleProducts(){
        return productRepository.findAll()
                .stream()
                .filter(product -> isProductVisible(product.getId()))
                .map(Product::getId)
                .collect(Collectors.toList());
    }

    private boolean isProductVisible(int productId){
        List<Size> normalSizes = new ArrayList<>();
        List<Size> specialSizes = new ArrayList<>();

        sizeRepository.findSizesByProductId(productId).forEach(size -> {
            if (size.isSpecial() && (size.isBackSoon() || stockRepository.findStockBySizeId(size.getId()) > 0)){
                specialSizes.add(size);
            } else if (size.isBackSoon() || stockRepository.findStockBySizeId(size.getId()) > 0) {
                normalSizes.add(size);
            }
        });

        return (specialSizes.isEmpty() && !normalSizes.isEmpty())
                || (!specialSizes.isEmpty() && !normalSizes.isEmpty());
    }
}
