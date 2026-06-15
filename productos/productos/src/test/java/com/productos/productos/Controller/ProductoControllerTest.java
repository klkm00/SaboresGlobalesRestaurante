package com.productos.productos.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productos.productos.DTO.ProductoRequestDTO;
import com.productos.productos.DTO.ProductoResponseDTO;
import com.productos.productos.Modelo.Producto;
import com.productos.productos.Service.ProductoService;

@WebMvcTest({ProductoController.class})
public class ProductoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;
    
    @Autowired
    private ObjectMapper objectMapper;

    private Producto producto;

    @BeforeEach

    void SetUp(){
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Pizza Italiana");
        producto.setDescripcion("Pizza Italiana con peperoni");
        producto.setPrecio(17000);
    }
    @Test
    void testActualizar() throws Exception {
        ProductoRequestDTO requestDTO = new ProductoRequestDTO(
            "Pizza Italiana", 
            "Pizza Italiana con peperoni", 
            17000
        );

        ProductoResponseDTO responseDTO = new ProductoResponseDTO(
            1L, 
            "Pizza Italiana", 
            "Pizza Italiana con peperoni", 
            17000
        );

        when(productoService.actualizar(any(Long.class)
            ,any(ProductoRequestDTO.class)))
            .thenReturn(Optional.of(responseDTO));

        mockMvc.perform(put("/api/v1/productos/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.nombre").value("Pizza Italiana"))
                    .andExpect(jsonPath("$.descripcion").value("Pizza Italiana con peperoni"))
                    .andExpect(jsonPath("$.precio").value(17000));

        verify(productoService, times(1))
                .actualizar(any(Long.class),any(ProductoRequestDTO.class));        

    }

    @Test
    void testCrear() throws Exception{
        ProductoRequestDTO requestDTO = new ProductoRequestDTO(
            "Pizza Italiana", 
            "Pizza Italiana con peperoni", 
            17000
        );


        ProductoResponseDTO responseDTO = new ProductoResponseDTO(
            1L, 
            "Pizza Italiana", 
            "Pizza Italiana con peperoni", 
            17000);

        when(productoService.crear(any(ProductoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Pizza Italiana"))
                .andExpect(jsonPath("$.descripcion").value("Pizza Italiana con peperoni"))
                .andExpect(jsonPath("$.precio").value(17000));   
    }

    @Test
    void testEliminar() throws Exception{
        when(productoService.obtenerPorId(1L))
                .thenReturn(Optional.of(new ProductoResponseDTO()));
        doNothing().when(productoService).eliminar(1L);

        mockMvc.perform(delete("/api/v1/productos/1"))
                .andExpect(status().isNoContent());
        verify(productoService, times(1)).eliminar(1L);
    }

    @Test
    void testObtenerPorId() throws Exception{
        ProductoResponseDTO dto = new ProductoResponseDTO(
            1L, 
            "Pizza Italiana", 
            "Pizza Italiana con peperoni", 
            17000
        );
        when(productoService.obtenerPorId(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/api/v1/productos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("Pizza Italiana"))
            .andExpect(jsonPath("$.descripcion").value("Pizza Italiana con peperoni"))
            .andExpect(jsonPath("$.precio").value(17000));

    }

    @Test
    void testObtenerTodos() throws Exception{
        ProductoResponseDTO dto = new ProductoResponseDTO(
            1L, 
            "Pizza Italiana", 
            "Pizza Italiana con peperoni", 
            17000
        );

        when(productoService.obtenerTodos()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/productos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nombre").value("Pizza Italiana"))
            .andExpect(jsonPath("$[0].descripcion").value("Pizza Italiana con peperoni"))
            .andExpect(jsonPath("$[0].precio").value(17000));
    }
}
