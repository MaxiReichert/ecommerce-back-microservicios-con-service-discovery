package com.tienda.ecommerce.pago.repositorio;

import com.tienda.ecommerce.pago.dominio.Descuento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DescuentoRepositorio extends JpaRepository<Descuento, Long> {
    Optional<Descuento> findByCodigo(String codigo);
}
