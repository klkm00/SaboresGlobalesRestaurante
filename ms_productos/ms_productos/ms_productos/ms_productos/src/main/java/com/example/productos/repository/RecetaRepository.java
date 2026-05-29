
package com.example.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import com.example.productos.model.Receta;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    
    List<Receta> findByNombreInsumoContainsIgnoreCase(String nombreInsumo);


    @Query("SELECT r FROM Receta r WHERE r.producto.id = :productoId")
    List<Receta> findByProductoId(@Param("productoId") Long productoId);
}
