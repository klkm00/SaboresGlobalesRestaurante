package com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Modelo.Rol;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO.UsuarioReponseDTO;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO.UsuarioRequestDTO;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Modelo.Usuario;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Repository.RolRepository;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repo;

    @InjectMocks
    private UsuarioService service;

    @Mock
    private RolRepository rolRepository;

    @Test
    public void testActualizar() {
        Long id = 2L;
        Rol rol = new Rol();
        rol.setId(2L);
        rol.setNombre("CLIENTE");
        
        Usuario usuarioActualizado = new Usuario(id, 
            "12.245.678-9", 
            "Elba",
            "Lazo", 
            "elbalazo@cliente",
            rol
            );

        UsuarioRequestDTO dto = new UsuarioRequestDTO(
            "12.345.678-9", 
            "Elba", 
            "Lazo", 
            "elbalazo@cliente.cl", 
            id);
        when(rolRepository.findById(2L))
        .thenReturn(Optional.of(rol));
        when(repo.findById(id)).thenReturn(Optional.of(new Usuario()));

        when (repo.save(any(Usuario.class)))
                .thenReturn(usuarioActualizado);

        Optional<UsuarioReponseDTO> resultado =
            service.actualizar(id, dto);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        assertEquals("12.245.678-9", resultado.get().getRut());
        
    }

    @Test
    void testBuscarPorNombre() {
        Rol rol = new Rol();
        rol.setId(1L);
        rol.setNombre("CLIENTE");
        String nombre = "Elba";

        Usuario usuario = new Usuario(
            1L, 
            "11.222.333-4", 
            nombre, 
            "Lazo", 
            "elbalazo@cliente.cl", 
            rol);
        when(repo.findByNombresContainingIgnoreCase(nombre))
                .thenReturn(List.of(usuario));
        
        List<UsuarioReponseDTO> dtos =
                service.buscarPorNombre(nombre);

        assertNotNull(dtos);
        assertEquals(1, dtos.size());
        assertEquals(nombre, dtos.get(0).getNombres());



    }

    @Test
    public void testBuscarPorRol() {

    Rol rol = new Rol();
    rol.setId(1L);
    rol.setNombre("CLIENTE");

    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setRut("11.222.333-4");
    usuario.setNombres("Elsa");
    usuario.setApellidos("Pato");
    usuario.setCorreo("elbalazo@cliente.cl");
    usuario.setRol(rol);

    when(repo.findByRolId(1L))
            .thenReturn(List.of(usuario));

    List<UsuarioReponseDTO> resultado =
            service.buscarPorRol(1L);

    assertEquals(1, resultado.size());
    assertEquals(1L,resultado.get(0).getId());
    
    }

    @Test
    public void testBuscarPorRut() {

        Rol rol = new Rol();
        rol.setId(1L);
        rol.setNombre("CLIENTE");
        String rut = "11.222.333-4";

        Usuario usuario = new Usuario(
            1L, 
            rut, 
            "Elba", 
            "Lazo", 
            "elbalazo@cliente.cl", 
            rol);

        when(repo.findByRutContainingIgnoreCase(rut))
                .thenReturn(List.of(usuario));

        List<UsuarioReponseDTO> dto =
            service.buscarPorRut(rut);

        assertNotNull(dto);
        assertEquals(1, dto.size());
        assertEquals(rut, dto.get(0).getRut());

    }

    @Test
    void testEliminar() {
        Long id = 1L;

        doNothing().when(repo).deleteById(id);

        service.eliminar(id);

        verify(repo,times(1)).deleteById(id);

    }

    @Test
    public void testGuardar() {
        
        Rol rol = new Rol();
        rol.setId(2L);
        rol.setNombre("CLIENTE");
        
        UsuarioRequestDTO dto = new UsuarioRequestDTO(
            "11.222.333-4", 
            "Elba",
            "Lazo", 
            "elbalazo@cliente.cl", 
            2L);

        Usuario usuario = new Usuario(
            1L, 
            "11.222.333-4", 
            "Elba",
            "Lazo", 
            "elbalazo@cliente.cl", 
            rol
        );

        when(repo.save(any(Usuario.class)))
                    .thenReturn(usuario);
        when(rolRepository.findById(2L))
            .thenReturn(Optional.of(rol));
        UsuarioReponseDTO resultado = 
                service.guardar(dto);
        
       assertNotNull(resultado);
       assertEquals(1L, resultado.getId());
    }

    @Test
    public void testObtenerTodos() {
        Rol rol = new Rol();
        rol.setId(2L);
        rol.setNombre("CLIENTE");
        Usuario usuario = new Usuario(
            1L,
             "11.222.333-k",
             "Elsa", 
             "Pato", 
             "elsaPato@cliente.cl", 
                rol
             );

        when(repo.findAll()).thenReturn(List.of(usuario));



        List<UsuarioReponseDTO> resultado =
        service.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("11.222.333-k", resultado.get(0).getRut());
            
    }

    @Test
    public void testObtenerporId() {
        Long id = 1L;
        Rol rol = new Rol();
        rol.setId(2L);
        rol.setNombre("CLIENTE");

        Usuario usuario = new Usuario(
            1L,
            "11.222.333-k",
            "Elsa",
            "Pato",
            "elsa@correo.cl",
            rol
            );
   

        when(repo.findById(id))
                .thenReturn(Optional.of(usuario));

        Optional<UsuarioReponseDTO> resultado =
                service.obtenerporId(id);

        assertTrue(resultado.isPresent());
        assertEquals(id,resultado.get().getId());
        assertEquals("11.222.333-k", resultado.get().getRut());
    }
}
