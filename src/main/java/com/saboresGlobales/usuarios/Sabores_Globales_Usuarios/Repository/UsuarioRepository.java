package com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Modelo.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>  {

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
