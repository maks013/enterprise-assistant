package com.enterpriseassistant.service.domain;

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

public class InMemoryServiceRepository implements ServiceRepository {

    Map<Integer, Service> inMemoryServices = new ConcurrentHashMap<>();

    Service service1 = new Service(1, "Delivery",
            BigDecimal.valueOf(123), BigDecimal.valueOf(100),"https://example.com/delivery-image.jpg" ,"");
    Service service2 = new Service(2, "Graphic design",
            BigDecimal.valueOf(123), BigDecimal.valueOf(100),"https://example.com/graphic-image.jpg", "Logo");

    public InMemoryServiceRepository() {
        inMemoryServices.put(1, service1);
        inMemoryServices.put(2, service2);
    }

    @Override
    public Service save(Service service) {
        final int newServiceId = 3;
        service.setId(newServiceId);
        inMemoryServices.put(newServiceId, service);
        return inMemoryServices.get(newServiceId);
    }

    @Override
    public Optional<Service> findByName(String name) {
        return inMemoryServices.values()
                .stream()
                .filter(service -> service.getName().equals(name))
                .findFirst();
    }

    @Override
    public Optional<Service> findById(Integer id) {
        return Optional.ofNullable(inMemoryServices.get(id));
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Service> findAll() {
        return inMemoryServices.values().stream().toList();
    }

    @Override
    public List<Service> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Service> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Service> findAllById(Iterable<Integer> integers) {
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
    public void delete(Service entity) {
        inMemoryServices.remove(entity.getId());
    }

    @Override
    public <S extends Service> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Service> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Service> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Service> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Service getOne(Integer integer) {
        return null;
    }

    @Override
    public Service getById(Integer integer) {
        return null;
    }

    @Override
    public Service getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Service> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Service> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Service> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Service> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Service> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Service> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Service, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public boolean existsByName(String name) {
        return inMemoryServices.values()
                .stream()
                .anyMatch(service -> service.getName().equals(name));
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Service> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
