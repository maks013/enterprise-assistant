package com.enterpriseassistant.client.domain;

import com.enterpriseassistant.client.dto.AddClientDto;
import com.enterpriseassistant.client.dto.AddressDto;
import com.enterpriseassistant.client.dto.ClientDto;
import com.enterpriseassistant.client.dto.RepresentativeDto;
import com.enterpriseassistant.client.exception.ClientNotFound;
import com.enterpriseassistant.client.exception.InvalidPostalFormat;
import com.enterpriseassistant.client.exception.InvalidTaxIdFormat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientFacadeTest {

    ClientFacade clientFacade = new ClientFacade(new InMemoryAddressRepository(),
            new InMemoryRepresentativeRepository(),
            new InMemoryClientRepository(),
            new ClientMapper());

    @Test
    void should_findAll_offers_with_size_2() {
        //given
        final int size;
        //when
        size = clientFacade.findAllClients().size();
        //then
        assertEquals(2, size);
    }

    @Test
    void should_throw_exception_when_get_client_by_not_existing_company_name() {
        //given
        final String companyName = "Not Existing Company Name Example";
        //when
        //then
        assertThrows(ClientNotFound.class, () -> clientFacade.getClientByCompanyName(companyName),
                "Client not found");
    }

    @Test
    void should_get_client_by_company_name() {
        //given
        final String companyName = "First Company";
        //when
        ClientDto clientDto = clientFacade.getClientByCompanyName(companyName);
        //then
        assertAll(
                () -> assertEquals(1, clientDto.getId()),
                () -> assertEquals(companyName, clientDto.getCompanyName()),
                () -> assertEquals("12-345", clientDto.getAddress().getPostalCode()),
                () -> assertEquals("Ethan", clientDto.getRepresentative().getName())
        );
    }

    @Test
    void should_get_client_by_company_id() {
        //given
        final Integer companyId = 1;
        //when
        ClientDto clientDto = clientFacade.getClientById(companyId);
        //then
        assertAll(
                () -> assertEquals(1, clientDto.getId()),
                () -> assertEquals("First Company", clientDto.getCompanyName()),
                () -> assertEquals("12-345", clientDto.getAddress().getPostalCode()),
                () -> assertEquals("Ethan", clientDto.getRepresentative().getName())
        );
    }

    @Test
    void should_delete_client_by_id() {
        //given
        //when
        final int sizeBeforeDelete = clientFacade.findAllClients().size();
        clientFacade.deleteClientById(1);
        final int sizeAfterDelete = clientFacade.findAllClients().size();
        //then
        assertEquals(1, sizeBeforeDelete - sizeAfterDelete);
    }

    @Test
    void should_throw_exception_when_delete_client_by_not_existing_id() {
        //given
        //when
        //then
        assertThrows(ClientNotFound.class, () -> clientFacade.deleteClientById(999),
                "Client not found");
    }

    @Test
    void should_add_new_client() {
        //given
        AddClientDto newClient = new AddClientDto("9762583826", "Added Company",
                new AddressDto("99-999", "Example", "Example 15a"),
                new RepresentativeDto("Smith", "Example", "+123456789", "example@example.com"));
        //when
        final int sizeBeforeAdd = clientFacade.findAllClients().size();
        ClientDto clientDto = clientFacade.addNewClient(newClient);
        final int sizeAfterAdd = clientFacade.findAllClients().size();
        //then
        assertAll(
                () -> assertEquals(1, sizeAfterAdd - sizeBeforeAdd),
                () -> assertEquals(newClient.getTaxIdNumber(), clientDto.getTaxIdNumber()),
                () -> assertEquals(newClient.getCompanyName(), clientDto.getCompanyName()),
                () -> assertEquals(newClient.getAddress().getStreet(), clientDto.getAddress().getStreet()),
                () -> assertEquals(newClient.getRepresentative().getSurname(), clientDto.getRepresentative().getSurname())
        );
    }

    @Test
    void should_throw_exception_when_adding_new_client_with_invalid_tax_id_number() {
        //given
        AddClientDto newClient = new AddClientDto("11", "Added Company",
                new AddressDto("99-999", "Example", "Example 15a"),
                new RepresentativeDto("Smith", "Example", "+123456789", "example@example.com"));
        //when
        //then
        assertThrows(InvalidTaxIdFormat.class, () -> clientFacade.addNewClient(newClient),
                "Invalid format of tax id number");
    }

    @Test
    void should_throw_exception_when_adding_new_client_with_invalid_postal_code() {
        //given
        AddClientDto newClient = new AddClientDto("9762583826", "Added Company",
                new AddressDto("999-99", "Example", "Example 15a"),
                new RepresentativeDto("Smith", "Example", "+123456789", "example@example.com"));
        //when
        //then
        assertThrows(InvalidPostalFormat.class, () -> clientFacade.addNewClient(newClient),
                "Invalid format of postal code");
    }

}
