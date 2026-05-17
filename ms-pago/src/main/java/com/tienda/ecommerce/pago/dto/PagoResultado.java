package com.tienda.ecommerce.pago.dto;

public class PagoResultado {
    private String estadoPago;
    private String idTransaccion;

    public PagoResultado(String estadoPago, String idTransaccion) {
        this.estadoPago = estadoPago;
        this.idTransaccion = idTransaccion;
    }

    public String getEstadoPago() { return estadoPago; }
    public String getIdTransaccion() { return idTransaccion; }
}
