package com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {


    @NotBlank(message = "EL RUT NO PUEDE ESTAR VACIO")
    private String rut;

    @NotBlank(message = "NO PUEDE ESTAR VACIO")
    private String nombres;
    private String apellidos;
    private String correo;

    @NotNull(message = "EL ROL ES OBLIGATORIO")
    private Long rolId;

}
