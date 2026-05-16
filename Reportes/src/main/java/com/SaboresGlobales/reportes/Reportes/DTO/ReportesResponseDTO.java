package com.SaboresGlobales.reportes.Reportes.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReportesResponseDTO {

    private Long id;
    private String pedido;
    private BigDecimal pagos;
    private Integer inventario;
    private String delivery;
}
