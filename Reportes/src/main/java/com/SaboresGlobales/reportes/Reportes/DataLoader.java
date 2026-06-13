package com.SaboresGlobales.reportes.Reportes;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.SaboresGlobales.reportes.Reportes.Modelo.Reporte;
import com.SaboresGlobales.reportes.Reportes.Repository.ReporteRepository;

import net.datafaker.Faker;


@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {


    @Autowired
    private ReporteRepository repository;

    @Override

    public void run(String... args) throws Exception{
        Faker faker = new Faker();
        Random  random = new Random();


        //GENERAR TIPOS DE REPORTES

        for (int i =0;i <3; i++){
            Reporte reporte = new Reporte();
            reporte.setPedido(faker.book().genre());
            reporte.setInventario(faker.book().hashCode());
            reporte.setPagos(faker.book().hashCode());
            reporte.setDelivery(faker.book().genre());
            repository.save(reporte);

        }
    }


}
