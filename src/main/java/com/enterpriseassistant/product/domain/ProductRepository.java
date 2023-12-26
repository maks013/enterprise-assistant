package com.enterpriseassistant.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);

    Optional<Product> findByGtin(String gtin);

    boolean existsByGtin(String gtin);
}
