package com.enterpriseassistant.client.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryRepresentativeRepository implements RepresentativeRepository{

    Map<Integer, Representative> inMemoryRepresentative = new ConcurrentHashMap<>();
    private Integer id = 0;

    @Override
    public Representative save(Representative representative) {
        id++;
        inMemoryRepresentative.put(id, representative);
        return inMemoryRepresentative.get(id);
    }
}
