package com.enterpriseassistant.service;

import com.enterpriseassistant.service.domain.ServiceFacade;
import com.enterpriseassistant.service.dto.AddServiceDto;
import com.enterpriseassistant.service.dto.ServiceDto;
import com.enterpriseassistant.service.dto.UpdateServiceDto;
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
@RequestMapping("/services")
@Api(value = "Service Controller", description = "Moduł Api odpowiadający za usługi")
public class ServiceController {

    private final ServiceFacade serviceFacade;

    @PostMapping
    @ApiOperation(value = "Tworzy nową usługę")
    public ResponseEntity<ServiceDto> addNewService(@Valid @RequestBody AddServiceDto addServiceDto) {
        final ServiceDto serviceDto = serviceFacade.addNewService(addServiceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Zastępuje dane usługi")
    public ResponseEntity<ServiceDto> updateService(@PathVariable int id, @Valid @RequestBody UpdateServiceDto updateServiceDto) {
        final ServiceDto serviceDto = serviceFacade.updateService(updateServiceDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(serviceDto);
    }

    @GetMapping
    @ApiOperation(value = "Pobiera wszystkie usługi")
    public ResponseEntity<List<ServiceDto>> findAllServices() {
        return ResponseEntity.status(HttpStatus.OK).body(serviceFacade.findAllServices());
    }

    @GetMapping("/name/{serviceName}")
    @ApiOperation(value = "Pobiera usługę po nazwie")
    public ResponseEntity<ServiceDto> findService(@PathVariable String serviceName) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceFacade.getServiceByName((serviceName)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Usuwa usługę")
    public ResponseEntity<?> deleteService(@PathVariable Integer id) {
        serviceFacade.deleteService(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
