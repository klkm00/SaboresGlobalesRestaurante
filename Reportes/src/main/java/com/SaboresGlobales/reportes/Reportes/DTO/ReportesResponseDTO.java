package com.SaboresGlobales.reportes.Reportes.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReportesResponseDTO {

    private Long id;
    private String pedido;
    private double pagos;
    private Integer inventario;
    private String delivery;
}
