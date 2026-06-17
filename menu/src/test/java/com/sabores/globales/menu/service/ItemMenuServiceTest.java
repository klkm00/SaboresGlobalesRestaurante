package com.sabores.globales.menu.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sabores.globales.menu.dto.ItemMenuRequest;
import com.sabores.globales.menu.dto.ItemMenuResponse;
import com.sabores.globales.menu.model.ItemMenuModel;
import com.sabores.globales.menu.model.OrigenMenuModel;
import com.sabores.globales.menu.repository.ItemMenuRepository;
import com.sabores.globales.menu.repository.OrigenMenuRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemMenuServiceTest {

    @Mock
    private ItemMenuRepository itemMenuRepository;

    @Mock
    private OrigenMenuRepository origenMenuRepository;

    @InjectMocks
    private ItemMenuService itemMenuService;

    private OrigenMenuModel origenModel;
    private ItemMenuModel itemModel;
    private ItemMenuRequest itemRequest;

    @BeforeEach
    void setUp() {
        origenModel = new OrigenMenuModel();
        origenModel.setIdOrigen(UUID.randomUUID());
        origenModel.setNombreCarta("Comida Italiana");
        origenModel.setDescripcionCarta("Pastas y pizzas");
        origenModel.setCartaDisponible(true);

        itemModel = new ItemMenuModel();
        itemModel.setIdItem(UUID.randomUUID());
        itemModel.setOrigenItem(origenModel);
        itemModel.setProductoId(UUID.randomUUID());
        itemModel.setItemDisponible(true);

        itemRequest = new ItemMenuRequest();
        itemRequest.setIdOrigen(origenModel.getIdOrigen());
        itemRequest.setProductoId(itemModel.getProductoId());
        itemRequest.setItemDisponible(true);
    }

    @Test
    void cuandoGuardar_debeRetornarItemMenuResponse() {
        when(origenMenuRepository.findById(origenModel.getIdOrigen()))
                .thenReturn(Optional.of(origenModel));
        when(itemMenuRepository.save(any(ItemMenuModel.class)))
                .thenReturn(itemModel);

        ItemMenuResponse resultado = itemMenuService.guardar(itemRequest);

        assertNotNull(resultado);
        assertEquals(itemModel.getProductoId(), resultado.getProductoId());
    }

    @Test
    void cuandoBuscarPorIdExiste_debeRetornarItemMenuResponse() {
        UUID id = itemModel.getIdItem();

        when(itemMenuRepository.findById(id)).thenReturn(Optional.of(itemModel));

        ItemMenuResponse resultado = itemMenuService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(itemModel.getProductoId(), resultado.getProductoId());
    }

    @Test
    void cuandoBuscarPorIdNoExiste_debeLanzarExcepcion() {
        UUID id = UUID.randomUUID();

        when(itemMenuRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> itemMenuService.buscarPorId(id));
    }

    @Test
    void cuandoListarTodos_debeRetornarLista() {
        when(itemMenuRepository.findAll()).thenReturn(List.of(itemModel));

        List<ItemMenuResponse> resultado = itemMenuService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void cuandoListarDisponibles_debeRetornarSoloDisponibles() {
        when(itemMenuRepository.findByItemDisponibleTrue()).thenReturn(List.of(itemModel));

        List<ItemMenuResponse> resultado = itemMenuService.listarDisponibles();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getItemDisponible());
    }

    @Test
    void cuandoEliminar_debeEliminarCorrectamente() {
        UUID id = itemModel.getIdItem();

        when(itemMenuRepository.findById(id)).thenReturn(Optional.of(itemModel));

        assertDoesNotThrow(() -> itemMenuService.eliminar(id));

        verify(itemMenuRepository).deleteById(id);
    }

    @Test
    void cuandoGuardarConOrigenInexistente_debeLanzarExcepcion() {
        when(origenMenuRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> itemMenuService.guardar(itemRequest));
    }
}