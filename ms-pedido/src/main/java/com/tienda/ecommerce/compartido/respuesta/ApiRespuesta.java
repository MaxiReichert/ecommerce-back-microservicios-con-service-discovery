package com.tienda.ecommerce.compartido.respuesta;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiRespuesta<T> {

    private T datos;
    private String mensaje;
    private int estado;

    private ApiRespuesta(T datos, String mensaje, int estado) {
        this.datos = datos;
        this.mensaje = mensaje;
        this.estado = estado;
    }

    // ─── Factories ────────────────────────────────────────────────────────────

    public static <T> ApiRespuesta<T> exito(T datos) {
        return new ApiRespuesta<>(datos, null, 200);
    }

    public static <T> ApiRespuesta<T> exito(T datos, String mensaje) {
        return new ApiRespuesta<>(datos, mensaje, 200);
    }

    public static <T> ApiRespuesta<T> creado(T datos) {
        return new ApiRespuesta<>(datos, null, 201);
    }

    public static <T> ApiRespuesta<T> error(String mensaje, int estado) {
        return new ApiRespuesta<>(null, mensaje, estado);
    }

    // ─── Getters ──────────────────────────────────────────────────────────────

    public T getDatos() { return datos; }
    public String getMensaje() { return mensaje; }
    public int getEstado() { return estado; }
}
