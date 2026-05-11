package com.saboresglobales.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUsuario;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String correo;

    @NotBlank
    @Column(nullable = false)
    private String contrasena;            //no texto plano

    @NotBlank
    @Column(nullable = false)
    private String rol = "CLIENTE";       //"CLIENTE", "TRABAJADOR", "ADMIN"

    @Column(nullable = false)
    private Boolean activo = true;

    private String codigoRecuperacion;    //codigo temporal para la recuperacion
}