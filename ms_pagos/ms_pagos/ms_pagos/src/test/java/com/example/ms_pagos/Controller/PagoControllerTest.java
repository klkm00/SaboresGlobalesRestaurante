package com.example.ms_pagos.Controller;

import com.example.ms_pagos.controller.PagoController;
import com.example.ms_pagos.dto.PagoRequestDTO;
import com.example.ms_pagos.dto.PagoResponseDTO;
import com.example.ms_pagos.exception.RecursoNoEncontradoException;
import com.example.ms_pagos.exception.ReglaNegocioException;
import com.example.ms_pagos.model.Pago.EstadoPago;
import com.example.ms_pagos.service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagoController.class)
@DisplayName("Pruebas unitarias - PagoController")
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Autowired
    private ObjectMapper objectMapper;

    private PagoResponseDTO responseConfirmado;
    private PagoResponseDTO responsePendiente;
    private PagoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseConfirmado = PagoResponseDTO.builder()
                .id(1L)
                .pedidoId(10L)
                .monto(150.0)
                .estado(EstadoPago.CONFIRMADO)
                .metodoPago("TARJETA")
                .fechaPago(LocalDateTime.now())
                .codigoTransaccion("TXN-ABC12345")
                .build();

        responsePendiente = PagoResponseDTO.builder()
                .id(2L)
                .pedidoId(11L)
                .monto(75.0)
                .estado(EstadoPago.PENDIENTE)
                .metodoPago("EFECTIVO")
                .fechaPago(LocalDateTime.now())
                .codigoTransaccion("TXN-ABC12345")
                .build();

        requestDTO = new PagoRequestDTO(10L, 150.0, "TARJETA", LocalDateTime.now(), "EFECTIVO",
         "TXN-ABC12345");
    }

    // ─── GET /pagos ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /pagos: debe retornar 200 y lista de pagos")
    void getAll_debeRetornar200ConListaDePagos() throws Exception {
        when(pagoService.getAll()).thenReturn(Arrays.asList(responseConfirmado, responsePendiente));

        mockMvc.perform(get("/pagos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].estado", is("CONFIRMADO")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].estado", is("PENDIENTE")));
    }

    @Test
    @DisplayName("GET /pagos: debe retornar lista vacía cuando no hay pagos")
    void getAll_debeRetornarListaVacia() throws Exception {
        when(pagoService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // ─── GET /pagos/{id} ─────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /pagos/{id}: debe retornar 200 y el pago cuando existe")
    void getById_debeRetornar200_cuandoPagoExiste() throws Exception {
        when(pagoService.getById(1L)).thenReturn(responseConfirmado);

        mockMvc.perform(get("/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.pedidoId", is(10)))
                .andExpect(jsonPath("$.monto", is(150.0)))
                .andExpect(jsonPath("$.estado", is("CONFIRMADO")))
                .andExpect(jsonPath("$.metodoPago", is("TARJETA")))
                .andExpect(jsonPath("$.codigoTransaccion", is("TXN-ABC12345")));
    }

    @Test
    @DisplayName("GET /pagos/{id}: debe retornar 404 cuando el pago no existe")
    void getById_debeRetornar404_cuandoPagoNoExiste() throws Exception {
        when(pagoService.getById(99L))
                .thenThrow(new RecursoNoEncontradoException("Pago no encontrado con id: 99"));

        mockMvc.perform(get("/pagos/99"))
                .andExpect(status().isNotFound());
    }

    // ─── GET /pagos/pedido/{pedidoId} ────────────────────────────────────────

    @Test
    @DisplayName("GET /pagos/pedido/{pedidoId}: debe retornar 200 y pagos del pedido")
    void getByPedido_debeRetornar200ConPagosDelPedido() throws Exception {
        when(pagoService.getByPedidoId(10L)).thenReturn(List.of(responseConfirmado));

        mockMvc.perform(get("/pagos/pedido/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].pedidoId", is(10)));
    }

    @Test
    @DisplayName("GET /pagos/pedido/{pedidoId}: debe retornar lista vacía si no hay pagos")
    void getByPedido_debeRetornarListaVacia() throws Exception {
        when(pagoService.getByPedidoId(999L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/pagos/pedido/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // ─── POST /pagos ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /pagos: debe retornar 201 y el pago creado")
    void procesarPago_debeRetornar201YPagoCreado() throws Exception {
        when(pagoService.procesarPago(any(PagoRequestDTO.class))).thenReturn(responseConfirmado);

        mockMvc.perform(post("/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.pedidoId", is(10)))
                .andExpect(jsonPath("$.monto", is(150.0)))
                .andExpect(jsonPath("$.estado", is("CONFIRMADO")))
                .andExpect(jsonPath("$.metodoPago", is("TARJETA")))
                .andExpect(jsonPath("$.codigoTransaccion", is("TXN-ABC12345")));
    }

    @Test
    @DisplayName("POST /pagos: debe retornar 400 si el pedido ya tiene pago confirmado")
    void procesarPago_debeRetornar400_cuandoPedidoYaPagado() throws Exception {
        when(pagoService.procesarPago(any(PagoRequestDTO.class)))
                .thenThrow(new ReglaNegocioException("El pedido ya fue pagado exitosamente"));

        mockMvc.perform(post("/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /pagos: debe retornar 400 si el body es inválido (sin pedidoId)")
    void procesarPago_debeRetornar400_sinPedidoId() throws Exception {
        PagoRequestDTO requestInvalido = new PagoRequestDTO(null, 100.0, "TARJETA", null, null, null);

        mockMvc.perform(post("/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /pagos: debe retornar 400 si el método de pago es inválido")
    void procesarPago_debeRetornar400_conMetodoPagoInvalido() throws Exception {
        PagoRequestDTO requestInvalido = new PagoRequestDTO(5L, 100.0, "BITCOIN", null, null, null);

        mockMvc.perform(post("/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());
    }

    // ─── PUT /pagos/{id}/anular ───────────────────────────────────────────────

    @Test
    @DisplayName("PUT /pagos/{id}/anular: debe retornar 200 y pago anulado")
    void anularPago_debeRetornar200YPagoAnulado() throws Exception {
        PagoResponseDTO pagoAnulado = PagoResponseDTO.builder()
                .id(1L)
                .pedidoId(10L)
                .monto(150.0)
                .estado(EstadoPago.FALLIDO)
                .metodoPago("TARJETA")
                .build();

        when(pagoService.anularPago(1L)).thenReturn(pagoAnulado);

        mockMvc.perform(put("/pagos/1/anular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("FALLIDO")));
    }

    @Test
    @DisplayName("PUT /pagos/{id}/anular: debe retornar 404 si el pago no existe")
    void anularPago_debeRetornar404_cuandoPagoNoExiste() throws Exception {
        when(pagoService.anularPago(99L))
                .thenThrow(new RecursoNoEncontradoException("Pago no encontrado con id: 99"));

        mockMvc.perform(put("/pagos/99/anular"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /pagos/{id}/anular: debe retornar 400 si el pago no está CONFIRMADO")
    void anularPago_debeRetornar400_cuandoPagoNoPuedeAnularse() throws Exception {
        when(pagoService.anularPago(2L))
                .thenThrow(new ReglaNegocioException("Solo se pueden anular pagos en estado CONFIRMADO"));

        mockMvc.perform(put("/pagos/2/anular"))
                .andExpect(status().isBadRequest());
    }

    // ─── DELETE /pagos/{id} ───────────────────────────────────────────────────

    @Test
    @DisplayName("DELETE /pagos/{id}: debe retornar 204 cuando el pago existe")
    void eliminar_debeRetornar204_cuandoPagoExiste() throws Exception {
        doNothing().when(pagoService).eliminar(1L);

        mockMvc.perform(delete("/pagos/1"))
                .andExpect(status().isNoContent());

        verify(pagoService, times(1)).eliminar(1L);
    }

    @Test
    @DisplayName("DELETE /pagos/{id}: debe retornar 404 cuando el pago no existe")
    void eliminar_debeRetornar404_cuandoPagoNoExiste() throws Exception {
        doThrow(new RecursoNoEncontradoException("Pago no encontrado con id: 55"))
                .when(pagoService).eliminar(55L);

        mockMvc.perform(delete("/pagos/55"))
                .andExpect(status().isNotFound());
    }
}
