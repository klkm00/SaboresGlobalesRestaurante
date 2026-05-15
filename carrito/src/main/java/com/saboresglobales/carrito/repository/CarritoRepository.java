package com.saboresglobales.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.saboresglobales.carrito.model.CarritoModel;
import java.util.UUID;

@Repository
public interface CarritoRepository extends JpaRepository<CarritoModel, UUID> {

    //buscar el carrito activo de un cliente especifico
    CarritoModel findByClienteIdAndEstado(UUID clienteId, String estado);
}