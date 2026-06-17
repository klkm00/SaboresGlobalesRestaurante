package com.sabores.globales.menu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.sabores.globales.menu.dto.ItemMenuRequest;
import com.sabores.globales.menu.dto.ItemMenuResponse;
import com.sabores.globales.menu.service.ItemMenuService;

import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemMenuControllerTest {

    @Mock
    private ItemMenuService itemMenuService;

    @InjectMocks
    private ItemMenuController itemMenuController;

    private ItemMenuResponse itemResponse;
    private ItemMenuRequest itemRequest;
    private UUID idItem;
    private UUID idOrigen;
    private UUID idProducto;

    @BeforeEach
    void setUp() {
        idItem = UUID.randomUUID();
        idOrigen = UUID.randomUUID();
        idProducto = UUID.randomUUID();

        itemResponse = new ItemMenuResponse();
        itemResponse.setIdItem(idItem);
        itemResponse.setProductoId(idProducto);
        itemResponse.setItemDisponible(true);

        itemRequest = new ItemMenuRequest();
        itemRequest.setIdOrigen(idOrigen);
        itemRequest.setProductoId(idProducto);
        itemRequest.setItemDisponible(true);
    }

    @Test
    void cuandoListarTodos_debeRetornar200() {
        when(itemMenuService.listarTodos()).thenReturn(List.of(itemResponse));

        ResponseEntity<List<ItemMenuResponse>> respuesta = itemMenuController.listarTodos();

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void cuandoListarDisponibles_debeRetornar200() {
        when(itemMenuService.listarDisponibles()).thenReturn(List.of(itemResponse));

        ResponseEntity<List<ItemMenuResponse>> respuesta = itemMenuController.listarDisponibles();

        assertEquals(200, respuesta.getStatusCode().value());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void cuandoListarPorOrigen_debeRetornar200() {
        when(itemMenuService.listarPorOrigen(idOrigen)).thenReturn(List.of(itemResponse));

        ResponseEntity<List<ItemMenuResponse>> respuesta = itemMenuController.listarPorOrigen(idOrigen);

        assertEquals(200, respuesta.getStatusCode().value());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void cuandoBuscarPorIdExiste_debeRetornar200() {
        when(itemMenuService.buscarPorId(idItem)).thenReturn(itemResponse);

        ResponseEntity<ItemMenuResponse> respuesta = itemMenuController.buscarPorId(idItem);

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
        assertEquals(idProducto, respuesta.getBody().getProductoId());
    }

    @Test
    void cuandoGuardar_debeRetornar201() {
        when(itemMenuService.guardar(itemRequest)).thenReturn(itemResponse);

        ResponseEntity<ItemMenuResponse> respuesta = itemMenuController.guardar(itemRequest);

        assertEquals(201, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
    }

    @Test
    void cuandoActualizar_debeRetornar200() {
        when(itemMenuService.actualizar(idItem, itemRequest)).thenReturn(itemResponse);

        ResponseEntity<ItemMenuResponse> respuesta = itemMenuController.actualizar(idItem, itemRequest);

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
    }

    @Test
    void cuandoEliminar_debeRetornar204() {
        ResponseEntity<Void> respuesta = itemMenuController.eliminar(idItem);

        assertEquals(204, respuesta.getStatusCode().value());
        verify(itemMenuService).eliminar(idItem);
    }
}