package com.saboresGlobales.Delivery.delivery.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.saboresGlobales.Delivery.delivery.Modelo.Delivery;
import com.saboresGlobales.Delivery.delivery.Repository.DeliveryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
private final DeliveryRepository deliveryRepository;
@Override
public void run(String... args){
    if (deliveryRepository.count()>0){
        log.info(">>> DataInitaizer: la bd ya tiene datos, se omite la carga inicial");

    }

    log.info(">>> DataInitializer: bd vacia detectada, insertando datos de prueba...");

        ///deliveryRepository.save(new Delivery(null, "Elba Lazo",1500 , "Av.Tierra 403", "En Proceso"));
        //deliveryRepository.save(new Delivery(null, "Elsa pato",1500 , "Av.Luna 120", "Enviado"));
        //deliveryRepository.save(new Delivery(null, "Ines Perado",1500 , "Av.Estrella 201", "Recibido"));


        }
}
