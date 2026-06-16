package com.auth;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.auth.Model.Rol;
import com.auth.Model.Usuario;
import com.auth.Repository.RolRepository;
import com.auth.Repository.UsuarioRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{
    @Autowired
    private UsuarioRepository repository;

    @Autowired 
    private RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        Faker faker = new Faker();
        Random random = new Random();
        List<Rol> roles = rolRepository.findAll();

        for (int i = 0; i < 3; i++) {
            Usuario usuario = new Usuario();
            usuario.setRut(faker.book().genre());
            usuario.setNombres(faker.book().genre());
            usuario.setApellidos(faker.book().genre());
            usuario.setCorreo(faker.book().genre());
            usuario.setContraseña(faker.book().genre());
            usuario.setCodigo_Recuperacion(faker.book().genre());
            usuario.setRol(roles.get(random.nextInt(roles.size())));
        }


        for (int i = 0; i < 3; i++) {
            Rol rol = new Rol();
            rol.setNombre(faker.book().genre());
        }
    }

}
