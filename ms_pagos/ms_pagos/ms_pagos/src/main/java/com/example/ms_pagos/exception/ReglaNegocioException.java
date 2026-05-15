package com.example.ms_pagos.exception;

/**
 * Excepción lanzada cuando se viola una regla de negocio (HTTP 400).
 * Ejemplo: intentar pagar un pedido que ya fue pagado.
 */
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
