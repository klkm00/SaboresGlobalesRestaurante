package com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Service;

import java.util.List;


import org.springframework.stereotype.Service;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO.UsuarioRequestDTO;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO.UsuarioReponseDTO;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Modelo.Usuario;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Modelo.Rol;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Repository.RolRepository;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;


import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository uRepository;
    private final RolRepository rolRepository;

    private UsuarioReponseDTO mapToDto(Usuario usuario){
        return new UsuarioReponseDTO(
            usuario.getId(),
            usuario.getRut(),
            usuario.getNombres(),
            usuario.getApellidos(),
            usuario.getCorreo(),
            usuario.getRol().getNombre()
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
            dto.getCorreo(),
            rol
        );
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
    public List<UsuarioReponseDTO> buscarPorNombre(String texto){
        return uRepository.findByNombresContainingIgnoreCase(texto)
        .stream().map(this::mapToDto).collect(Collectors.toList());
    }
    
}         
