package com.saboresGlobales.Pedido.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenResponseDTO {
    private Long id;
    private String orden;
    private String descripcion;
}
