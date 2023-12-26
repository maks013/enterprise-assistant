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

class InMemoryRepresentativeRepository implements RepresentativeRepository {

    Map<Integer, Representative> inMemoryRepresentative = new ConcurrentHashMap<>();

    Representative representative1 = new Representative(1, "Ethan", "Smith", "+48 232 465 786", "ethan.smith@example.com", 1);
    Representative representative2 = new Representative(2, "Olivia", "Johnson", "+48 123 456 789", "oliwia.johnson@example.com", 2);

    InMemoryRepresentativeRepository() {
        inMemoryRepresentative.put(1, representative1);
        inMemoryRepresentative.put(2, representative2);
    }

    @Override
    public Representative save(Representative representative) {
        final int newRepresentative = 3;
        representative.setId(newRepresentative);
        inMemoryRepresentative.put(newRepresentative, representative);
        return inMemoryRepresentative.get(newRepresentative);
    }

    @Override
    public List<Representative> findAll() {
        return null;
    }

    @Override
    public List<Representative> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Representative> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Representative> findAllById(Iterable<Integer> integers) {
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
    public void delete(Representative entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Representative> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Representative> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Representative> findById(Integer integer) {
        return Optional.ofNullable(inMemoryRepresentative.get(integer));
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Representative> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Representative> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Representative> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Representative getOne(Integer integer) {
        return null;
    }

    @Override
    public Representative getById(Integer integer) {
        return null;
    }

    @Override
    public Representative getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Representative> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Representative> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Representative> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Representative> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Representative> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Representative> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Representative, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
