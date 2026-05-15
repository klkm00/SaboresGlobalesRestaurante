package com.sabores.globales.menu.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sabores.globales.menu.model.ItemMenuModel;

@Repository
public interface ItemMenuRepository extends JpaRepository<ItemMenuModel, UUID> {

    // Buscar todos los items de un origen específico
    List<ItemMenuModel> findByOrigenItemIdOrigen(UUID idOrigen);

    // Buscar los items disponibles en este momento
    List<ItemMenuModel> findByItemDisponibleTrue();

    // Buscar items disponibles de un origen específico
    List<ItemMenuModel> findByOrigenItemIdOrigenAndItemDisponibleTrue(UUID idOrigen);
}
