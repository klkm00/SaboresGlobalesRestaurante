package com.example.productos.dto;

import jakarta.validation.constraints.*;

/**
 * DTO para recibir los datos de creación de un ítem de Receta.
 * Separa la validación de la entidad JPA (buena práctica).
 */
public class RecetaRequestDTO {

    @NotBlank(message = "El nombre del insumo es obligatorio")
    private String nombreInsumo;

    @Positive(message = "La cantidad debe ser mayor a 0")
    private int cantidad;

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    public RecetaRequestDTO() {}

    public RecetaRequestDTO(String nombreInsumo, int cantidad, Long productoId) {
        this.nombreInsumo = nombreInsumo;
        this.cantidad = cantidad;
        this.productoId = productoId;
    }

    public String getNombreInsumo() { return nombreInsumo; }
    public void setNombreInsumo(String nombreInsumo) { this.nombreInsumo = nombreInsumo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
}
