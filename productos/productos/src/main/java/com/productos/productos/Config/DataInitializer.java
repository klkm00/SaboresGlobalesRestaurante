package com.productos.productos.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.productos.productos.Modelo.Producto;
import com.productos.productos.Modelo.Receta;
import com.productos.productos.Repository.ProductoRepository;
import com.productos.productos.Repository.RecetaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
 private final ProductoRepository productoRepo;
    private final RecetaRepository recetaRepo;

   
    
    @Override
    public void run(String... args){
        if (productoRepo.count() > 0) {
            log.info("Inicializando datos de prueba...");
            // Aquí puedes agregar código para crear productos y recetas de ejemplo
            return;
        }


        log.info("No se encontraron productos, inicializando datos de prueba...");
        // Aquí puedes agregar código para crear productos y recetas de ejemplo
        // Por ejemplo:
    
        Producto producto1 = new Producto(null, "Pizza Margherita", "Pizza clásica con tomate, mozzarella y albahaca", 8.99);
        Producto producto2 = new Producto(null, "Hamburguesa Clásica", "Hamburguesa con carne, queso, lechuga y tomate", 6.49);
        Producto producto3 = new Producto(null, "Ensalada César", "Ensalada con lechuga, pollo, croutons y aderezo César", 5.99);
        productoRepo.save(producto1);
        productoRepo.save(producto2);
        productoRepo.save(producto3);

        Receta receta1 = new Receta(null, "Tomate", 2, producto1);
        Receta receta2 = new Receta(null, "Carne de res", 1, producto2);
        Receta receta3 = new Receta(null, "Lechuga", 1, producto3);
        
        recetaRepo.save(receta1);
        recetaRepo.save(receta2);
        recetaRepo.save(receta3);
        log.info("Datos de prueba inicializados.");
    }
}
