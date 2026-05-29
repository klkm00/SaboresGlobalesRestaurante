
package com.example.productos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.productos.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

List<Producto> findByNombreContainsIgnoreCase(String nombre);

}
