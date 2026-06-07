
package com.example.ms_inventario.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_inventario.model.Inventario;
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    List<Inventario> findByInsumo(String insumo);
}
