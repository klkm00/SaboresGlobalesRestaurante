package com.SaboresGlobales.reportes.Reportes.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.SaboresGlobales.reportes.Reportes.Repository.ReporteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner{
    private final ReporteRepository reporteRepository;


    @Override
    public void run(String... args){
        if (reporteRepository.count() > 0){
            log.info(">>> DataInitializer: la bd ya tiene datos, se omite la carga inicial.");
        }

        log.info(">>> DataInicializer: BD vacia detectada, insertando datos de prueba...");

        
    }

}
