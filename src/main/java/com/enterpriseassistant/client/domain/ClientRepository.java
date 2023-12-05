package com.enterpriseassistant.client.domain;

import java.util.List;
import java.util.Optional;

interface ClientRepository {

    Client save(Client client);

    boolean existsByTaxIdNumber(String taxIdNumber);

    Optional<Client> findByCompanyName(String companyName);

    Optional<Client> findById(Integer id);

    List<Client> findAll();

    void delete(Client client);
}
