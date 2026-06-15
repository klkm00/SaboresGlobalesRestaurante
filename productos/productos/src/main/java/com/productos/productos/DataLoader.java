package com.productos.productos;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;

import com.productos.productos.Modelo.Producto;
import com.productos.productos.Modelo.Receta;
import com.productos.productos.Repository.ProductoRepository;
import com.productos.productos.Repository.RecetaRepository;

import net.datafaker.Faker;

@Profile("dev")
public class DataLoader implements CommandLineRunner{
    @Autowired
    private ProductoRepository repoprod;

    @Autowired
    private RecetaRepository reporece;

    @Override
    public void run(String... args) throws Exception{
        Faker faker = new Faker();
        Random random = new Random();

        List<Producto> productos = repoprod.findAll();

        for (int i = 0; i < 3; i++) {
            Receta receta = new Receta();
            receta.setNombreInsumo(faker.book().genre());
            receta.setCantidad(faker.book().hashCode());
            receta.setProducto(productos.get(random.nextInt(productos.size())));
        }

        for (int i = 0; i <3; i++) {
            Producto producto = new Producto();
            producto.setNombre(faker.book().genre());
            producto.setDescripcion(faker.book().genre());
            producto.setPrecio(faker.book().hashCode());            
        }
        
    }
}
