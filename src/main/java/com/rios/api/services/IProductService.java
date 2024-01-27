package com.rios.api.services;

import com.rios.api.entities.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> findAll();
    Optional<Product> findById(Long id);

    Iterable<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice);
    void save(Product product);

    void deleteById(Long id);
}
