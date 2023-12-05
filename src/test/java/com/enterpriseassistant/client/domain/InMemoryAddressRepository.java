package com.enterpriseassistant.client.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryAddressRepository implements AddressRepository{

    Map<Integer, Address> inMemoryAddresses = new ConcurrentHashMap<>();
    private Integer id = 0;

    @Override
    public Address save(Address address) {
        id++;
        inMemoryAddresses.put(id, address);
        return inMemoryAddresses.get(id);
    }

}
