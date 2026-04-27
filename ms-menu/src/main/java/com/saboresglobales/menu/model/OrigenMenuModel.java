package com.saboresglobales.menu.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "origen")
@AllArgsConstructor
@NoArgsConstructor
public class OrigenMenuModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idOrigen;               // ID autogenerado

    @NotBlank
    @Column(nullable = false)
    private String nombreCarta;         // "Comida asiatica", "Comida italiana"

    private String descripcionCarta;    // Descripcion del origen de la cocina

    @Column(nullable = false)
    private Boolean cartaDisponible = true; // Activar/Desactivar una carta
}