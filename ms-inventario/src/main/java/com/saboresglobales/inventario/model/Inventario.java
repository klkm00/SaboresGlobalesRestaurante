
package com.saboresglobales.inventario.model;
import jakarta.persistence.*;

@Entity
public class Inventario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String insumo;
    private int stock;
}
