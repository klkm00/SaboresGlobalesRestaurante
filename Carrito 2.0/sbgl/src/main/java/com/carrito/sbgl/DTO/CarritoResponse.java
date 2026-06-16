package com.carrito.sbgl.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoResponse {
    private Long id;
    private Long id_producto;
    private int cantidad;

}
