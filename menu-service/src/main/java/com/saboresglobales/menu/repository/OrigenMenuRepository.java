package com.saboresglobales.menu.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.sabores.globales.menu.model.OrigenMenuModel;

@Repository
public interface OrigenMenuRepository extends JpaRepository<OrigenMenuModel, UUID> {

    List<OrigenMenuModel> findByCartaDisponibleTrue();
    
}
