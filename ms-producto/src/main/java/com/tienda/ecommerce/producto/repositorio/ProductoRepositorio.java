package com.tienda.ecommerce.producto.repositorio;

import com.tienda.ecommerce.producto.dominio.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
    // El frontend maneja los filtros → solo necesitamos findAll() de JpaRepository
}
