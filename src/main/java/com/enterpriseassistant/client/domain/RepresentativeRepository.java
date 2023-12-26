package com.enterpriseassistant.client.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RepresentativeRepository extends JpaRepository<Representative, Integer> {
}
