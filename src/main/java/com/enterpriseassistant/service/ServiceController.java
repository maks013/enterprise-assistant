package com.enterpriseassistant.service;

import com.enterpriseassistant.service.domain.ServiceFacade;
import com.enterpriseassistant.service.dto.AddServiceDto;
import com.enterpriseassistant.service.dto.ServiceDto;
import com.enterpriseassistant.service.dto.UpdateServiceDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/services")
public class ServiceController {

    private final ServiceFacade serviceFacade;

    @PostMapping
    public ResponseEntity<ServiceDto> addNewService(@Valid @RequestBody AddServiceDto addServiceDto) {
        final ServiceDto serviceDto = serviceFacade.addNewService(addServiceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceDto> updateService(@PathVariable int id, @Valid @RequestBody UpdateServiceDto updateServiceDto) {
        final ServiceDto serviceDto = serviceFacade.updateService(updateServiceDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(serviceDto);
    }

    @GetMapping
    public ResponseEntity<List<ServiceDto>> findAllServices() {
        return ResponseEntity.status(HttpStatus.OK).body(serviceFacade.findAllServices());
    }

    @GetMapping("/name/{serviceName}")
    public ResponseEntity<ServiceDto> findService(@PathVariable String serviceName) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceFacade.getServiceByName((serviceName)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Integer id) {
        serviceFacade.deleteService(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
