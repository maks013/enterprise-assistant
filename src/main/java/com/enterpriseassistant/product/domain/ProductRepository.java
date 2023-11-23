package com.enterpriseassistant.product.domain;

import java.util.List;
import java.util.Optional;

interface ProductRepository {

    Product save(Product product);

    Optional<Product> findByName(String name);

    Optional<Product> findByGtin(String gtin);

    Optional<Product> findById(Integer id);

    List<Product> findAll();

    boolean existsByGtin(String gtin);

    void delete(Product product);

}
