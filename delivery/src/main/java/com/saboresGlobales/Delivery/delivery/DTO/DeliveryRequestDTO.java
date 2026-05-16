package com.saboresGlobales.Delivery.delivery.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequestDTO {
@NotBlank(message = "NO PUEDE ESTAR VACIO")
private String repartidor;
@Positive(message = "LA TARIFA DEBE SER MAYOR A 0")
private double tarifa;
@NotBlank(message = "NO PUEDE ESTAR VACIO")
private String gps;
private String estado;
}
