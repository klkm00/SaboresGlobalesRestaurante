package com.example.productos.dto;

import jakarta.validation.constraints.*;

/**
 * DTO para recibir los datos de creación o actualización de un Producto.
 * Separa la validación de la entidad JPA (buena práctica).
 */
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción del producto es obligatoria")
    private String descripcion;

    @Positive(message = "El precio debe ser mayor a 0")
    private double precio;

    public ProductoRequestDTO() {}

    public ProductoRequestDTO(String nombre, String descripcion, double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}
