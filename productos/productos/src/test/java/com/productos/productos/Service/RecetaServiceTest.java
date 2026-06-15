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

import com.productos.productos.DTO.RecetaRequestDTO;
import com.productos.productos.DTO.RecetaResponseDTO;
import com.productos.productos.Modelo.Producto;
import com.productos.productos.Modelo.Receta;
import com.productos.productos.Repository.ProductoRepository;
import com.productos.productos.Repository.RecetaRepository;
@ExtendWith(MockitoExtension.class)
public class RecetaServiceTest {
    @Mock
    private RecetaRepository repository;

    @InjectMocks
    private RecetaService service;

    @Mock
    private ProductoRepository productoRepository;
    @Test
    void testActualizar() {
        
        Long id = 2L;
        Producto producto = new Producto();
        producto.setId(2L);
        producto.setNombre("Pizza Italiana");
        producto.setDescripcion("Pizza Italiana con peperoni");
        producto.setPrecio(17000);

        Receta receta = new Receta(
            id, 
            "Tomate", 
            10, 
            producto);

        RecetaRequestDTO dto = new RecetaRequestDTO(
            "Tomate", 
            10, 
            id);


        when(productoRepository.findById(2L))
            .thenReturn(Optional.of(producto));

        when(repository.findById(id)).thenReturn(Optional.of(new Receta()));

        when(repository.save(any(Receta.class)))
                .thenReturn(receta);

        Optional<RecetaResponseDTO> optional = 
            service.actualizar(id, dto);

        assertTrue(optional.isPresent());
        assertEquals(id, optional.get().getId());
        assertEquals("Tomate", optional.get().getNombreInsumo());
        
    }

    @Test
    void testEliminar() {
        Long id = 1L;

        doNothing().when(repository).deleteById(id);

        service.eliminar(id);

        verify(repository,times(1)).deleteById(id);

    }

    @Test
    void testGuardar() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Pizza Italiana");
        producto.setDescripcion("Pizza Italiana con peperoni");
        producto.setPrecio(17000);

        RecetaRequestDTO dto = new RecetaRequestDTO(
            "Tomate", 
            10, 
            1L
        );
        Receta receta = new Receta(
            1L, 
            "Tomate", 
            10, 
            producto
        );
        when(repository.save(any(Receta.class)))
            .thenReturn(receta);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        RecetaResponseDTO responseDTO = service.guardar(dto);
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());


    }

    @Test
    void testObtenerPorId() {
        Long id = 1L;
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Pizza Italiana");
        producto.setDescripcion("Pizza Italiana con peperoni");
        producto.setPrecio(17000);

        Receta receta = new Receta(
            1L, 
            "Tomate", 
            10, 
            producto
        );

        when(repository.findById(id)).thenReturn(Optional.of(receta));

        Optional<RecetaResponseDTO> optional = service.obtenerPorId(id);

        assertTrue(optional.isPresent());
        assertEquals(id, optional.get().getId());
    }

    @Test
    void testObtenerRecetasPorNombreInsumo() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Pizza Italiana");
        producto.setDescripcion("Pizza Italiana con peperoni");        
        producto.setPrecio(17000);

        String nombre = "Tomate";

        Receta r3Receta = new Receta(
            1L, 
            nombre, 
            10, 
            producto
        );

        when(repository.findByNombreInsumoContainsIgnoreCase(nombre))
            .thenReturn(List.of(r3Receta));

        List<RecetaResponseDTO>dtos = service.obtenerRecetasPorNombreInsumo(nombre);

        assertNotNull(dtos);
        assertEquals(1, dtos.size());
        assertEquals(nombre, dtos.get(0).getNombreInsumo());
    }

    @Test
    void testObtenerRecetasPorProductoId() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Pizza Italiana");
        producto.setDescripcion("Pizza Italiana con peperoni");
        producto.setPrecio(17000);

        Receta receta = new Receta();
        receta.setId(1L);
        receta.setNombreInsumo("Tomate");
        receta.setCantidad(17000);
        receta.setProducto(producto);

        when(repository.findByProductoId(1L))
                    .thenReturn(List.of(receta));

        List<RecetaResponseDTO> resultado = service.obtenerRecetasPorProductoId(1L);

        assertEquals(1, resultado.size());
        assertEquals("Tomate", resultado.get(0).getNombreInsumo());
    }

    @Test
    void testObtenerTodas() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Pizza Italiana");
        producto.setDescripcion("Pizza Italiana con peperoni");
        producto.setPrecio(17000);

        Receta receta = new Receta(
            1L, 
            "Tomate", 
            10, 
            producto
        ); 

        when(repository.findAll()).thenReturn(List.of(receta));

        List<RecetaResponseDTO> list = service.obtenerTodas();

        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).getId());

    }

    @Test
    void testObtenerPorProductoID(){
        Producto producto = new Producto();
        producto.setId(1L);



        Receta receta= new Receta(
            1L, 
            "Tomate", 
            10, 
            producto
        );

        when(repository.findByProductoId(1L))
                .thenReturn(List.of(receta));

        List<RecetaResponseDTO> responseDTOs = service.buscarPorProductoID(1L);
        assertEquals(1, responseDTOs.size());
        assertEquals(1L, responseDTOs.get(0).getId());
    }
}
