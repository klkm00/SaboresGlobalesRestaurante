package com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Modelo.Rol;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Modelo.Usuario;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Repository.RolRepository;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

        private final RolRepository rolRepository;
        private final UsuarioRepository usuarioRepository;


        @Override
        public void run(String... args){
            if(rolRepository.count()>0){
                log.info(">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial");
                return;
            }

            log.info(">>> DataInitializer: BD vacia detectada, insertando datos de prueba...");
            
            Rol prog = rolRepository.save(
                new Rol(null, "Administrador" )
            );
            Rol cliente = rolRepository.save(
                new Rol(null, "Cliente")
            );

             Rol emp = rolRepository.save(
                    new Rol(null, "Empleado"));
                   
                   
            usuarioRepository.save(new Usuario(null, "111.222.333-4", "Maria", "Primera", "m.primera@admin.cl", prog));
            usuarioRepository.save(new Usuario(null, "111.222.333-4", "Armando", "Casas", "m.primera@emp.cl", emp));
            usuarioRepository.save(new Usuario(null, "111.222.333-4", "Susana", "Oria", "m.primera@cliente.cl", cliente));


                  
        
        }
    
}
