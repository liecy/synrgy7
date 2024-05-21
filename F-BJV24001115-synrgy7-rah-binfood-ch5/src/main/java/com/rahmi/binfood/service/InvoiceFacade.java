package com.rahmi.binfood.service;

import net.sf.jasperreports.engine.JRException;

import java.util.UUID;

public interface InvoiceFacade {
    byte[] generateInvoice(UUID orderId) throws JRException;
}