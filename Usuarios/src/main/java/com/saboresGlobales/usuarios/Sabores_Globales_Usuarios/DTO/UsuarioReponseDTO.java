package com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioReponseDTO {

    private Long id;
    private String rut;
    private String nombres;
    private String apellidos;
    private String correo;


    private String rol;

}
