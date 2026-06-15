package com.saboresGlobales.Pedido.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;
import java.util.Optional;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saboresGlobales.Pedido.DTO.OrdenRequestDTO;
import com.saboresGlobales.Pedido.DTO.OrdenResponseDTO;
import com.saboresGlobales.Pedido.Model.Orden;
import com.saboresGlobales.Pedido.Service.OrdenService;
@WebMvcTest({OrdenController.class})
public class OrdenControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdenService service;

    @Autowired
    private ObjectMapper mapper;

    private Orden orden;

    @BeforeEach
    void SetUp(){
        orden = new Orden();
        orden.setId(1L);
        orden.setOrden("Empanadas");
        orden.setDescripcion("Cantidad:10, Sabor: Queso");
    }
    @Test
    void testActualizar() throws Exception{
        OrdenRequestDTO ordenRequestDTO = new OrdenRequestDTO(
            "Empanadas", 
            "Cantidad:12, Sabor: Queso"
        );

        OrdenResponseDTO ordenResponseDTO = new OrdenResponseDTO(
            1L, 
            "Empanadas", 
            "Cantidad:12, Sabor: Queso"
        );

        when(service.actualizar(any(Long.class), any(OrdenRequestDTO.class))).thenReturn(Optional.of(ordenResponseDTO));
        mockMvc.perform(put("/api/v1/ordenes/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(mapper.writeValueAsString(ordenRequestDTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.orden").value("Empanadas"))
               .andExpect(jsonPath("$.descripcion").value("Cantidad:12, Sabor: Queso"));
    }

    @Test
    void testCrear() throws Exception{
        OrdenRequestDTO requestDTO = new OrdenRequestDTO(
          "Empanadas", 
          "Cantidad:12, Sabor: Queso"
        );
        OrdenResponseDTO ordenResponseDTO = new OrdenResponseDTO(
            1L, 
            "Empanadas", 
            "Cantidad:12, Sabor: Queso"
        );

        when(service.guardar(any(OrdenRequestDTO.class))).thenReturn(ordenResponseDTO);

        mockMvc.perform(post("/api/v1/ordenes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orden").value("Empanadas"))
                .andExpect(jsonPath("$.descripcion").value("Cantidad:12, Sabor: Queso"));         
        
    }

    @Test
    void testEliminar() throws Exception{
        when(service.obtenerPorId(1L)).thenReturn(Optional.of(new OrdenResponseDTO()));

        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/v1/ordenes/1")).andExpect(status().isNoContent());
        
    }

    @Test
    void testObtenerTodos() throws Exception{
        OrdenResponseDTO dto = new OrdenResponseDTO(
            1L, 
            "Empanadas", 
            "Cantidad:12, Sabor: Queso"
        );
        when(service.obtenerTodos()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/ordenes"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1))
               .andExpect(jsonPath("$[0].orden").value("Empanadas"))
               .andExpect(jsonPath("$[0].descripcion").value("Cantidad:12, Sabor: Queso"));         
               
    }

    @Test
    void testObtenerporId() throws Exception{
        OrdenResponseDTO dto = new OrdenResponseDTO(
            1L, 
            "Empanadas", 
            "Cantidad:12, Sabor: Queso"
        );

        when(service.obtenerPorId(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/api/v1/ordenes/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.orden").value("Empanadas"))
               .andExpect(jsonPath("$.descripcion").value("Cantidad:12, Sabor: Queso"));         
                   

    }
}
