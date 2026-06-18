package com.example.ms_inventario.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_inventario.dto.InventarioRequestDTO;
import com.example.ms_inventario.dto.InventarioResponseDTO;
import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.repository.InventarioRepository;
import com.example.ms_inventario.service.InventarioService;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - InventarioService")
class InventarioServiceTest {

    @Mock
    private InventarioRepository repo;

    @InjectMocks
    private InventarioService inventarioService;

  
    @Test
    public void testActualizar(){
        Long id = 2L;

        Inventario inventarioNuevo = new Inventario(
            id, 
            "Aceite", 
            12
        );
        InventarioRequestDTO dto = new InventarioRequestDTO(
            "Aceite", 
            12
        );

        when(repo.findById(2L)).thenReturn(Optional.of(new Inventario()));

        when(repo.save(any(Inventario.class))).thenReturn(inventarioNuevo);

        Optional<InventarioResponseDTO> resOptional = inventarioService.Actualizar(id, dto);

        assertTrue(resOptional.isPresent());
        assertEquals(id, resOptional.get().getId());
    }

    @Test
    void testCrear(){
            Inventario inventarioNuevo = new Inventario(
            1L, 
            "Aceite", 
            12
        );
        InventarioRequestDTO dto = new InventarioRequestDTO(
            "Aceite", 
            12
        );

        when(repo.save(any(Inventario.class))).thenReturn(inventarioNuevo);
        InventarioResponseDTO responseDTO = inventarioService.Guardar(dto);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
    }

    @Test
    void Eliminar(){
        Long id =1L;
        doNothing().when(repo).deleteById(id);

        inventarioService.eliminar(id);

        verify(repo, times(1)).deleteById(id);
    }

    @Test
    void BuscarTodos(){
        Inventario inventarioNuevo = new Inventario(
            1L, 
            "Aceite", 
            12
        );

        when(repo.findAll()).thenReturn(List.of(inventarioNuevo));

        List<InventarioResponseDTO> list = inventarioService.ObtenerTodos();
        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).getId());
    }

    @Test
    void BuscarPorInsumo(){
        String insumo = "Aceite";

        Inventario inventarioNuevo = new Inventario(
            1L, 
            insumo, 
            12
        );

        when(repo.findByInsumo(insumo)).thenReturn(List.of(inventarioNuevo));
        List<InventarioResponseDTO> list = inventarioService.ObtenerPorInsumo(insumo);

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(insumo, list.get(0).getInsumo());

    }

    @Test
    void BuscarPorId(){
        Long id = 1L;
          Inventario inventarioNuevo = new Inventario(
            id, 
            "Aceite", 
            12
        );

        when(repo.findById(1L)).thenReturn(Optional.of(inventarioNuevo));

        Optional<InventarioResponseDTO> optional =
            inventarioService.ObtenerPorId(id);

        assertTrue(optional.isPresent());
        assertEquals(id, optional.get().getId());
        
    }


}
