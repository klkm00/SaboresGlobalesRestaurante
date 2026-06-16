package com.auth.Controller;

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

import com.auth.DTO.RolRequesrDTO;
import com.auth.DTO.RolResponseDTO;
import com.auth.Model.Rol;
import com.auth.Service.RolService;
import com.fasterxml.jackson.databind.ObjectMapper;
@WebMvcTest({RolController.class})
public class RolControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    @Autowired
    private ObjectMapper objectMapper;

    private Rol rol;
    @BeforeEach
    void SetUp(){
        rol = new Rol();
        rol.setId(1L);
        rol.setNombre("CLIENTE");
    }
   @Test
    void testActualizar() throws Exception {

    RolRequesrDTO request = new RolRequesrDTO("ADMIN");

    RolResponseDTO response = new RolResponseDTO(
            1L,
            "ADMIN");

    when(rolService.actualizar(any(Long.class), any(RolRequesrDTO.class)))
            .thenReturn(Optional.of(response));

    mockMvc.perform(put("/api/v1/roles/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("ADMIN"));

    verify(rolService, times(1))
            .actualizar(any(Long.class), any(RolRequesrDTO.class));
}

    @Test
    void testCrear() throws Exception {
        RolRequesrDTO request = new RolRequesrDTO("CLIENTE");

        RolResponseDTO response = new RolResponseDTO(
            1L,
             "CLIENTE");

        when(rolService.guardar(any(RolRequesrDTO.class)))
                .thenReturn(response);
        mockMvc.perform(post("/api/v1/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("CLIENTE"));
    }

    @Test
    void testEliminar() throws Exception {
       when(rolService.obtenerPorId(1L))
            .thenReturn(java.util.Optional.of(new RolResponseDTO()));

        doNothing().when(rolService).eliminar(1L);

        mockMvc.perform(delete("/api/v1/roles/1"))
                .andExpect(status().isNoContent());
        verify(rolService, times(1)).eliminar(1L);
    }

    @Test
    void testListar() throws Exception{
        RolResponseDTO dto = new RolResponseDTO(
            1L, 
            "CLIENTE");

        when(rolService.obtenerTodas())
                .thenReturn(List.of(dto));
        mockMvc.perform(get("/api/v1/roles"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1))
               .andExpect(jsonPath("$[0].nombre").value("CLIENTE"));
    }

    @Test
    public void testObtenerPorId() throws Exception {
        RolResponseDTO dto = new RolResponseDTO(
            1L, 
            "CLIENTE"
        );

        when(rolService.obtenerPorId(1L))
            .thenReturn(java.util.Optional.of(dto));

        mockMvc.perform(get("/api/v1/roles/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.nombre").value("CLIENTE"));
    }
}
