package com.example.ms_inventario.dto;

import jakarta.validation.constraints.*;

/**
 * DTO para recibir los datos de creación o actualización de un Inventario.
 * Separa la validación de la entidad JPA (buena práctica).
 */
public class InventarioRequestDTO {

    @NotBlank(message = "El nombre del insumo es obligatorio")
    private String insumo;

    @PositiveOrZero(message = "El stock no puede ser negativo")
    private int stock;

    public InventarioRequestDTO() {}

    public InventarioRequestDTO(String insumo, int stock) {
        this.insumo = insumo;
        this.stock = stock;
    }

    public String getInsumo() { return insumo; }
    public void setInsumo(String insumo) { this.insumo = insumo; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
