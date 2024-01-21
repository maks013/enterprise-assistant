package com.enterpriseassistant.invoice;

import com.enterpriseassistant.client.domain.ClientFacade;
import com.enterpriseassistant.client.dto.ClientDto;
import com.enterpriseassistant.infrastructure.email.EmailSender;
import com.enterpriseassistant.invoice.domain.InvoiceFacade;
import com.enterpriseassistant.invoice.dto.InvoiceDto;
import com.enterpriseassistant.order.domain.OrderFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/invoices")
@Api(value = "Invoice Controller", description = "Moduł Api odpowiadający za faktury")
public class InvoiceController {

    private final InvoiceFacade invoiceFacade;
    private final EmailSender emailSender;
    private final ClientFacade clientFacade;
    private final OrderFacade orderFacade;

    @GetMapping
    @ApiOperation(value = "Pobiera wszystkie faktury")
    public ResponseEntity<List<InvoiceDto>> findAllInvoices(){
        return ResponseEntity.status(HttpStatus.OK).body(invoiceFacade.findAllInvoices());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Pobiera fakturę po identyfikatorze")
    public ResponseEntity<InvoiceDto> findInvoiceById(@PathVariable Integer id){
        InvoiceDto invoiceDto = invoiceFacade.findInvoiceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(invoiceDto);
    }

    @PostMapping("/create/{id}")
    @ApiOperation(value = "Tworzy nową fakturę")
    public ResponseEntity<InvoiceDto> createInvoice(@PathVariable Integer id){
        InvoiceDto invoiceDto = invoiceFacade.createNewInvoice(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceDto);
    }

    @GetMapping("/download/{id}")
    @ApiOperation(value = "Zapisuje plik PDF z fakturą")
    public void downloadInvoice(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        byte[] pdfContents = invoiceFacade.downloadInvoice(id);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoice_" + id + ".pdf");
        response.getOutputStream().write(pdfContents);
        response.getOutputStream().flush();
    }

    @PostMapping("/send/{id}")
    @ApiOperation(value = "Przesyła fakturę do klienta na adres e-mail")
    public ResponseEntity<?> sendInvoiceByEmail(@PathVariable Integer id) {
        InvoiceDto invoice = invoiceFacade.findInvoiceById(id);
        ClientDto client = clientFacade.getClientById(orderFacade.getOrderById(invoice.getOrderId()).getClientId());

        byte[] pdfContents = invoiceFacade.downloadInvoice(id);

        emailSender.sendWithAttachment(client.getRepresentative().getEmail(), client.getRepresentative().getName(), pdfContents, invoice.getNumber());

        return ResponseEntity.ok().body("Invoice sent successfully to " + client.getRepresentative().getEmail());
    }

}
