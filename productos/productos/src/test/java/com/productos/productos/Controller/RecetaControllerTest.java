package com.productos.productos.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
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
import com.productos.productos.DTO.RecetaRequestDTO;
import com.productos.productos.DTO.RecetaResponseDTO;
import com.productos.productos.Modelo.Producto;
import com.productos.productos.Modelo.Receta;
import com.productos.productos.Service.ProductoService;
import com.productos.productos.Service.RecetaService;
@WebMvcTest({RecetaController.class})
public class RecetaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecetaService service;
    @MockBean
    private ProductoService productoService;
    @Autowired
    private ObjectMapper objectMapper;

    private Receta receta;
    private Producto producto;
    

    @BeforeEach
    void Setup(){
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Pizza Italiana");
        producto.setDescripcion("Pizza Italiana con peperoni");
        producto.setPrecio(17000);
    }

    @BeforeEach
    void SetUp1(){
        receta = new Receta();
        receta.setId(1L);
        receta.setNombreInsumo("Tomate");
        receta.setCantidad(10);
        receta.setProducto(producto);
    }
    @Test
    void testActualizar() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Pizza Italiana");

        RecetaRequestDTO requestDTO = new RecetaRequestDTO(
            "Tomate", 
            10, 
            1L
        );

        RecetaResponseDTO recetaResponseDTO = new RecetaResponseDTO(
            1L, 
            "Tomate", 
            20, 
            "Pizza Italiana"
        );

        when(service.actualizar(any(Long.class),
                    any(RecetaRequestDTO.class)))
                    .thenReturn(Optional.of(recetaResponseDTO));

        mockMvc.perform(put("/api/v1/recetas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreInsumo").value("Tomate"))
                .andExpect(jsonPath("$.cantidad").value(20))
                .andExpect(jsonPath("$.productoNombre").value("Pizza Italiana"));
        verify(service, times(1)).actualizar(any(Long.class), any(RecetaRequestDTO.class));

    }

    @Test
    void testBuscarPorNombreInsumo() throws Exception{
        when(service.obtenerRecetasPorNombreInsumo("Tomate"))
                .thenReturn(List.of(new RecetaResponseDTO()));
    }

    @Test
    void testEliminar() throws Exception {
        when(service.obtenerPorId(1L))
                .thenReturn(Optional.of(new RecetaResponseDTO()));

        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/v1/recetas/1"))
            .andExpect(status().isNoContent());
        
            verify(service, times(1)).eliminar(1L);
    }

    @Test
    void testGuardar() throws Exception{
        RecetaRequestDTO recetaRequestDTO = new RecetaRequestDTO(
            "Tomate", 
            10, 
            1L
        );

        RecetaResponseDTO recetaResponseDTO = new RecetaResponseDTO(
            1L, 
            "Tomate", 
            10, 
            "Pizza Italiana"
        );

        when(service.guardar(any(RecetaRequestDTO.class))).thenReturn(recetaResponseDTO);
        
        mockMvc.perform(post("/api/v1/recetas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(recetaRequestDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombreInsumo").value("Tomate"))
            .andExpect(jsonPath("$.cantidad").value(10))
            .andExpect(jsonPath("$.productoNombre").value("Pizza Italiana"));
            
    }

    @Test
    void testObtenerPorId() throws  Exception {
        RecetaResponseDTO dto = new RecetaResponseDTO(
            1L, 
            "Tomate", 
            10, 
            "Pizza Italiana"
        );
        
        when(service.obtenerPorId(1L)).thenReturn(Optional.of(dto));
            
        mockMvc.perform(get("/api/v1/recetas/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombreInsumo").value("Tomate"))
            .andExpect(jsonPath("$.cantidad").value(10))
            .andExpect(jsonPath("$.productoNombre").value("Pizza Italiana"));
            
    }

    @Test
    void testObtenerTodas() throws Exception {
        RecetaResponseDTO dto = new RecetaResponseDTO(
            1L, 
            "Tomate", 
            10, 
            "Pizza Italiana"
        );

        when(service.obtenerTodas()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/recetas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nombreInsumo").value("Tomate"))
            .andExpect(jsonPath("$[0].cantidad").value(10))
            .andExpect(jsonPath("$[0].productoNombre").value("Pizza Italiana"));
            
    }

    @Test
    void testObtenerPorProductoID(){
        when(service.buscarPorProductoID(1L))
        .thenReturn(List.of(new RecetaResponseDTO()));
    }
}
