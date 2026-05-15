package com.example.ms_pagos.exception;

/**
 * Excepción lanzada cuando no se encuentra un recurso (HTTP 404).
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
