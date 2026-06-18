package com.example.ms_inventario;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.repository.InventarioRepository;

import net.datafaker.Faker;
@Profile("dev")
@Component
public class DataLoader  implements CommandLineRunner{
    @Autowired
    private InventarioRepository repository;

    @Override
    public void run(String... args) throws Exception{
        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i <3; i++) {
            Inventario inventario = new Inventario();
            inventario.setInsumo(faker.book().genre());
            inventario.setStock(i);
        }
    }
}
