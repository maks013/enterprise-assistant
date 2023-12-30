package com.enterpriseassistant.invoice.domain;

import com.enterpriseassistant.client.domain.ClientFacade;
import com.enterpriseassistant.client.dto.ClientDto;
import com.enterpriseassistant.invoice.dto.InvoiceDto;
import com.enterpriseassistant.invoice.exception.InvoiceAlreadyExists;
import com.enterpriseassistant.invoice.exception.InvoiceNotFound;
import com.enterpriseassistant.order.domain.OrderFacade;
import com.enterpriseassistant.order.dto.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class InvoiceFacade {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceCreator invoiceCreator;
    private final OrderFacade orderFacade;
    private final ClientFacade clientFacade;

    public List<InvoiceDto> findAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(Invoice::toDto)
                .collect(Collectors.toList());
    }

    public InvoiceDto findInvoiceById(Integer id) {
        return getInvoiceById(id).toDto();
    }

    public byte[] downloadInvoice(Integer id) {
        final Invoice invoice = getInvoiceById(id);
        final OrderDto order = orderFacade.getOrderById(invoice.getOrderId());
        final ClientDto client = clientFacade.getClientById(order.getClientId());
        return invoiceCreator.generateInvoicePdf(invoice, order, client);
    }

    public InvoiceDto createNewInvoice(int orderId) {

        if (invoiceRepository.existsByOrderId(orderId)) {
            throw new InvoiceAlreadyExists();
        }

        final OrderDto order = orderFacade.getOrderById(orderId);

        final Invoice invoice = Invoice.builder()
                .number(createInvoiceNumber(LocalDateTime.now(), orderId))
                .payment(order.getPayment())
                .paymentDueDate(LocalDateTime.now().plusDays(order.getDaysToPay().days))
                .totalPriceGross(calculateTotalPriceGross(order))
                .totalPriceNet(calculateTotalPriceNet(order))
                .issueDate(LocalDateTime.now())
                .orderId(order.getId())
                .build();

        return invoiceRepository.save(invoice).toDto();
    }

    private Invoice getInvoiceById(Integer id) {
        return invoiceRepository.findById(id)
                .orElseThrow(InvoiceNotFound::new);
    }

    private String createInvoiceNumber(LocalDateTime localDateTime, int invoiceId) {
        return "FV" + invoiceId + "/" + localDateTime.getYear();
    }

    private BigDecimal calculateTotalPriceGross(OrderDto order) {
        final BigDecimal totalProductPriceGross = order.getProductOrderItems().stream()
                .map(item -> item.getUnitPriceGross().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final BigDecimal totalServicePriceGross = order.getServiceOrderItems().stream()
                .map(item -> item.getUnitPriceGross().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalProductPriceGross.add(totalServicePriceGross);
    }

    private BigDecimal calculateTotalPriceNet(OrderDto order) {
        final BigDecimal totalProductPriceNet = order.getProductOrderItems().stream()
                .map(item -> item.getUnitPriceNet().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final BigDecimal totalServicePriceNet = order.getServiceOrderItems().stream()
                .map(item -> item.getUnitPriceNet().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalProductPriceNet.add(totalServicePriceNet);
    }

}
