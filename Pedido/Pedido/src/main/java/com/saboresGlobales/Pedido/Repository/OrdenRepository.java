package com.saboresGlobales.Pedido.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saboresGlobales.Pedido.Model.Orden;

public interface OrdenRepository extends JpaRepository<Orden,Long> {
    
}
