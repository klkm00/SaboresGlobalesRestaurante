package com.productos.productos.Service;

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

import com.productos.productos.DTO.ProductoRequestDTO;
import com.productos.productos.DTO.ProductoResponseDTO;
import com.productos.productos.Modelo.Producto;
import com.productos.productos.Repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void testActualizar() {
        Long id = 1L;
        
        Producto productoActualizado = new Producto(
            id, 
            "Pizza Italiana", 
            "Pizza Italiana con peperoni", 
            17000);

        ProductoRequestDTO dto = new ProductoRequestDTO(
            "Pizza Napolitana", 
            "Pizza Napolitana con Aceitunas", 
            17500);

        when(productoRepository.findById(id))
        .thenReturn(Optional.of(new Producto()));

        when(productoRepository.save(any(Producto.class)))
                .thenReturn(productoActualizado);

        Optional<ProductoResponseDTO> optional =
            productoService.actualizar(id,dto);

        assertNotNull(productoActualizado);
        assertEquals(id,optional.get().getId());
        assertEquals("Pizza Italiana", optional.get().getNombre());
    }

    @Test
    void testBuscarPorNombre() {
        String nombre = "Pizza Italiana";
        Producto producto = new Producto(
            1L, 
            nombre, 
            "Pizza Italiana con peperoni", 
            1
        );

        when(productoRepository.findByNombreContainingIgnoreCase(nombre))
                .thenReturn(List.of(producto));

        List<ProductoResponseDTO> dtos = productoService.buscarPorNombre(nombre);

        assertNotNull(dtos);
        assertEquals(1, dtos.size());
        assertEquals(nombre, dtos.get(0).getNombre());

    }

    @Test
    void testCrear() {
        ProductoRequestDTO dto = new ProductoRequestDTO(
            "Pizza Italiana", 
            "Pizza Italiana con peperoni", 
            17000);

        Producto producto = new Producto(
            1L, 
            "Pizza Italiana", 
            "Pizza Italiana con peperoni", 
            17000);

        when(productoRepository.save(any(Producto.class)))
                .thenReturn(producto);
        ProductoResponseDTO dto2 = 
            productoService.crear(dto);

        assertNotNull(dto2);
        assertEquals(1L, dto2.getId());
        assertEquals("Pizza Italiana", dto2.getNombre());
    }

    @Test
    void testEliminar() {
        Long id = 1L;
        
        doNothing().when(productoRepository).deleteById(id);

        productoService.eliminar(id);

        verify(productoRepository, 
            times(1))
            .deleteById(id);
    }

    @Test
    void testObtenerPorId() {
        Long id = 1L;
        Producto producto = new Producto(
            id,
            "Pizza Italiana", 
             "Pizza Italiana con peperoni", 
             17000
            );
        when(productoRepository.findById(id))
                .thenReturn(Optional.of(producto));
        Optional<ProductoResponseDTO> dto = 
            productoService.obtenerPorId(id);

        assertTrue(dto.isPresent());
        assertEquals(id, dto.get().getId());
        assertEquals("Pizza Italiana", dto.get().getNombre());
    }

    @Test
    void testObtenerTodos() {
        Producto producto = new Producto(
            1L, 
            "Pizza Italiana", 
            "Pizza Italiana con peperoni", 
            17000
        );

        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<ProductoResponseDTO> resultado = productoService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pizza Italiana", resultado.get(0).getNombre());
    }
}
