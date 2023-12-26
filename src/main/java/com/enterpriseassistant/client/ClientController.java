package com.enterpriseassistant.client;

import com.enterpriseassistant.client.domain.ClientFacade;
import com.enterpriseassistant.client.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientFacade clientFacade;

    @PostMapping
    public ResponseEntity<ClientDto> addNewClient(@Valid @RequestBody AddClientDto addClientDto) {
        final ClientDto clientDto = clientFacade.addNewClient(addClientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientDto);
    }

    @PutMapping("/client/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable int id, @Valid @RequestBody UpdateClientDto updateClientDto) {
        final ClientDto clientDto = clientFacade.updateClient(updateClientDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(clientDto);
    }

    @PutMapping("/representative/{id}")
    public ResponseEntity<RepresentativeDto> updateRepresentative(@PathVariable int id, @Valid @RequestBody UpdateRepresentativeDto updateRepresentativeDto) {
        final RepresentativeDto representativeDto = clientFacade.updateRepresentative(updateRepresentativeDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(representativeDto);
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable int id, @Valid @RequestBody UpdateAddressDto updateAddressDto) {
        final AddressDto addressDto = clientFacade.updateAddress(updateAddressDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(addressDto);
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> findAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(clientFacade.findAllClients());
    }

    @GetMapping("/byName/{companyName}")
    public ResponseEntity<ClientDto> findClient(@PathVariable String companyName) {
        return ResponseEntity.status(HttpStatus.OK).body(clientFacade.getClientByCompanyName((companyName)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Integer id) {
        clientFacade.deleteClientById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
