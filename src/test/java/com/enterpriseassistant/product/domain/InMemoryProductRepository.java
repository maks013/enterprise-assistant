package com.enterpriseassistant.product.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryProductRepository implements ProductRepository {

    Map<Integer, Product> inMemoryProducts = new ConcurrentHashMap<>();

    private static final Product product1 = new Product(1, "0123456789012", "Backpack",
            BigDecimal.valueOf(123), BigDecimal.valueOf(100),"https://example.com/backpack-image.jpg" ,"Black");
    private static final Product product2 = new Product(1, "0123456789013", "Shoulder bag",
            BigDecimal.valueOf(123), BigDecimal.valueOf(100),"https://example.com/shoulder-bag-image.jpg" ,"Small, blue");

    public InMemoryProductRepository() {
        inMemoryProducts.put(1, product1);
        inMemoryProducts.put(2, product2);
    }

    @Override
    public Product save(Product product) {
        final int newProductId = 3;
        product.setId(newProductId);
        inMemoryProducts.put(newProductId, product);
        return inMemoryProducts.get(newProductId);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return inMemoryProducts.values()
                .stream()
                .filter(product -> product.getName().equals(name))
                .findFirst();
    }

    @Override
    public Optional<Product> findByGtin(String gtin) {
        return inMemoryProducts.values()
                .stream()
                .filter(product -> product.getGtin().equals(gtin))
                .findFirst();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return Optional.ofNullable(inMemoryProducts.get(id));
    }

    @Override
    public List<Product> findAll() {
        return inMemoryProducts.values().stream().toList();
    }

    @Override
    public boolean existsByGtin(String gtin) {
        return inMemoryProducts.values()
                .stream()
                .anyMatch(product -> product.getGtin().equals(gtin));
    }

    @Override
    public void delete(Product product) {
        inMemoryProducts.remove(product.getId());
    }
}
