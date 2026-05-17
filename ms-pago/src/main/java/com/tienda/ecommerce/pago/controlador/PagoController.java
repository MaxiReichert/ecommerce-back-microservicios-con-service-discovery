package com.tienda.ecommerce.pago.controlador;

import com.tienda.ecommerce.pago.dto.PagoResultado;
import com.tienda.ecommerce.pago.servicio.PagoServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    private final PagoServicio pagoServicio;

    public PagoController(PagoServicio pagoServicio) {
        this.pagoServicio = pagoServicio;
    }

    @PostMapping("/procesar")
    public ResponseEntity<PagoResultado> procesarPago(@RequestParam String metodoPago, @RequestParam Double total) {
        PagoResultado resultado = pagoServicio.procesarPago(metodoPago, total);
        return ResponseEntity.ok(resultado);
    }
}
