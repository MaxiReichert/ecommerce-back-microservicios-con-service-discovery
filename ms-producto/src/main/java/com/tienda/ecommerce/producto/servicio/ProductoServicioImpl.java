package com.tienda.ecommerce.producto.servicio;

import com.tienda.ecommerce.producto.dto.ProductoDTO;
import com.tienda.ecommerce.producto.repositorio.ProductoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductoServicioImpl implements ProductoServicio {

    private final ProductoRepositorio productoRepositorio;

    public ProductoServicioImpl(ProductoRepositorio productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

    @Override
    public List<ProductoDTO> buscarTodos() {
        return productoRepositorio.findAll()
                .stream()
                .map(ProductoDTO::desdeEntidad)
                .toList();
    }

    @Override
    public ProductoDTO buscarPorId(Long id) {
        return productoRepositorio.findById(id)
                .map(ProductoDTO::desdeEntidad)
                .orElseThrow(() -> new com.tienda.ecommerce.compartido.excepcion.EntidadNoEncontradaException(
                        "Producto no encontrado con id: " + id));
    }
}
