package com.example.productos.dto;

/**
 * DTO para exponer los datos de un ítem de Receta en las respuestas REST.
 * No expone campos internos innecesarios de la entidad JPA.
 */
public class RecetaResponseDTO {

    private Long id;
    private String nombreInsumo;
    private int cantidad;
    private Long productoId;
    private String nombreProducto;

    public RecetaResponseDTO() {}

    public RecetaResponseDTO(Long id, String nombreInsumo, int cantidad, Long productoId, String nombreProducto) {
        this.id = id;
        this.nombreInsumo = nombreInsumo;
        this.cantidad = cantidad;
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreInsumo() { return nombreInsumo; }
    public void setNombreInsumo(String nombreInsumo) { this.nombreInsumo = nombreInsumo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
}
