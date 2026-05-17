package com.tienda.ecommerce.pedido.dto.externo;

public class PagoResultado {
    private String estadoPago;
    private String idTransaccion;

    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }
    public String getIdTransaccion() { return idTransaccion; }
    public void setIdTransaccion(String idTransaccion) { this.idTransaccion = idTransaccion; }
}
