package com.carrito.sbgl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;

import com.carrito.sbgl.Model.Carrito;
import com.carrito.sbgl.Repository.CarritoRepository;
import com.carrito.sbgl.Service.CarritoService;

import net.datafaker.Faker;

@Profile("dev")
public class DataLoader implements CommandLineRunner {
    @Autowired
    private CarritoRepository repository;
    @Autowired
    private CarritoService service;

    @Override
    public void run(String... args) throws Exception{
        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            Carrito  carrito = new Carrito();
            carrito.setId_producto(random.nextLong());
            carrito.setCantidad(random.nextInt());
        }
    }

}
