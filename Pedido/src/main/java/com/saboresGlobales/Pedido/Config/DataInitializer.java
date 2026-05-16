package com.saboresGlobales.Pedido.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.saboresGlobales.Pedido.Model.Orden;
import com.saboresGlobales.Pedido.Model.Pedido;
import com.saboresGlobales.Pedido.Repository.OrdenRepository;
import com.saboresGlobales.Pedido.Repository.PedidoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner{
    private final OrdenRepository ordenRepository;
    private final PedidoRepository pedidoRepository;

    @Override
    public void run(String... args){
        if(ordenRepository.count()> 0){
            log.info(">>> DataInitializer: La bd ya tiene datos, se omite la carga inicial");
            return;
        }
        log.info(">>> DataInitializer: bd vacia detectada, insertando datos de prueba...");
    
        Orden orden = ordenRepository.save(
            new Orden(null, "Empanadas", "cantidad: 12, sabor: queso")
        );
        Orden orden2 = ordenRepository.save( new Orden(null, "Tacos", "cantidad: 8, sabor: carnitas"));

        pedidoRepository.save(new Pedido(null, "Listo", orden));
        pedidoRepository.save(new Pedido(null, "En Preparacion", orden2));
    }

   

}
