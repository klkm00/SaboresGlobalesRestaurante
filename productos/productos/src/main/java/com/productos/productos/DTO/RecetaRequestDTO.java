package com.productos.productos.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetaRequestDTO {
    
    @NotBlank(message = "El nombre del insumo es obligatorio")
    private String nombreInsumo;

    @Positive(message = "La cantidad debe ser mayor a 0")
    private int cantidad;

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

}
