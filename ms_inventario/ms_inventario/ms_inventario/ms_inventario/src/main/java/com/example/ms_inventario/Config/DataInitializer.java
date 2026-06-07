package com.example.ms_inventario.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.repository.InventarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component

public class DataInitializer implements CommandLineRunner {
    private final InventarioRepository inventarioRepository;

    @Override
    public void run(String... args) {
        if (inventarioRepository.count() > 0) {
            log.info(">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial");
            return;
        }

        log.info(">>> DataInitializer: BD vacia detectada, insertando datos de prueba...");
        // Aquí puedes agregar código para insertar datos de prueba en la base de datos
       Inventario item1 = new Inventario(null, "Tomate", 100);
       Inventario item2 = new Inventario(null, "Lechuga", 50);
        Inventario item3 = new Inventario(null, "Queso", 20);
        inventarioRepository.save(item1);
        inventarioRepository.save(item2);
        inventarioRepository.save(item3);
        log.info(">>> DataInitializer: Datos de prueba insertados correctamente.");

        

    }
}
