package com.auth.DTO;

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
    @NotBlank(message = "No PUEDE ESTAR VACIO")
    private String apellidos;
  
    @NotBlank(message = "EL CORREO ES OBLIGATORIO")
    private String correo;
    @NotBlank(message = "LA CONTRASEÑA ES OBLIGATORIA")
    private String contraseña;
    
    @NotBlank(message = "EL CODIGO DE RECUPERACION ES OBLIGATORIO")
    private String codigo_recuperacion;
    @NotNull(message = "EL ROL ES OBLIGATORIO")
    private Long rolId;

}
