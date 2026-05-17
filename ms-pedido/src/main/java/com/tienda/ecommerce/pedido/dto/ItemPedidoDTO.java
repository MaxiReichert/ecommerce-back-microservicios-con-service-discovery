package com.tienda.ecommerce.pedido.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemPedidoDTO {

    @NotNull
    private Long productoId;

    @NotNull
    @Min(1)
    private Integer cantidad;

    // Getters
    public Long getProductoId() { return productoId; }
    public Integer getCantidad() { return cantidad; }
}
