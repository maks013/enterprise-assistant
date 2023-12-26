package com.enterpriseassistant.service.domain;

import com.enterpriseassistant.service.dto.AddServiceDto;
import com.enterpriseassistant.service.dto.ServiceDto;
import com.enterpriseassistant.service.dto.UpdateServiceDto;
import com.enterpriseassistant.service.exception.ServiceAlreadyExists;
import com.enterpriseassistant.service.exception.ServiceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ServiceFacade {

    private final ServiceRepository repository;
    private final ServiceMapper serviceMapper;

    public List<ServiceDto> findAllServices() {
        return repository.findAll()
                .stream()
                .map(Service::toDto)
                .collect(Collectors.toList());
    }

    public ServiceDto addNewService(AddServiceDto addServiceDto) {

        existsByName(addServiceDto.getName());

        Service service = serviceMapper.fromAddDto(addServiceDto);
        return repository.save(service).toDto();
    }

    public ServiceDto getServiceById(Integer id) {
        return getService(id).toDto();
    }

    public ServiceDto getServiceByName(String name) {
        return repository.findByName(name)
                .orElseThrow(ServiceNotFound::new)
                .toDto();
    }

    public void deleteService(Integer id) {
        final Service service = getService(id);
        repository.delete(service);
    }

    public ServiceDto updateService(UpdateServiceDto updateServiceDto, int id) {
        Service updatedService = serviceMapper.toUpdate(getService(id), updateServiceDto);
        Service savedService = repository.save(updatedService);

        return savedService.toDto();
    }

    private Service getService(Integer id) {
        return repository.findById(id)
                .orElseThrow(ServiceNotFound::new);
    }

    private void existsByName(String name) {
        if (repository.existsByName(name)) {
            throw new ServiceAlreadyExists();
        }
    }

}
