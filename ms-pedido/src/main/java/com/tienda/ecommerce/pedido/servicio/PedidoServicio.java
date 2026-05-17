package com.tienda.ecommerce.pedido.servicio;

import com.tienda.ecommerce.pedido.dto.PedidoRequestDTO;

public interface PedidoServicio {

    /**
     * Crea un pedido validando los precios contra la base de datos de productos.
     * Retorna el código de seguimiento generado.
     */
    String crearPedido(PedidoRequestDTO request);
}
