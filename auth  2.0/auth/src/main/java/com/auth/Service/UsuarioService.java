package com.auth.Service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.auth.DTO.UsuarioReponseDTO;
import com.auth.DTO.UsuarioRequestDTO;
import com.auth.Model.Rol;
import com.auth.Model.Usuario;
import com.auth.Repository.RolRepository;
import com.auth.Repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;


import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository uRepository;
    private final RolRepository rolRepository;

    private UsuarioReponseDTO mapToDto(Usuario usuario){
         
        String nombreRol = null;
        if(usuario.getRol() != null){
        nombreRol = usuario.getRol().getNombre();
        }
        return new UsuarioReponseDTO(
            usuario.getId(),
            usuario.getRut(),
            usuario.getNombres(),
            usuario.getApellidos(),
            usuario.getCorreo(),usuario.getContraseña(),usuario.getCodigo_Recuperacion(),
            nombreRol
            
        );
    }

    public List<UsuarioReponseDTO> obtenerTodos(){
        return uRepository.findAll()
            .stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    public Optional <UsuarioReponseDTO> obtenerporId(Long id){
        return uRepository.findById(id).map(this::mapToDto);
    }


    public UsuarioReponseDTO guardar(UsuarioRequestDTO dto){
        Rol rol = rolRepository
                    .findById(dto.getRolId())
                    .orElseThrow(()-> new RuntimeException(
                        "Rol no encontrado con id" + dto.getRolId()
                    ));

        Usuario usuario = new Usuario(
            null,
            dto.getRut(),
            dto.getNombres(),
            dto.getApellidos(),
            dto.getCorreo(), dto.getContraseña(),dto.getCodigo_recuperacion(),
            rol
        );
         if (uRepository.existsByRut(dto.getRut())) {
        throw new RuntimeException("El rut ya existe");
        }

        return mapToDto(uRepository.save(usuario));
    }

    public Optional <UsuarioReponseDTO> actualizar(Long id,UsuarioRequestDTO dto){
        return uRepository.findById(id).map(existente -> {
            Rol rol = rolRepository
            .findById(dto.getRolId()).orElseThrow(() -> new RuntimeException("Rol no encontrado con id"+ dto.getRolId()));
            existente.setRut(dto.getRut()); 
            existente.setNombres(dto.getNombres());
            existente.setApellidos(dto.getApellidos());
            existente.setCorreo(dto.getCorreo());
            existente.setContraseña(dto.getContraseña());
            existente.setCodigo_Recuperacion(dto.getCodigo_recuperacion());
            existente.setRol(rol);
            return mapToDto(uRepository.save(existente));
       
        });


    }



    public void eliminar(Long id){
         uRepository.deleteById(id);
    }
    public List<UsuarioReponseDTO> buscarPorRut(String texto){
        return uRepository.findByRutContainingIgnoreCase(texto)
        .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<UsuarioReponseDTO> buscarPorRol(Long rolId){
        return uRepository.findByRolId(rolId)
        .stream().map(this::mapToDto).collect(Collectors.toList());
    }
    public List<UsuarioReponseDTO> buscarPorNombre(String nombres){
        return uRepository.findByNombresContainingIgnoreCase(nombres)
        .stream().map(this::mapToDto).collect(Collectors.toList());
    }
    
    
}         
