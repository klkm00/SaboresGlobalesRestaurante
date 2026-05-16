package com.saboresGlobales.Pedido.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {
private Long id;
private String estado;
private String orden;


}
