package com.saboresGlobales.Delivery.delivery.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.saboresGlobales.Delivery.delivery.DTO.DeliveryRequestDTO;
import com.saboresGlobales.Delivery.delivery.DTO.DeliveryResponseDTO;
import com.saboresGlobales.Delivery.delivery.Modelo.Delivery;
import com.saboresGlobales.Delivery.delivery.Service.DeliveryService;
@WebMvcTest({DeliveryController.class})
public class DeliveryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Delivery delivery;

    @BeforeEach
    void SetUp(){
        delivery = new Delivery();
        delivery.setRepartidor("Jupiter");
        delivery.setTarifa(1500);
        delivery.setGps("Av.Estrella");
        delivery.setId(1L);
        delivery.setEstado("Entregado");
    }
    @Test
    void testActualizar() throws Exception{

        DeliveryRequestDTO  dto = new DeliveryRequestDTO(
            "Jupiter", 
            1500, 
            "Av.Estrella #12", 
            "Entregado"
        );

        DeliveryResponseDTO dResponseDTO = new DeliveryResponseDTO(
            1L, 
            "Jupiter", 
            1500, 
            "Av.Estrella #12", 
            "Entregado"
        );
        when(service.actualizar(any(Long.class), any(DeliveryRequestDTO.class))).thenReturn(Optional.of(dResponseDTO));

            mockMvc.perform(put("/api/delivery/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.repartidor").value("Jupiter"))
                    .andExpect(jsonPath("$.tarifa").value(1500))
                    .andExpect(jsonPath("$.gps").value("Av.Estrella #12"))
                    .andExpect(jsonPath("$.estado").value("Entregado"));
 
            verify(service, times(1)).actualizar(any(Long.class), any(DeliveryRequestDTO.class));
    }

    @Test
    void testBuscarporRepartidor() throws Exception{
        when(service.buscarporRepartidor("Jupiter"))
            .thenReturn(List.of(new DeliveryResponseDTO()));
    }

    @Test
    void testCrear() throws Exception {
         DeliveryRequestDTO  dto = new DeliveryRequestDTO(
            "Jupiter", 
            1500, 
            "Av.Estrella #12", 
            "Entregado"
        );

        DeliveryResponseDTO dResponseDTO = new DeliveryResponseDTO(
            1L, 
            "Jupiter", 
            1500, 
            "Av.Estrella #12", 
            "Entregado"
        );

        when(service.guardar(any(DeliveryRequestDTO.class))).thenReturn(dResponseDTO);

        mockMvc.perform(post("/api/delivery")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.repartidor").value("Jupiter"))
            .andExpect(jsonPath("$.tarifa").value(1500))
            .andExpect(jsonPath("$.gps").value("Av.Estrella #12"))
            .andExpect(jsonPath("$.estado").value("Entregado"));
    }  

    @Test
    void testEliminar() throws Exception {
        when(service.obtenerPorID(1L)).thenReturn(Optional.of(new DeliveryResponseDTO()));

        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/delivery/1"))
            .andExpect(status().isNoContent());
        
            verify(service, times(1)).eliminar(1L);
  
    }

    @Test
    void testObtenerPorID()throws Exception  {
        DeliveryResponseDTO dResponseDTO = new DeliveryResponseDTO(
            1L, 
            "Jupiter", 
            1500, 
            "Av.Estrella #12", 
            "Entregado"
        );

        when(service.obtenerPorID(1L)).thenReturn(Optional.of(dResponseDTO));

        mockMvc.perform(get("/api/delivery/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.repartidor").value("Jupiter"))
            .andExpect(jsonPath("$.tarifa").value(1500))
            .andExpect(jsonPath("$.gps").value("Av.Estrella #12"))
            .andExpect(jsonPath("$.estado").value("Entregado"));
    }

    @Test
    void testObtenerTodos() throws Exception {
        DeliveryResponseDTO dResponseDTO = new DeliveryResponseDTO(
            1L, 
            "Jupiter", 
            1500, 
            "Av.Estrella #12", 
            "Entregado"
        );
        when(service.obtenDeliveryRequestDTOs()).thenReturn(List.of(dResponseDTO));
        mockMvc.perform(get("/api/delivery"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].repartidor").value("Jupiter"))
            .andExpect(jsonPath("$[0].tarifa").value(1500))
            .andExpect(jsonPath("$[0].gps").value("Av.Estrella #12"))
            .andExpect(jsonPath("$[0].estado").value("Entregado"));
    }
}
