package com.saboresGlobales.Pedido;

import java.util.Random;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;

import com.saboresGlobales.Pedido.Model.Orden;
import com.saboresGlobales.Pedido.Model.Pedido;
import com.saboresGlobales.Pedido.Repository.OrdenRepository;
import com.saboresGlobales.Pedido.Repository.PedidoRepository;

import net.datafaker.Faker;

@Profile("dev")
public class DataLoader implements CommandLineRunner{
    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired PedidoRepository pedidoRepository;

    @Override
    public void run(String... args) throws Exception{

       Faker faker = new Faker();
       Random random =new Random();

       List<Orden> list = ordenRepository.findAll();
       
       for (int i = 0; i < 3; i++) {
        Orden orden = new Orden();
        orden.setOrden(faker.book().genre());
        orden.setDescripcion(faker.book().genre());
       }

       for (int i = 0; i <3; i++) {
        Pedido pedido = new Pedido();
        pedido.setEstado(faker.book().genre());
        pedido.setOrden(list.get(random.nextInt(list.size())));
       }
       
    }
}
