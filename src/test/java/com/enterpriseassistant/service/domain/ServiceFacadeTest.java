package com.enterpriseassistant.service.domain;

import com.enterpriseassistant.product.dto.AddProductDto;
import com.enterpriseassistant.product.dto.ProductDto;
import com.enterpriseassistant.product.exception.ProductNotFound;
import com.enterpriseassistant.service.dto.AddServiceDto;
import com.enterpriseassistant.service.dto.ServiceDto;
import com.enterpriseassistant.service.exception.ServiceAlreadyExists;
import com.enterpriseassistant.service.exception.ServiceNotFound;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ServiceFacadeTest {

    private final ServiceFacade serviceFacade = new ServiceFacade(new InMemoryServiceRepository(), new ServiceMapper());

    @Test
    void should_find_all_services_and_size_should_equals_2() {
        //given
        final int size = serviceFacade.findAllServices().size();
        //when
        //then
        assertEquals(2, size);
    }

    @Test
    void should_add_service_properly() {
        //given
        AddServiceDto service = AddServiceDto.builder()
                .name("New service")
                .priceNet(BigDecimal.valueOf(100))
                .imageUrl("https://example.com/service-image.jpg")
                .additionalInformation("information")
                .build();
        //when
        final int sizeBeforeAddingService = serviceFacade.findAllServices().size();
        ServiceDto addedService = serviceFacade.addNewService(service);
        final int sizeAfterAddingService = serviceFacade.findAllServices().size();
        //then
        assertAll(
                () -> assertEquals(1, sizeAfterAddingService - sizeBeforeAddingService),
                () -> assertEquals(service.getName(), addedService.getName()),
                () -> assertEquals(service.getPriceNet().setScale(2), addedService.getPriceNet()),
                () -> assertEquals(BigDecimal.valueOf(123).setScale(2), addedService.getPriceGross()),
                () -> assertEquals(service.getImageUrl(), addedService.getImageUrl()),
                () -> assertEquals(service.getAdditionalInformation(), addedService.getAdditionalInformation())
        );
    }

    @Test
    void should_throw_exception_when_adding_service_with_taken_name() {
        //given
        AddServiceDto service = AddServiceDto.builder()
                .name("New service")
                .priceNet(BigDecimal.valueOf(100))
                .imageUrl("https://example.com/service-image.jpg")
                .additionalInformation("information")
                .build();
        AddServiceDto serviceWithTheSameName = AddServiceDto.builder()
                .name("New service")
                .priceNet(BigDecimal.valueOf(100))
                .additionalInformation("information")
                .build();
        //when
        serviceFacade.addNewService(service);
        //then
        assertThrows(ServiceAlreadyExists.class, () -> serviceFacade.addNewService(serviceWithTheSameName),
                "Service with this name already exists");
    }

    @Test
    void should_throw_exception_when_get_product_by_not_existing_name() {
        //given
        final String notExistingNameOfService = "notExistingNameOfService";
        //when
        //then
        assertThrows(ServiceNotFound.class, () -> serviceFacade.getServiceByName(notExistingNameOfService),
                "Service not found");
    }

    @Test
    void should_get_service_by_name() {
        //given
        final String name = "New service";
        AddServiceDto service = AddServiceDto.builder()
                .name(name)
                .priceNet(BigDecimal.valueOf(100))
                .imageUrl("https://example.com/service-image.jpg")
                .additionalInformation("information")
                .build();
        //when
        ServiceDto addedService = serviceFacade.addNewService(service);
        ServiceDto serviceDto = serviceFacade.getServiceByName(name);
        //then
        assertAll(
                () -> assertEquals(addedService.getName(), serviceDto.getName()),
                () -> assertEquals(addedService.getPriceNet(), serviceDto.getPriceNet()),
                () -> assertEquals(addedService.getPriceGross(), serviceDto.getPriceGross()),
                () -> assertEquals(addedService.getImageUrl(), serviceDto.getImageUrl()),
                () -> assertEquals(addedService.getAdditionalInformation(), serviceDto.getAdditionalInformation())
        );
    }

    @Test
    void should_get_service_by_id() {
        //given
        final String name = "New service";
        AddServiceDto service = AddServiceDto.builder()
                .name(name)
                .priceNet(BigDecimal.valueOf(100))
                .imageUrl("https://example.com/service-image.jpg")
                .additionalInformation("information")
                .build();
        //when
        ServiceDto addedService = serviceFacade.addNewService(service);
        ServiceDto serviceDto = serviceFacade.getServiceById(addedService.getId());
        //then
        assertAll(
                () -> assertEquals(addedService.getName(), serviceDto.getName()),
                () -> assertEquals(addedService.getPriceNet(), serviceDto.getPriceNet()),
                () -> assertEquals(addedService.getPriceGross(), serviceDto.getPriceGross()),
                () -> assertEquals(addedService.getImageUrl(), serviceDto.getImageUrl()),
                () -> assertEquals(addedService.getAdditionalInformation(), serviceDto.getAdditionalInformation())
        );
    }

    @Test
    void should_throw_exception_when_get_service_by_not_existing_name() {
        //given
        final String notExistingName = "notExistingName";
        //when
        //then
        assertThrows(ServiceNotFound.class, () -> serviceFacade.getServiceByName(notExistingName),
                "Service not found");
    }

    @Test
    void should_throw_exception_while_deleting_service_by_not_existing_id() {
        //given
        final int notExistingId = 99;
        //when
        //then
        assertThrows(ServiceNotFound.class, () -> serviceFacade.deleteService(notExistingId),
                "Service not found");
    }

    @Test
    void should_delete_service_properly() {
        //given
        final int serviceId = 1;
        //when
        final int sizeBeforeDeleting = serviceFacade.findAllServices().size();
        serviceFacade.deleteService(serviceId);
        final int sizeAfterDeleting = serviceFacade.findAllServices().size();
        //then
        assertEquals(1, sizeBeforeDeleting - sizeAfterDeleting);
    }

}
