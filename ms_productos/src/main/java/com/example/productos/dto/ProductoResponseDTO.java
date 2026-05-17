package com.example.productos.dto;

/**
 * DTO para exponer los datos de un Producto en las respuestas REST.
 * No expone campos internos innecesarios de la entidad JPA.
 */
public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;

    public ProductoResponseDTO() {}

    public ProductoResponseDTO(Long id, String nombre, String descripcion, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}
