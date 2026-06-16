package com.carrito.sbgl.Controller;

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

import com.carrito.sbgl.DTO.CarritoRequest;
import com.carrito.sbgl.DTO.CarritoResponse;
import com.carrito.sbgl.Model.Carrito;
import com.carrito.sbgl.Service.CarritoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest({CarritoController.class})
public class CarritoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarritoService service;
    @Autowired
    private ObjectMapper objectmapper;
    private Carrito ca;
    
    @BeforeEach
    void setUp(){
        ca = new Carrito();
        ca.setId(1L);
        ca.setId_producto(1L);
        ca.setCantidad(2);
    }

    @Test
    void testObtenerTodos() throws Exception{
        CarritoResponse dto = new CarritoResponse(
            1L, 
            1L, 
            2
        );

        when(service.obtenerTodos()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/carrito"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].id_producto").value(1))
                .andExpect(jsonPath("$[0].cantidad").value(2));

    }

    @Test
    void testActualizar() throws Exception{
        CarritoRequest request = new CarritoRequest(
            1L, 
            2
        );
        CarritoResponse response = new CarritoResponse(
            1L, 
            1L, 
            2
        );
        when(service.actualizar(any(Long.class), any(CarritoRequest.class))).thenReturn(Optional.of(response));
        mockMvc.perform(put("/api/v1/carrito/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectmapper.writeValueAsString(request)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.id_producto").value(1))
                        .andExpect(jsonPath("$.cantidad").value(2));
    }   

    @Test
    void testCrear() throws Exception{
          CarritoRequest request = new CarritoRequest(
            1L, 
            2
        );
        CarritoResponse response = new CarritoResponse(
            1L, 
            1L, 
            2
        );
        when(service.guardar(any(CarritoRequest.class))).thenReturn(response);
        mockMvc.perform(post("/api/v1/carrito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectmapper.writeValueAsString(request)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.id_producto").value(1))
                        .andExpect(jsonPath("$.cantidad").value(2));
    }

    @Test
    void testEliminar() throws Exception{
        when(service.obtenerPorId(1L)).thenReturn(Optional.of(new CarritoResponse()));

        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/v1/carrito/1")).andExpect(status().isNoContent());
    }

    @Test
    void testObtenerporId() throws Exception{
        CarritoResponse response = new CarritoResponse(
            1L, 
            1L, 
            2
        );

        when(service.obtenerPorId(1L)).thenReturn(Optional.of(response));
                mockMvc.perform(get("/api/v1/carrito/1"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.id_producto").value(1))
                        .andExpect(jsonPath("$.cantidad").value(2));
    }
}
