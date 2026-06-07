package com.example.ms_pagos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.ms_pagos.model.Pago;
import com.example.ms_pagos.repository.PagoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer  implements CommandLineRunner {
    private final PagoRepository pagoRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("[ms_pagos] Iniciando DataInitializer...");

        // Verificar si ya hay datos para evitar duplicados
        if (pagoRepository.count() > 0) {
            log.info("[ms_pagos] Ya existen pagos en la base de datos, no se insertarán datos de ejemplo.");
        } else {
            log.info("[ms_pagos] No se encontraron pagos, insertando datos de ejemplo...");

            Pago pago1 = new Pago(null, 1L, 100.0, Pago.EstadoPago.CONFIRMADO, "TARJETA", null, "TXN12345");
            Pago pago2 = new Pago(null, 2L, 50.0, Pago.EstadoPago.PENDIENTE, "EFECTIVO", null, "TXN67890");
            Pago pago3 = new Pago(null, 1L, 25.0, Pago.EstadoPago.FALLIDO, "TRANSFERENCIA", null, "TXN54321");

            pagoRepository.save(pago1);
            pagoRepository.save(pago2);
            pagoRepository.save(pago3);

            log.info("[ms_pagos] Datos de ejemplo insertados correctamente.");
        } 
        
    }
}
