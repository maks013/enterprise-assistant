package com.enterpriseassistant.client.domain;

import com.enterpriseassistant.client.dto.AddClientDto;
import com.enterpriseassistant.client.dto.AddressDto;
import com.enterpriseassistant.client.dto.ClientDto;
import com.enterpriseassistant.client.dto.RepresentativeDto;
import com.enterpriseassistant.client.exception.ClientNotFound;
import com.enterpriseassistant.client.exception.ExistingTaxIdNumber;
import com.enterpriseassistant.client.exception.InvalidPostalFormat;
import com.enterpriseassistant.client.exception.InvalidTaxIdFormat;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ClientFacade {

    private final AddressRepository addressRepository;
    private final RepresentativeRepository representativeRepository;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public List<ClientDto> findAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(Client::toDto)
                .collect(Collectors.toList());
    }

    public ClientDto getClientById(Integer id) {
        return getClient(id).toDto();
    }

    public ClientDto getClientByCompanyName(String companyName) {
        return clientRepository.findByCompanyName(companyName)
                .map(Client::toDto)
                .orElseThrow(ClientNotFound::new);
    }

    public void deleteClientById(Integer id) {
        final Client client = getClient(id);
        clientRepository.delete(client);
    }

    public ClientDto addNewClient(AddClientDto addClientDto) {
        validateTaxIdNumber(addClientDto.getTaxIdNumber());
        validatePostalCode(addClientDto.getAddress().getPostalCode());

        Client client = clientMapper.fromAddDto(addClientDto);

        final Client savedClient = clientRepository.save(client);

        createAddress(addClientDto.getAddress(), savedClient.getId());
        createRepresentative(addClientDto.getRepresentative(), savedClient.getId());

        return savedClient.toDto();
    }

    private void createAddress(AddressDto addressDto, int clientId) {
        Address address = clientMapper.addressFromDto(addressDto);
        address.setClientId(clientId);
        addressRepository.save(address);
    }

    private void createRepresentative(RepresentativeDto representativeDto, int clientId) {
        Representative representative = clientMapper.representativeFromDto(representativeDto);
        representative.setClientId(clientId);
        representativeRepository.save(representative);
    }

    private void validateTaxIdNumber(String taxIdNumber) {
        if (!TaxIdNumberValidator.validateNIP(taxIdNumber)) {
            throw new InvalidTaxIdFormat();
        }
        if (clientRepository.existsByTaxIdNumber(taxIdNumber)) {
            throw new ExistingTaxIdNumber();
        }
    }

    private void validatePostalCode(String postalCode) {
        if (!AddressValidator.validatePostalCode(postalCode)) {
            throw new InvalidPostalFormat();
        }
    }

    private Client getClient(Integer id) {
        return clientRepository.findById(id)
                .orElseThrow(ClientNotFound::new);
    }
}
