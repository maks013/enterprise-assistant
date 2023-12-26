package com.enterpriseassistant.client.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface ClientRepository extends JpaRepository<Client, Integer> {

    boolean existsByTaxIdNumber(String taxIdNumber);

    Optional<Client> findByCompanyName(String companyName);
}
