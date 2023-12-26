package com.enterpriseassistant.client.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class InMemoryAddressRepository implements AddressRepository {

    Map<Integer, Address> inMemoryAddresses = new ConcurrentHashMap<>();

    Address address1 = new Address(1, "12-345", "Warsaw", "Chmielna 100", 1);
    Address address2 = new Address(2, "33-111", "Cracow", "Jagiellońska 100", 2);

    InMemoryAddressRepository() {
        inMemoryAddresses.put(1, address1);
        inMemoryAddresses.put(2, address2);
    }

    @Override
    public Address save(Address address) {
        final int newAddressId = 3;
        address.setId(newAddressId);
        inMemoryAddresses.put(newAddressId, address);
        return inMemoryAddresses.get(newAddressId);
    }

    @Override
    public List<Address> findAll() {
        return null;
    }

    @Override
    public List<Address> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Address> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Address> findAllById(Iterable<Integer> integers) {
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
    public void delete(Address entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Address> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Address> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Address> findById(Integer integer) {
        return Optional.ofNullable(inMemoryAddresses.get(integer));
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Address> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Address> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Address> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Address getOne(Integer integer) {
        return null;
    }

    @Override
    public Address getById(Integer integer) {
        return null;
    }

    @Override
    public Address getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Address> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Address> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Address> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Address> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Address> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Address> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Address, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
