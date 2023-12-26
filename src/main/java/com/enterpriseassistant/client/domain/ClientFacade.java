package com.enterpriseassistant.client.domain;

import com.enterpriseassistant.client.dto.*;
import com.enterpriseassistant.client.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
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
        final Address address = getAddress(id);
        final Representative representative = getRepresentative(id);

        addressRepository.delete(address);
        representativeRepository.delete(representative);
        clientRepository.delete(client);
    }

    public ClientDto addNewClient(AddClientDto addClientDto) {
        validateTaxIdNumber(addClientDto.getTaxIdNumber());
        validatePostalCode(addClientDto.getAddress().getPostalCode());

        Client client = clientRepository.save(clientMapper.fromAddDto(addClientDto));

        client.setAddress(createAddress(addClientDto.getAddress(), client.getId()));
        client.setRepresentative(createRepresentative(addClientDto.getRepresentative(), client.getId()));

        return clientRepository.save(client).toDto();
    }

    public ClientDto updateClient(UpdateClientDto updateClientDto, int id) {
        if (!updateClientDto.getTaxIdNumber().isEmpty()) {
            validateTaxIdNumber(updateClientDto.getTaxIdNumber());
        }

        Client updatedClient = clientMapper.toUpdate(getClient(id), updateClientDto);

        return clientRepository.save(updatedClient).toDto();
    }

    public AddressDto updateAddress(UpdateAddressDto updateAddressDto, int id) {
        if (!updateAddressDto.getPostalCode().isEmpty()) {
            validatePostalCode(updateAddressDto.getPostalCode());
        }

        Address updatedAddress = clientMapper.toUpdate(getAddress(id), updateAddressDto);

        return addressRepository.save(updatedAddress).toDto();
    }

    public RepresentativeDto updateRepresentative(UpdateRepresentativeDto updateRepresentativeDto, int id) {
        Representative updatedRepresentative = clientMapper.toUpdate(getRepresentative(id), updateRepresentativeDto);

        return representativeRepository.save(updatedRepresentative).toDto();
    }

    private Address createAddress(AddressDto addressDto, int clientId) {
        Address address = clientMapper.addressFromDto(addressDto, clientId);
        return addressRepository.save(address);
    }

    private Representative createRepresentative(RepresentativeDto representativeDto, int clientId) {
        Representative representative = clientMapper.representativeFromDto(representativeDto, clientId);
        return representativeRepository.save(representative);
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

    private Address getAddress(Integer id) {
        return addressRepository.findById(id)
                .orElseThrow(AddressNotFound::new);
    }

    private Representative getRepresentative(Integer id) {
        return representativeRepository.findById(id)
                .orElseThrow(RepresentativeNotFound::new);
    }
}
