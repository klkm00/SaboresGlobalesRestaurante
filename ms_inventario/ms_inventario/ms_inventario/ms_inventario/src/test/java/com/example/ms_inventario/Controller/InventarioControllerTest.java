package com.example.ms_inventario.Controller;

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

import com.example.ms_inventario.controller.InventarioController;
import com.example.ms_inventario.dto.InventarioRequestDTO;
import com.example.ms_inventario.dto.InventarioResponseDTO;
import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.service.InventarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest({InventarioController.class})
public class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Inventario inventario;

    @BeforeEach
    void SetUp(){
        inventario= new Inventario();
        inventario.setId(1L);
        inventario.setInsumo("Aceite");
        inventario.setStock(24);
    }
    @Test
    void testCreate() throws Exception {
    InventarioRequestDTO requestDTO = new InventarioRequestDTO(
            "Aceite", 
            24
        );

        InventarioResponseDTO responseDTO = new InventarioResponseDTO(
            1L, 
            "Salsa Blanca", 
            24
        );

        when(service.Guardar(any(InventarioRequestDTO.class)))
                    .thenReturn((responseDTO));

        mockMvc.perform(post("/inventario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.insumo").value("Salsa Blanca"))
                .andExpect(jsonPath("$.stock").value(24));
       


    }

    @Test
    void testDelete() throws Exception{
        when(service.ObtenerPorId(1L)).thenReturn(Optional.of(new InventarioResponseDTO()));
    
        doNothing().when(service).eliminar(1L);
    
        mockMvc.perform(delete("/inventario/1"))
                .andExpect(status().isNoContent());
        verify(service,times(1)).eliminar(1L);
    }

    @Test
    void testGetAll() throws Exception{
          InventarioResponseDTO responseDTO = new InventarioResponseDTO(
            1L, 
            "Salsa Blanca", 
            24
        );
        when(service.ObtenerTodos()).thenReturn(List.of(responseDTO));
        mockMvc.perform(get("/inventario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].insumo").value("Salsa Blanca"))
                .andExpect(jsonPath("$[0].stock").value(24));

    }

    @Test
    void testGetById() throws Exception{
          InventarioResponseDTO responseDTO = new InventarioResponseDTO(
            1L, 
            "Salsa Blanca", 
            24
        );
        when(service.ObtenerPorId(1L)).thenReturn(Optional.of(responseDTO));

        mockMvc.perform(get("/inventario/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.insumo").value("Salsa Blanca"))
        .andExpect(jsonPath("$.stock").value(24));
    
    }

    @Test
    void testGetByInsumo() throws Exception{
        when(service.ObtenerPorInsumo("Aceite"))
        .thenReturn(List.of(new InventarioResponseDTO()));
    }

    @Test
    void testUpdate() throws Exception{
         InventarioRequestDTO requestDTO = new InventarioRequestDTO(
            "Aceite", 
            24
        );

        InventarioResponseDTO responseDTO = new InventarioResponseDTO(
            1L, 
            "Salsa Blanca", 
            24
        );

        when(service.Actualizar(any(Long.class),any(InventarioRequestDTO.class)))
                    .thenReturn(Optional.of(responseDTO));

        mockMvc.perform(put("/inventario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.insumo").value("Salsa Blanca"))
                .andExpect(jsonPath("$.stock").value(24));

        verify(service, times(1)).Actualizar(any(Long.class), any(InventarioRequestDTO.class));
    }
}
