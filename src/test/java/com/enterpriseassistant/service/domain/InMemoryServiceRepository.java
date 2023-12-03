package com.enterpriseassistant.service.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryServiceRepository implements ServiceRepository {

    Map<Integer, Service> inMemoryServices = new ConcurrentHashMap<>();

    private static final Service service1 = new Service(1, "Delivery",
            BigDecimal.valueOf(123), BigDecimal.valueOf(100),"https://example.com/delivery-image.jpg" ,"");
    private static final Service service2 = new Service(1, "Graphic design",
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
    public List<Service> findAll() {
        return inMemoryServices.values().stream().toList();
    }

    @Override
    public boolean existsByName(String name) {
        return inMemoryServices.values()
                .stream()
                .anyMatch(service -> service.getName().equals(name));
    }

    @Override
    public void delete(Service product) {
        inMemoryServices.remove(product.getId());
    }
}
