package com.tienda.ecommerce.pago.servicio;

import com.tienda.ecommerce.pago.dto.PagoResultado;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PagoServicio {

    public PagoResultado procesarPago(String metodoPago, Double total) {
        if (metodoPago == null) {
            return new PagoResultado("PENDIENTE", null);
        }

        switch (metodoPago.toLowerCase()) {
            case "efectivo":
                // Pago en efectivo al recibir
                return new PagoResultado("PENDIENTE", null);
            case "tarjeta":
            case "codi":
                // Simular pasarela de pago (aprobado exitosamente)
                String txId = "TX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                return new PagoResultado("PAGADO", txId);
            default:
                return new PagoResultado("PENDIENTE", null);
        }
    }
}
