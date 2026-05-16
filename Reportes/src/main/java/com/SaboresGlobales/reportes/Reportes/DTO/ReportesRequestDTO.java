package com.SaboresGlobales.reportes.Reportes.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportesRequestDTO {
    @NotBlank(message = "NO PUEDE ESTAR VACIO")
    private String pedido;
    private BigDecimal pagos;
    private Integer inventario;
    private String delivery;

}
