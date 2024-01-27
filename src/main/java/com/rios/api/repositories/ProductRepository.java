package com.rios.api.repositories;

import com.rios.api.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Iterable<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    Iterable<Product> findProductByPrice(BigDecimal minPrice, BigDecimal maxPrice);
}
