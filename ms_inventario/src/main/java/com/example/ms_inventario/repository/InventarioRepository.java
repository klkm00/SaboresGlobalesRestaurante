
package com.example.ms_inventario.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_inventario.model.Inventario;
public interface InventarioRepository extends JpaRepository<Inventario, Long> {}
