package com.saboresglobales.carrito.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.saboresglobales.carrito.dto.ItemCarritoRequest;
import com.saboresglobales.carrito.dto.ItemCarritoResponse;
import com.saboresglobales.carrito.service.ItemCarritoService;

import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCarritoControllerTest {

    @Mock
    private ItemCarritoService itemCarritoService;

    @InjectMocks
    private ItemCarritoController itemCarritoController;

    private ItemCarritoResponse itemResponse;
    private ItemCarritoRequest itemRequest;
    private UUID idCarrito;
    private UUID idItem;
    private UUID idProducto;

    @BeforeEach
    void setUp() {
        idCarrito = UUID.randomUUID();
        idItem = UUID.randomUUID();
        idProducto = UUID.randomUUID();

        itemResponse = new ItemCarritoResponse();
        itemResponse.setIdItemCarrito(idItem);
        itemResponse.setProductoId(idProducto);
        itemResponse.setCantidad(2);
        itemResponse.setPrecioUnitario(9.99);
        itemResponse.setSubtotal(19.98);

        itemRequest = new ItemCarritoRequest();
        itemRequest.setProductoId(idProducto);
        itemRequest.setCantidad(2);
        itemRequest.setPrecioUnitario(9.99);
    }

    @Test
    void cuandoListarItems_debeRetornar200() {
        when(itemCarritoService.listarItems(idCarrito)).thenReturn(List.of(itemResponse));

        ResponseEntity<List<ItemCarritoResponse>> respuesta = itemCarritoController.listarItems(idCarrito);

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void cuandoAgregarItem_debeRetornar201() {
        when(itemCarritoService.agregarItem(idCarrito, itemRequest)).thenReturn(itemResponse);

        ResponseEntity<ItemCarritoResponse> respuesta = itemCarritoController.agregarItem(idCarrito, itemRequest);

        assertEquals(201, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
        assertEquals(idProducto, respuesta.getBody().getProductoId());
    }

    @Test
    void cuandoQuitarItem_debeRetornar204() {
        ResponseEntity<Void> respuesta = itemCarritoController.quitarItem(idItem);

        assertEquals(204, respuesta.getStatusCode().value());
        verify(itemCarritoService).quitarItem(idItem);
    }

    @Test
    void cuandoVaciarCarrito_debeRetornar204() {
        ResponseEntity<Void> respuesta = itemCarritoController.vaciarCarrito(idCarrito);

        assertEquals(204, respuesta.getStatusCode().value());
        verify(itemCarritoService).vaciarCarrito(idCarrito);
    }
}