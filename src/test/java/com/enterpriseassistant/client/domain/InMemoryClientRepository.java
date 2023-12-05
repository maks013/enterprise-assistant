package com.enterpriseassistant.client.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryClientRepository implements ClientRepository{

    Map<Integer, Client> inMemoryClients = new ConcurrentHashMap<>();

    private static final Client client1 = new Client(1,"8010427611", "First Company",
            new Address(1,"12-345","Warsaw","Chmielna 100",1),
            new Representative(1,"Ethan","Smith","+48 232 465 786","ethan.smith@example.com",1));
    private static final Client client2 = new Client(2,"2334567899", "Second Company",
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
    public List<Client> findAll() {
        return inMemoryClients.values().stream().toList();
    }

    @Override
    public void delete(Client client) {
        inMemoryClients.remove(client.getId());
    }
}
