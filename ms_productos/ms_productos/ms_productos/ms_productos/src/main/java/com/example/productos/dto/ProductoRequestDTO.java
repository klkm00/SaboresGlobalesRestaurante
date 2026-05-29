package com.example.productos.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para recibir los datos de creación o actualización de un Producto.
 * Separa la validación de la entidad JPA (buena práctica).
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción del producto es obligatoria")
    private String descripcion;

    @Positive(message = "El precio debe ser mayor a 0")
    private double precio;
}
