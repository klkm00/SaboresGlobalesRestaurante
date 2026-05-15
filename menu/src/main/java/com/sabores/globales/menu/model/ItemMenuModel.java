package com.sabores.globales.menu.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "item_menu")
@AllArgsConstructor
@NoArgsConstructor
public class ItemMenuModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idItem;               // ID autogenerado

    @ManyToOne
    @JoinColumn(name = "origen_id", nullable = false)
    private OrigenMenuModel origenItem;         // a qué cocina pertenece el plato

    @NotNull
    @Column(name = "producto_id", nullable = false)
    private UUID productoId;       // -ms producto- ID del producto particular

    @Column(nullable = false)
    private Boolean itemDisponible = true; // disponibilidad del playo hoy
}