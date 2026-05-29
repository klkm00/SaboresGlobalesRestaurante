package com.example.productos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para exponer los datos de un ítem de Receta en las respuestas REST.
 * No expone campos internos innecesarios de la entidad JPA.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecetaResponseDTO {

    private Long id;
    private String nombreInsumo;
    private int cantidad;
    private String productoNombre; // Para mostrar el nombre del producto asociado
}
