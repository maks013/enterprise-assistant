package com.enterpriseassistant.invoice.domain;

import com.enterpriseassistant.client.domain.ClientFacadeConfigForTests;
import com.enterpriseassistant.invoice.dto.InvoiceDto;
import com.enterpriseassistant.invoice.exception.InvoiceAlreadyExists;
import com.enterpriseassistant.order.domain.OrderFacade;
import com.enterpriseassistant.order.domain.OrderFacadeConfigForTests;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceFacadeTest {

    InMemoryInvoiceRepository invoiceRepository = new InMemoryInvoiceRepository();
    OrderFacadeConfigForTests orderFacadeConfigForTests = new OrderFacadeConfigForTests();
    OrderFacade orderFacade = orderFacadeConfigForTests.orderFacade();
    ClientFacadeConfigForTests clientFacadeConfigForTests = new ClientFacadeConfigForTests();

    InvoiceFacade invoiceFacade = new InvoiceFacade(invoiceRepository, new InvoiceCreator(),
            orderFacade, clientFacadeConfigForTests.clientFacade());

    @Test
    void should_find_all_invoices() {
        //given
        final int sizeOfAllInvoices = invoiceFacade.findAllInvoices().size();
        //when
        //then
        assertEquals(1, sizeOfAllInvoices);
    }

    @Test
    void should_find_invoice_by_id() {
        //given
        final int invoiceId = 1;
        //when
        InvoiceDto invoiceDto = invoiceFacade.findInvoiceById(invoiceId);
        //then
        assertAll(
                () -> assertEquals(1, invoiceDto.getId()),
                () -> assertEquals("FV1/2023", invoiceDto.getNumber())
        );
    }

    @Test
    void should_throw_exception_when_create_invoice_with_already_existing_orderId() {
        //given
        final int orderId = 1;
        //when
        //then
        assertThrows(InvoiceAlreadyExists.class, () -> invoiceFacade.createNewInvoice(orderId));
    }

    @Test
    void should_create_new_invoice_successfully() {
        //given
        final int orderId = 2;
        //when
        InvoiceDto invoiceDto = invoiceFacade.createNewInvoice(orderId);
        //then
        assertAll(
                () -> assertEquals(2, invoiceDto.getId()),
                () -> assertEquals(orderId, invoiceDto.getOrderId()),
                () -> assertEquals(orderFacade.getOrderById(orderId).getPayment(), invoiceDto.getPayment()),
                () -> assertEquals(LocalDateTime.now().plusDays(orderFacade.getOrderById(orderId).getDaysToPay().days).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        invoiceDto.getPaymentDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        );
    }

}
