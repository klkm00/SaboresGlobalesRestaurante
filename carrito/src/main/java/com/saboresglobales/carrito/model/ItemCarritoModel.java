package com.saboresglobales.carrito.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "item_carrito")
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarritoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idItemCarrito;  //id creado automaticamente

    @ManyToOne //muchos items pertenecen a un solo carrito
    @JoinColumn(name = "carrito_id", nullable = false)
    private CarritoModel carrito; //referencia local al carrito

    @NotNull
    @Column(name = "producto_id", nullable = false)
    private UUID productoId; //ver variables

    @NotNull
    @Min(value = 1) //la cantidad minima es 1 item
    @Column(nullable = false)
    private int cantidad;

    @NotNull
    @Column(nullable = false)
    private Double precioUnitario; //precio fijo al momento de agregar el item

    @Column(nullable = false)
    private Double subtotal; //se calcula: cantidad*precioUnitario
}