package com.auth.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.auth.Model.Usuario;

import org.springframework.data.repository.query.Param;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>  {
    boolean existsByRut(String rut);
    List<Usuario> findByRutContainingIgnoreCase(String rut);
    List<Usuario> findByNombresContainingIgnoreCase(String nombres);

    @Query("SELECT u FROM Usuario  u WHERE u.rol.id = :rolId")
    List<Usuario> findByRolId(@Param("rolId")Long rolId);
    
    @Query(
        value = "SELECT * FROM usuarios WHERE rut LIKE CONCAT('%',:texto,'%')",
        nativeQuery = true
    )
    List<Usuario> buscarPorRut(@Param("texto") String texto);

}
