package com.tienda.ecommerce.compartido.excepcion;

public class EntidadNoEncontradaException extends RuntimeException {

    public EntidadNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
