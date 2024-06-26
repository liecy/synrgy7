package com.rahmi.binfood.controller;

import com.rahmi.binfood.service.InvoiceFacade;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceFacade invoiceFacade;

    @Autowired
    public InvoiceController(InvoiceFacade invoiceFacade) {
        this.invoiceFacade = invoiceFacade;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<byte[]> getOrderInvoice(@PathVariable UUID orderId) {
        try {
            byte[] pdf = invoiceFacade.generateInvoice(orderId);
            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .header("Content-Disposition", "inline; filename=invoice.pdf")
                    .body(pdf);
        } catch (JRException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body(("{\"message\": \"Failed to generate invoice: " + e.getMessage() + "\"}").getBytes());
        }
    }
}
