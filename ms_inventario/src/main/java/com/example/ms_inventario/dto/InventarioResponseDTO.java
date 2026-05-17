package com.example.ms_inventario.dto;

/**
 * DTO para exponer los datos de un Inventario en las respuestas REST.
 * No expone campos internos innecesarios de la entidad JPA.
 */
public class InventarioResponseDTO {

    private Long id;
    private String insumo;
    private int stock;

    public InventarioResponseDTO() {}

    public InventarioResponseDTO(Long id, String insumo, int stock) {
        this.id = id;
        this.insumo = insumo;
        this.stock = stock;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getInsumo() { return insumo; }
    public void setInsumo(String insumo) { this.insumo = insumo; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
