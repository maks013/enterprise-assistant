package com.enterpriseassistant.service.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ServiceRepository extends JpaRepository<Service, Integer> {

    Optional<Service> findByName(String name);

    boolean existsByName(String name);
}
