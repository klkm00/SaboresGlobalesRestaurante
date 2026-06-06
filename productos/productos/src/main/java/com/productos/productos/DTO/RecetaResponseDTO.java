package com.productos.productos.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RecetaResponseDTO {
    private Long id;
    private String nombreInsumo; // Agrega el campo nombreInsumo a la respuesta de la receta
    private int cantidad; // Agrega el campo cantidad a la respuesta de la receta
    private String productoNombre; // Agrega el nombre del producto asociado a la receta
}
