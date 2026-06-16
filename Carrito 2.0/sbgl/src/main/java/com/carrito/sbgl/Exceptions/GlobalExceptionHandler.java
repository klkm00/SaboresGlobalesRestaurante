package com.carrito.sbgl.Exceptions;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
public ResponseEntity<Map<String,String>> handleValidationErrors(
    MethodArgumentNotValidException ex){
    Map<String, String> errores = new LinkedHashMap<>();
    
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errores.put(error.getField(),error.getDefaultMessage())

        );

        return ResponseEntity.badRequest().body(errores);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,String>> handleRuntimeException(
        RuntimeException ex){
            Map<String,String> error = new LinkedHashMap<>();
            error.put("error", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
}
