package com.saboresGlobales.Delivery.delivery.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponseDTO {
    private Long id;
    private String repartidor;
    private double tarifa;
    private String gps;
    private String estado;
}
