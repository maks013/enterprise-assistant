package com.enterpriseassistant.product.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class InMemoryProductRepository implements ProductRepository {

    Map<Integer, Product> inMemoryProducts = new ConcurrentHashMap<>();

    Product product1 = new Product(1, "0123456789012", "Backpack",
            BigDecimal.valueOf(123), BigDecimal.valueOf(100),"https://example.com/backpack-image.jpg" ,"Black");
    Product product2 = new Product(1, "0123456789013", "Shoulder bag",
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
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Product> findAll() {
        return inMemoryProducts.values().stream().toList();
    }

    @Override
    public List<Product> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Product> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Product entity) {
        inMemoryProducts.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Product> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Product> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Product> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Product> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Product> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Product getOne(Integer integer) {
        return null;
    }

    @Override
    public Product getById(Integer integer) {
        return null;
    }

    @Override
    public Product getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Product> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Product> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Product> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Product> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Product> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Product> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Product, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public boolean existsByGtin(String gtin) {
        return inMemoryProducts.values()
                .stream()
                .anyMatch(product -> product.getGtin().equals(gtin));
    }

}
