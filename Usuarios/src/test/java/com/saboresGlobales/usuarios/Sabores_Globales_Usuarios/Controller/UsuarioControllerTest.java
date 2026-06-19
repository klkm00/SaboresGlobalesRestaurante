package com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO.UsuarioReponseDTO;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO.UsuarioRequestDTO;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Modelo.Rol;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Modelo.Usuario;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Service.UsuarioService;

@WebMvcTest({UsuarioController.class})
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;
    private Rol rol;

    @BeforeEach
    void SetUp(){
    rol= new Rol();
    rol.setId(1L);
    rol.setNombre("CLIENTE");
    }

    @BeforeEach
    void SetUp1(){
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setRut("11.222.333-4");
        usuario.setNombres("Elba");
        usuario.setApellidos("Lazo");
        usuario.setCorreo("elbalazo@cliente.cl");
        usuario.setRol(rol);
    }
   @Test
    void testActualizar() throws Exception {
        Rol rol = new Rol();
        rol.setId(1L);
    UsuarioRequestDTO request = new UsuarioRequestDTO(
         "11.222.333-4", 
         "Elba", 
         "Lazo",
         null, 
         1L);

    UsuarioReponseDTO response = new UsuarioReponseDTO(
            1L,
            "12.345.678-9",
            "Juan",
            "Perez",
            "juanperez@cliente.cl", "CLIENTE"
    );

    when(usuarioService.actualizar(any(Long.class),
            any(UsuarioRequestDTO.class)))
            .thenReturn(Optional.of(response));

    mockMvc.perform(put("/api/v1/usuarios/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.rut").value("12.345.678-9"))
            .andExpect(jsonPath("$.nombres").value("Juan"))
            .andExpect(jsonPath("$.apellidos").value("Perez"))
            .andExpect(jsonPath("$.correo").value("juanperez@cliente.cl"))
            .andExpect(jsonPath("$.rol").value("CLIENTE"))
            ;

    verify(usuarioService, times(1))
            .actualizar(any(Long.class),
                    any(UsuarioRequestDTO.class));
}
    @Test
    void testBuscarPorNombre() throws Exception {
     when(usuarioService.buscarPorNombre("Elba"))
        .thenReturn(List.of(new UsuarioReponseDTO()));
    }

    @Test
    void testBuscarPorRol() {
        when(usuarioService.buscarPorRol(1L))
        .thenReturn(List.of(new UsuarioReponseDTO()));
    }

    @Test
    void testBuscarPorRut() {
       when(usuarioService.buscarPorRut("12.345.678-9"))
        .thenReturn(List.of(new UsuarioReponseDTO())); 
    }

  @Test
    void testCrear() throws Exception {

    UsuarioRequestDTO request = new UsuarioRequestDTO(
            "12.345.678-9",
            "Elba",
            "Lazo", "elbalazo@clientes.cl", 1L
    );

    UsuarioReponseDTO response = new UsuarioReponseDTO(
            1L,
            "12.345.678-9",
            "Elba",
            "Lazo", 
            "elbalazo@clientes.cl", 
            "CLIENTE"
        );

    when(usuarioService.guardar(any(UsuarioRequestDTO.class)))
            .thenReturn(response);

    mockMvc.perform(post("/api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.rut").value("12.345.678-9"))
            .andExpect(jsonPath("$.nombres").value("Elba"))
            .andExpect(jsonPath("$.correo").value("elbalazo@clientes.cl"))
            .andExpect(jsonPath("$.rol").value("CLIENTE"));
}

    @Test
    void testEliminar()throws Exception {
        when(usuarioService.obtenerporId(1L))
                .thenReturn(Optional.of(new UsuarioReponseDTO()));
        doNothing().when(usuarioService).eliminar(1L);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
        verify(usuarioService, times(1)).eliminar(1L);
    }

    @Test
    void testObtenerPorId() throws Exception{
        UsuarioReponseDTO dto = new UsuarioReponseDTO(
            1L, 
            "12.345.678-9", 
            "Elba", 
            "Lazo", 
            "elbalazo@clientes.cl", 
            "CLIENTE");
        when(usuarioService.obtenerporId(1L))
                .thenReturn(Optional.of(dto));
        mockMvc.perform(get("/api/v1/usuarios/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.rut").value("12.345.678-9"))
            .andExpect(jsonPath("$.nombres").value("Elba"))
            .andExpect(jsonPath("$.correo").value("elbalazo@clientes.cl"))
            .andExpect(jsonPath("$.rol").value("CLIENTE"));

    }

    @Test
    void testObtenerTodos() throws Exception{

        UsuarioReponseDTO dto = new UsuarioReponseDTO(
            1L, 
            "12.345.678-9", 
            "Elba", 
            "Lazo", 
            "elbalazo@clientes.cl", 
            "CLIENTE");

        when(usuarioService.obtenerTodos())
                .thenReturn(List.of(dto));
                
        mockMvc.perform(get("/api/v1/usuarios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].rut").value("12.345.678-9"))
            .andExpect(jsonPath("$[0].nombres").value("Elba"))
            .andExpect(jsonPath("$[0].correo").value("elbalazo@clientes.cl"))
            .andExpect(jsonPath("$[0].rol").value("CLIENTE"));

    }
}
