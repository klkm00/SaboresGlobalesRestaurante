package com.example.productos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para exponer los datos de un Producto en las respuestas REST.
 * No expone campos internos innecesarios de la entidad JPA.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;


}
