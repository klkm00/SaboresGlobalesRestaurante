package com.carrito.sbgl.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrito.sbgl.Model.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito,Long> {
}