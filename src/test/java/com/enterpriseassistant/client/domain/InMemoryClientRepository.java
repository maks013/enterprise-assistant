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

class InMemoryClientRepository implements ClientRepository{

    Map<Integer, Client> inMemoryClients = new ConcurrentHashMap<>();

    Client client1 = new Client(1,"8010427611", "First Company",
            new Address(1,"12-345","Warsaw","Chmielna 100",1),
            new Representative(1,"Ethan","Smith","+48 232 465 786","ethan.smith@example.com",1));
    Client client2 = new Client(2,"2334567899", "Second Company",
            new Address(2,"33-111","Cracow","Jagiellońska 100",2),
            new Representative(2,"Olivia","Johnson","+48 123 456 789","oliwia.johnson@example.com",2));

    public InMemoryClientRepository(){
        inMemoryClients.put(1, client1);
        inMemoryClients.put(2, client2);
    }

    @Override
    public Client save(Client client) {
        final int newClientId = 3;
        client.setId(newClientId);
        inMemoryClients.put(newClientId, client);
        return inMemoryClients.get(newClientId);
    }

    @Override
    public boolean existsByTaxIdNumber(String taxIdNumber) {
        return inMemoryClients.values()
                .stream()
                .anyMatch(product -> product.getTaxIdNumber().equals(taxIdNumber));
    }

    @Override
    public Optional<Client> findByCompanyName(String companyName) {
        return inMemoryClients.values()
                .stream()
                .filter(product -> product.getCompanyName().equals(companyName))
                .findFirst();
    }

    @Override
    public Optional<Client> findById(Integer id) {
        return Optional.ofNullable(inMemoryClients.get(id));
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Client> findAll() {
        return inMemoryClients.values().stream().toList();
    }

    @Override
    public List<Client> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Client> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Client> findAllById(Iterable<Integer> integers) {
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
    public <S extends Client> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Client> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Client> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Client> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Client getOne(Integer integer) {
        return null;
    }

    @Override
    public Client getById(Integer integer) {
        return null;
    }

    @Override
    public Client getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Client> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Client> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Client> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Client> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Client> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Client> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Client, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public void delete(Client client) {
        inMemoryClients.remove(client.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Client> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
