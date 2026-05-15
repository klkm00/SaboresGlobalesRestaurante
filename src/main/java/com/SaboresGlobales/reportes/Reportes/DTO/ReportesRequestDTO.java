package com.SaboresGlobales.reportes.Reportes.DTO;

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
    private double pagos;
    private int inventario;
    private String delivery;

}
