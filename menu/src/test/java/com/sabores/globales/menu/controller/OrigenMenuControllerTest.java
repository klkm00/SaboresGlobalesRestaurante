package com.sabores.globales.menu.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.sabores.globales.menu.dto.OrigenRequest;
import com.sabores.globales.menu.dto.OrigenResponse;
import com.sabores.globales.menu.service.OrigenMenuService;

import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrigenMenuControllerTest {

    @Mock
    private OrigenMenuService origenMenuService;

    @InjectMocks
    private OrigenMenuController origenMenuController;

    private OrigenResponse origenResponse;
    private OrigenRequest origenRequest;

    @BeforeEach
    void setUp() {
        origenResponse = new OrigenResponse();
        origenResponse.setIdOrigen(UUID.randomUUID());
        origenResponse.setNombreCarta("Comida Italiana");
        origenResponse.setDescripcionCarta("Pastas y pizzas");
        origenResponse.setCartaDisponible(true);

        origenRequest = new OrigenRequest();
        origenRequest.setNombreCarta("Comida Italiana");
        origenRequest.setDescripcionCarta("Pastas y pizzas");
    }

    @Test
    void cuandoListarActivos_debeRetornar200() {
        when(origenMenuService.listarActivos()).thenReturn(List.of(origenResponse));

        ResponseEntity<List<OrigenResponse>> respuesta = origenMenuController.listarActivos();

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void cuandoBuscarPorIdExiste_debeRetornar200() {
        UUID id = origenResponse.getIdOrigen();
        when(origenMenuService.buscarPorId(id)).thenReturn(origenResponse);

        ResponseEntity<OrigenResponse> respuesta = origenMenuController.buscarPorId(id);

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
        assertEquals("Comida Italiana", respuesta.getBody().getNombreCarta());
    }

    @Test
    void cuandoGuardar_debeRetornar201() {
        when(origenMenuService.guardar(origenRequest)).thenReturn(origenResponse);

        ResponseEntity<OrigenResponse> respuesta = origenMenuController.guardar(origenRequest);

        assertEquals(201, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
        assertEquals("Comida Italiana", respuesta.getBody().getNombreCarta());
    }

    @Test
    void cuandoActualizar_debeRetornar200() {
        UUID id = origenResponse.getIdOrigen();
        when(origenMenuService.actualizar(id, origenRequest)).thenReturn(origenResponse);

        ResponseEntity<OrigenResponse> respuesta = origenMenuController.actualizar(id, origenRequest);

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
    }

    @Test
    void cuandoEliminar_debeRetornar204() {
        UUID id = origenResponse.getIdOrigen();

        ResponseEntity<Void> respuesta = origenMenuController.eliminar(id);

        assertEquals(204, respuesta.getStatusCode().value());
        verify(origenMenuService).eliminar(id);
    }
}