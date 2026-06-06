package com.productos.productos.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.productos.productos.Modelo.Producto;

public interface  ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
