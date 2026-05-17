
package com.example.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.productos.model.Receta;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    List<Receta> findByProductoId(Long productoId);
}
