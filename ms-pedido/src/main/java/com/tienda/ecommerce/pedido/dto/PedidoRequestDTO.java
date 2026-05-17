package com.tienda.ecommerce.pedido.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class PedidoRequestDTO {

    // Datos del cliente
    @NotBlank
    private String nombre;
    @NotBlank
    private String telefono;
    @NotBlank
    private String direccion;
    @NotBlank
    private String colonia;

    private String referencias;

    @NotBlank
    private String metodoPago;

    // Opcional para validar si se aplicó un descuento en el front
    private String codigoDescuento;

    @NotEmpty
    private List<ItemPedidoDTO> items;

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getColonia() {
        return colonia;
    }

    public String getReferencias() {
        return referencias;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public String getCodigoDescuento() {
        return codigoDescuento;
    }

    public List<ItemPedidoDTO> getItems() {
        return items;
    }
}
