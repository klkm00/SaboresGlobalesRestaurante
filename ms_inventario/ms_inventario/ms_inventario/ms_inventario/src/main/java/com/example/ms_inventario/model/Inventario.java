
package com.example.ms_inventario.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventario")
@Entity
public class Inventario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String insumo;
    private int stock;
}
