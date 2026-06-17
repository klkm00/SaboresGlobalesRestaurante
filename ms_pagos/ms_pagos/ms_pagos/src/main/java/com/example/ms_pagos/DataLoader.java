package com.example.ms_pagos;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.ms_pagos.model.Pago;
import com.example.ms_pagos.repository.PagoRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{
    @Autowired
    private PagoRepository repository;
    
    @Override
    public void run(String... args) throws Exception{
        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            Pago pagos = new Pago();
            // As Pago.setEstado expects an enum Pago.EstadoPago, pick a random enum value
            Pago.EstadoPago[] estados = Pago.EstadoPago.values();
            pagos.setEstado(estados[random.nextInt(estados.length)]);
            repository.save(pagos);
            pagos.setCodigoTransaccion("23FR3");
            pagos.setMetodoPago("EFECTIVO");
            pagos.setMonto(random.nextDouble());
            pagos.setPedidoId(random.nextLong());
            pagos.setFechaPago(LocalDateTime.now());

        }
    }



}
