package com.enterpriseassistant.service.domain;

import java.util.List;
import java.util.Optional;

interface ServiceRepository {

    Service save(Service service);

    Optional<Service> findByName(String name);

    Optional<Service> findById(Integer id);

    List<Service> findAll();

    boolean existsByName(String name);

    void delete(Service service);

}
