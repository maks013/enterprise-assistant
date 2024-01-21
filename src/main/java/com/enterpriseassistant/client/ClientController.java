package com.enterpriseassistant.client;

import com.enterpriseassistant.client.domain.ClientFacade;
import com.enterpriseassistant.client.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/clients")
@Api(value = "Client Controller", description = "Moduł Api odpowiadający za klientów")
public class ClientController {

    private final ClientFacade clientFacade;

    @PostMapping
    @ApiOperation(value = "Tworzy nowego klienta")
    public ResponseEntity<ClientDto> addNewClient(@Valid @RequestBody AddClientDto addClientDto) {
        final ClientDto clientDto = clientFacade.addNewClient(addClientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientDto);
    }

    @PutMapping("/client/{id}")
    @ApiOperation(value = "Zastępuje dane klienta")
    public ResponseEntity<ClientDto> updateClient(@PathVariable int id, @Valid @RequestBody UpdateClientDto updateClientDto) {
        final ClientDto clientDto = clientFacade.updateClient(updateClientDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(clientDto);
    }

    @PutMapping("/representative/{id}")
    @ApiOperation(value = "Zastępuje dane osoby reprezentującej firmę klienta")
    public ResponseEntity<RepresentativeDto> updateRepresentative(@PathVariable int id, @Valid @RequestBody UpdateRepresentativeDto updateRepresentativeDto) {
        final RepresentativeDto representativeDto = clientFacade.updateRepresentative(updateRepresentativeDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(representativeDto);
    }

    @PutMapping("/address/{id}")
    @ApiOperation(value = "Zastępuje adres klienta")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable int id, @Valid @RequestBody UpdateAddressDto updateAddressDto) {
        final AddressDto addressDto = clientFacade.updateAddress(updateAddressDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(addressDto);
    }

    @GetMapping
    @ApiOperation(value = "Pobiera wszystkich klientów")
    public ResponseEntity<List<ClientDto>> findAllClients() {
        return ResponseEntity.status(HttpStatus.OK).body(clientFacade.findAllClients());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Pobiera klienta po identyfikatorze")
    public ResponseEntity<ClientDto> findClientById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(clientFacade.getClientById(id));
    }

    @GetMapping("/byName/{companyName}")
    @ApiOperation(value = "Pobiera klienta po nazwie firmy")
    public ResponseEntity<ClientDto> findClient(@PathVariable String companyName) {
        return ResponseEntity.status(HttpStatus.OK).body(clientFacade.getClientByCompanyName((companyName)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Usuwa klienta")
    public ResponseEntity<?> deleteClient(@PathVariable Integer id) {
        clientFacade.deleteClientById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
