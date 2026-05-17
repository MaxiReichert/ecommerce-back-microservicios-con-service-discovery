package com.tienda.ecommerce.pago.servicio;

import com.tienda.ecommerce.pago.dominio.Descuento;
import com.tienda.ecommerce.pago.repositorio.DescuentoRepositorio;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DescuentoServicio {

    private final DescuentoRepositorio descuentoRepositorio;

    public DescuentoServicio(DescuentoRepositorio descuentoRepositorio) {
        this.descuentoRepositorio = descuentoRepositorio;
    }

    public double obtenerPorcentajeDescuento(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return 0.0;
        }
        Optional<Descuento> descuentoOpt = descuentoRepositorio.findByCodigo(codigo.toUpperCase());
        if (descuentoOpt.isPresent()) {
            Descuento descuento = descuentoOpt.get();
            if (Boolean.TRUE.equals(descuento.getActivo())) {
                return descuento.getPorcentaje();
            }
        }
        return 0.0; // Código no existe o no está activo
    }
}
