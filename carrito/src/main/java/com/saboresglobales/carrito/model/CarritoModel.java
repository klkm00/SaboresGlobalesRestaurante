package com.saboresglobales.carrito.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "carrito")
@AllArgsConstructor
@NoArgsConstructor
public class CarritoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idCarrito; //id creado automaticamente

    @NotNull
    @Column(name = "cliente_id", nullable = false)
    private UUID clienteId; //ver variables

    @NotBlank
    @Column(nullable = false)
    private String estado = "ACTIVO"; //valor por defecto al crear un carrito

    @Column(nullable = false)
    private Double total = 0.0; //empieza en 0, se actualiza al agregar un item

    private LocalDateTime fechaCreado = LocalDateTime.now();

    private LocalDateTime fechaActualizado = LocalDateTime.now();
}