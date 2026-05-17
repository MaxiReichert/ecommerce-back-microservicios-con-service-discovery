package com.tienda.ecommerce.pago.controlador;

import com.tienda.ecommerce.pago.servicio.DescuentoServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/descuentos")
@CrossOrigin(origins = "*")
public class DescuentoController {

    private final DescuentoServicio descuentoServicio;

    public DescuentoController(DescuentoServicio descuentoServicio) {
        this.descuentoServicio = descuentoServicio;
    }

    @GetMapping("/validar")
    public ResponseEntity<Double> validarDescuento(@RequestParam String codigo) {
        double porcentaje = descuentoServicio.obtenerPorcentajeDescuento(codigo);
        return ResponseEntity.ok(porcentaje);
    }
}
