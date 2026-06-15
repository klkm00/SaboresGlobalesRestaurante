package com.saboresGlobales.Pedido.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import com.saboresGlobales.Pedido.DTO.PedidoRequestDTO;
import com.saboresGlobales.Pedido.DTO.PedidoResponseDTO;
import com.saboresGlobales.Pedido.Model.Orden;
import com.saboresGlobales.Pedido.Model.Pedido;
import com.saboresGlobales.Pedido.Service.OrdenService;
import com.saboresGlobales.Pedido.Service.PedidoService;

@WebMvcTest({PedidoController.class})
public class PedidoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @MockBean
    private OrdenService ordenService;

    @Autowired
    private ObjectMapper object;

    private Orden orden;
    private Pedido pedido;

    @BeforeEach
    void SetUp(){
        orden = new Orden();
        orden.setId(1L);
        orden.setOrden("Empanadas");
        orden.setDescripcion("Cantidad:10 , Sabor: Queso");
    }

    @BeforeEach
    void SetUp2(){
        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setEstado("Listo");
        pedido.setOrden(orden);
    }
     
    @Test
    void testActualizar() throws Exception{
        Orden orden = new Orden(
            1L, 
            "Empanadas", 
            "Cantidad: 10, Sabor: Queso"
        );

        PedidoRequestDTO requestDTO = new PedidoRequestDTO(
            "Listo",
            1L
        ); 

        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO(
            1L, 
            "Listo", 
            "Empanadas"
        );

        when(pedidoService.actualizar(any(Long.class),any(PedidoRequestDTO.class))).thenReturn(Optional.of(pedidoResponseDTO));

        mockMvc.perform(put("/api/v1/pedidos/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(object.writeValueAsString(requestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.estado").value("Listo"))   
                    .andExpect(jsonPath("$.orden").value("Empanadas")); 
        verify(pedidoService, times(1)).actualizar(any(Long.class), any(PedidoRequestDTO.class));


    }

    @Test
    void testBuscarPorOrden() {
        when(pedidoService.buscarPorOrden(1L))
                .thenReturn(List.of(new PedidoResponseDTO()));
    }

    @Test
    void testCreate() throws Exception{
         PedidoRequestDTO requestDTO = new PedidoRequestDTO(
            "Listo",
            1L
        ); 

        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO(
            1L, 
            "Listo", 
            "Empanadas"
        );

        when(pedidoService.guardar(any(PedidoRequestDTO.class))).thenReturn(pedidoResponseDTO);

        mockMvc.perform(post("/api/v1/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(object.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("Listo"))   
                .andExpect(jsonPath("$.orden").value("Empanadas")); 
   
    }

    @Test
    void testEliminar() throws Exception{
        when(pedidoService.obtenerPorId(1L))
                .thenReturn(Optional.of(new PedidoResponseDTO()));

        doNothing().when(pedidoService).elimnar(1L);

        mockMvc.perform(delete("/api/v1/pedidos/1")).andExpect(status().isNoContent());

        verify(pedidoService, times(1)).elimnar(1L);
    }

    @Test
    void testObtenerTodos() throws Exception{
        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO(
            1L, 
            "Listo", 
            "Empanadas"
        );
        
        when(pedidoService.obtenerTodos()).thenReturn(List.of(pedidoResponseDTO));

        mockMvc.perform(get("/api/v1/pedidos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].estado").value("Listo"))   
            .andExpect(jsonPath("$[0].orden").value("Empanadas")); 
   
    }

    @Test
    void testObtenerporId() throws Exception{
        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO(
            1L, 
            "Listo", 
            "Empanadas"
        );

        when(pedidoService.obtenerPorId(1L)).thenReturn(Optional.of(pedidoResponseDTO));

        mockMvc.perform(get("/api/v1/pedidos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.estado").value("Listo"))   
            .andExpect(jsonPath("$.orden").value("Empanadas")); 
    }
}
