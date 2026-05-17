package com.tienda.ecommerce.compartido.excepcion;

import com.tienda.ecommerce.compartido.respuesta.ApiRespuesta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ManejadorExcepciones {

    @ExceptionHandler(EntidadNoEncontradaException.class)
    public ResponseEntity<ApiRespuesta<Void>> manejarNoEncontrada(EntidadNoEncontradaException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiRespuesta.error(ex.getMessage(), 404));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiRespuesta<Void>> manejarValidacion(MethodArgumentNotValidException ex) {
        String errores = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiRespuesta.error(errores, 400));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiRespuesta<Void>> manejarGeneral(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiRespuesta.error("Error interno del servidor", 500));
    }
}
