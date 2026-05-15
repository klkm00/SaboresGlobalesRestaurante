
package com.saboresglobales.inventario.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Inventario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String insumo;
    private int stock;
}
