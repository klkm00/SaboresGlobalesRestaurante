package com.saboresglobales.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.saboresglobales.carrito.model.ItemCarritoModel;
import java.util.List;
import java.util.UUID;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarritoModel, UUID> {

    //buscar todos los items de un carrito especifico
    List<ItemCarritoModel> findByCarritoIdCarrito(UUID idCarrito);

    //buscar un producto especifico dentro de un carrito (para saber si el producto ya esta agregado)
    ItemCarritoModel findByCarritoIdCarritoAndProductoId(UUID idCarrito, UUID productoId);
}