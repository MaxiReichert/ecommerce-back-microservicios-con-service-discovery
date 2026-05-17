package com.tienda.ecommerce.producto.controlador;

import com.tienda.ecommerce.compartido.respuesta.ApiRespuesta;
import com.tienda.ecommerce.producto.dto.ProductoDTO;
import com.tienda.ecommerce.producto.servicio.ProductoServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoControlador {

    private final ProductoServicio productoServicio;

    public ProductoControlador(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }

    // GET /api/productos
    @GetMapping
    public ResponseEntity<ApiRespuesta<List<ProductoDTO>>> obtenerTodos() {
        return ResponseEntity.ok(ApiRespuesta.exito(productoServicio.buscarTodos()));
    }

    // GET /api/productos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoServicio.buscarPorId(id));
    }
}
