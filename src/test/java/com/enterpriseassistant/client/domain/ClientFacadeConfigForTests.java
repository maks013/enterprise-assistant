package com.enterpriseassistant.client.domain;

public class ClientFacadeConfigForTests {

    public ClientFacade clientFacade() {
        return new ClientFacade(new InMemoryAddressRepository(),
                new InMemoryRepresentativeRepository(),
                new InMemoryClientRepository(),
                new ClientMapper());
    }

}
