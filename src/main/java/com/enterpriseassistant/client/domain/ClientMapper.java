package com.enterpriseassistant.client.domain;

import com.enterpriseassistant.client.dto.*;
import org.springframework.stereotype.Component;

@Component
class ClientMapper {

    Client fromAddDto(AddClientDto addClientDto) {
        return Client.builder()
                .taxIdNumber(addClientDto.getTaxIdNumber())
                .companyName(addClientDto.getCompanyName())
                .build();
    }

    Address addressFromDto(AddressDto addressDto, int clientId) {
        return Address.builder()
                .postalCode(addressDto.getPostalCode())
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .clientId(clientId)
                .build();
    }

    Representative representativeFromDto(RepresentativeDto representativeDto, int clientId) {
        return Representative.builder()
                .name(representativeDto.getName())
                .surname(representativeDto.getSurname())
                .phoneNumber(representativeDto.getPhoneNumber())
                .email(representativeDto.getEmail())
                .clientId(clientId)
                .build();
    }

    Client toUpdate(Client client, UpdateClientDto updateClientDto) {
        client.setCompanyName(updateClientDto.getCompanyName().isEmpty() ?
                client.getCompanyName() : updateClientDto.getCompanyName());
        client.setTaxIdNumber(updateClientDto.getTaxIdNumber().isEmpty() ?
                client.getTaxIdNumber() : updateClientDto.getTaxIdNumber());
        return client;
    }

    Address toUpdate(Address address, UpdateAddressDto updateAddressDto) {
        address.setPostalCode(updateAddressDto.getPostalCode().isEmpty() ?
                address.getPostalCode() : updateAddressDto.getPostalCode());
        address.setCity(updateAddressDto.getCity().isEmpty() ?
                address.getCity() : updateAddressDto.getCity());
        address.setStreet(updateAddressDto.getStreet().isEmpty() ?
                address.getStreet() : updateAddressDto.getStreet());
        return address;
    }

    Representative toUpdate(Representative representative, UpdateRepresentativeDto updateRepresentativeDto){
        representative.setName(updateRepresentativeDto.getName().isEmpty() ?
                representative.getName() : updateRepresentativeDto.getName());
        representative.setSurname(updateRepresentativeDto.getSurname().isEmpty() ?
                representative.getSurname() : updateRepresentativeDto.getSurname());
        representative.setPhoneNumber(updateRepresentativeDto.getPhoneNumber().isEmpty() ?
                representative.getPhoneNumber() : updateRepresentativeDto.getPhoneNumber());
        representative.setEmail(updateRepresentativeDto.getEmail().isEmpty() ?
                representative.getEmail() : updateRepresentativeDto.getEmail());
        return representative;
    }
}
