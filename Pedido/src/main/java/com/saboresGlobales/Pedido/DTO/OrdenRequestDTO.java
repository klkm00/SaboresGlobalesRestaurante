package com.saboresGlobales.Pedido.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenRequestDTO {
    @NotBlank(message = "NO PUEDE ESTAR VACIO")
    private String orden;
    @NotBlank(message = "NO PUEDE ESTAR VACIO")
    private String descripcion;
}
