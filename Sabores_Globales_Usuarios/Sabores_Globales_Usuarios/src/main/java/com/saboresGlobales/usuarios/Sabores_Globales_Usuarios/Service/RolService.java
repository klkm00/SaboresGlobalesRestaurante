package com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Modelo.Rol;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Repository.RolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {
    private final RolRepository rolRepository;

    public List<Rol> obtenerTodas(){
        return rolRepository.findAll();
    }

    public Optional<Rol> obtenerPorId(Long id){
        return rolRepository.findById(id);
    }

    public Rol save(Rol rol){
        return rolRepository.save(rol);
    }
        public void eliminar(Long id){
            rolRepository.deleteById(id);
        }
}
