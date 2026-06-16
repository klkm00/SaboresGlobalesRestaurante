package com.auth.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.auth.DTO.RolRequesrDTO;
import com.auth.DTO.RolResponseDTO;
import com.auth.Model.Rol;
import com.auth.Repository.RolRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor

@Service
public class RolService {
    private final RolRepository rolRepository;

    public List<RolResponseDTO> obtenerTodas(){
        return rolRepository.findAll().stream()
                .map(rol -> new RolResponseDTO(rol.getId(), rol.getNombre()))
                .collect(java.util.stream.Collectors.toList());
    }

    public Optional<RolResponseDTO> obtenerPorId(Long id){
        return rolRepository.findById(id).map(rol -> new RolResponseDTO(rol.getId(), rol.getNombre()));
    }

    public RolResponseDTO guardar(RolRequesrDTO dto){
        Rol rol = new Rol();
        rol.setNombre(dto.getNombre());

        Rol nuevoRol = rolRepository.save(rol);

        return new RolResponseDTO(nuevoRol.getId(), nuevoRol.getNombre());
    }
        public void eliminar(Long id){
            rolRepository.deleteById(id);



        }

        public Optional<RolResponseDTO> actualizar(Long id, RolRequesrDTO dto){
            return rolRepository.findById(id).map(rol -> {
                rol.setNombre(dto.getNombre());
                Rol rolActualizado = rolRepository.save(rol);
                return new RolResponseDTO(rolActualizado.getId(), rolActualizado.getNombre());
            });

            
        }
          
    
    
    }
