package com.saboresGlobales.Delivery.delivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;

import com.saboresGlobales.Delivery.delivery.Modelo.Delivery;
import com.saboresGlobales.Delivery.delivery.Repository.DeliveryRepository;
import com.saboresGlobales.Delivery.delivery.Service.DeliveryService;

import net.datafaker.Faker;

@Profile("dev")
public class DataLoader implements CommandLineRunner{
    @Autowired
    private DeliveryRepository repository;

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        Faker faker = new Faker();
        for (int i = 0; i < 3; i++) {
            Delivery delivery = new Delivery();
            delivery.setEstado(faker.book().genre());
            delivery.setGps(faker.book().genre());
            delivery.setTarifa(faker.book().hashCode());
            delivery.setRepartidor(faker.book().genre());
        }  
    }
}

    
