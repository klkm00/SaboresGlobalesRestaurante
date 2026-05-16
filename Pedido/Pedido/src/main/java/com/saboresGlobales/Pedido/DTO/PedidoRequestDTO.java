package com.saboresGlobales.Pedido.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {

    @NotBlank(message = "NO PUEDE ESTAR VACIO")
    private String estado;
    
    @NotNull(message = "LA ORDEN ES OBLIGATORIA")
    private Long ordenId;
}
