package com.tienda.ecommerce.pedido.controlador;

import com.tienda.ecommerce.compartido.respuesta.ApiRespuesta;
import com.tienda.ecommerce.pedido.dto.PedidoRequestDTO;
import com.tienda.ecommerce.pedido.servicio.PedidoServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoControlador {

    private final PedidoServicio pedidoServicio;

    public PedidoControlador(PedidoServicio pedidoServicio) {
        this.pedidoServicio = pedidoServicio;
    }

    @PostMapping
    public ResponseEntity<ApiRespuesta<String>> crearPedido(@Valid @RequestBody PedidoRequestDTO request) {
        String codigoSeguimiento = pedidoServicio.crearPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiRespuesta.exito(codigoSeguimiento));
    }
}
