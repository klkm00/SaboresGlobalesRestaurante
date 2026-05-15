package com.saboresglobales.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.saboresglobales.auth.model.UsuarioModel;
import java.util.UUID; 

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {

    //buscar usuario por correo (para el login)
    UsuarioModel findByCorreo(String correo);

    //buscar usuario por código de recuperación
    UsuarioModel findByCodigoRecuperacion(String codigoRecuperacion);

    //verificar si un correo ya existe (para el registro)
    Boolean existsByCorreo(String correo);
}