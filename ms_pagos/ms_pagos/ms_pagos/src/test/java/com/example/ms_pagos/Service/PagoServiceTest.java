package com.example.ms_pagos.Service;

import com.example.ms_pagos.dto.PagoRequestDTO;
import com.example.ms_pagos.dto.PagoResponseDTO;
import com.example.ms_pagos.exception.RecursoNoEncontradoException;
import com.example.ms_pagos.exception.ReglaNegocioException;
import com.example.ms_pagos.model.Pago;
import com.example.ms_pagos.model.Pago.EstadoPago;
import com.example.ms_pagos.repository.PagoRepository;
import com.example.ms_pagos.service.PagoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - PagoService")
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private WebClient webClientPedidos;

    // Mocks encadenados para WebClient
    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private PagoService pagoService;

    private Pago pagoConfirmado;
    private Pago pagoPendiente;
    private Pago pagoFallido;
    private PagoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        pagoConfirmado = Pago.builder()
                .id(1L)
                .pedidoId(10L)
                .monto(150.0)
                .estado(EstadoPago.CONFIRMADO)
                .metodoPago("TARJETA")
                .fechaPago(LocalDateTime.now())
                .codigoTransaccion("TXN-ABC123")
                .build();

        pagoPendiente = Pago.builder()
                .id(2L)
                .pedidoId(11L)
                .monto(75.0)
                .estado(EstadoPago.PENDIENTE)
                .metodoPago("EFECTIVO")
                .fechaPago(LocalDateTime.now())
                .codigoTransaccion(null)
                .build();

        pagoFallido = Pago.builder()
                .id(3L)
                .pedidoId(12L)
                .monto(200.0)
                .estado(EstadoPago.FALLIDO)
                .metodoPago("TRANSFERENCIA")
                .fechaPago(LocalDateTime.now())
                .codigoTransaccion(null)
                .build();

        requestDTO = buildPagoRequestDTO(10L, 150.0, "TARJETA");
    }

    private PagoRequestDTO buildPagoRequestDTO(Long pedidoId, Double monto, String metodoPago) {
        PagoRequestDTO dto = new PagoRequestDTO();
        dto.setPedidoId(pedidoId);
        dto.setMonto(monto);
        dto.setMetodoPago(metodoPago);
        return dto;
    }

    // ─── Helpers para mockear WebClient ──────────────────────────────────────

    @SuppressWarnings("unchecked")
    private void mockWebClientOk() {
        when(webClientPedidos.get()).thenReturn((WebClient.RequestHeadersUriSpec) requestHeadersUriSpec);
        Mockito.<WebClient.RequestHeadersSpec<?>>when(requestHeadersUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toBodilessEntity()).thenReturn(Mono.empty());
    }

    @SuppressWarnings("unchecked")
    private void mockWebClientError() {
        when(webClientPedidos.get()).thenReturn((WebClient.RequestHeadersUriSpec) requestHeadersUriSpec);
        Mockito.<WebClient.RequestHeadersSpec<?>>when(requestHeadersUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toBodilessEntity()).thenReturn(Mono.error(new RuntimeException("Servicio no disponible")));
    }

    // ─── getAll() ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getAll: debe retornar todos los pagos mapeados a DTO")
    void getAll_debeRetornarTodosLosPagos() {
        when(pagoRepository.findAll()).thenReturn(Arrays.asList(pagoConfirmado, pagoPendiente));

        List<PagoResponseDTO> resultado = pagoService.getAll();

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        assertThat(resultado.get(0).getEstado()).isEqualTo(EstadoPago.CONFIRMADO);
        assertThat(resultado.get(1).getId()).isEqualTo(2L);
        assertThat(resultado.get(1).getEstado()).isEqualTo(EstadoPago.PENDIENTE);
        verify(pagoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAll: debe retornar lista vacía cuando no hay pagos")
    void getAll_debeRetornarListaVacia_sinPagos() {
        when(pagoRepository.findAll()).thenReturn(Collections.emptyList());

        List<PagoResponseDTO> resultado = pagoService.getAll();

        assertThat(resultado).isEmpty();
    }

    // ─── getById() ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("getById: debe retornar DTO cuando el pago existe")
    void getById_debeRetornarDTO_cuandoPagoExiste() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoConfirmado));

        PagoResponseDTO resultado = pagoService.getById(1L);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getPedidoId()).isEqualTo(10L);
        assertThat(resultado.getMonto()).isEqualTo(150.0);
        assertThat(resultado.getEstado()).isEqualTo(EstadoPago.CONFIRMADO);
        assertThat(resultado.getMetodoPago()).isEqualTo("TARJETA");
        assertThat(resultado.getCodigoTransaccion()).isEqualTo("TXN-ABC123");
    }

    @Test
    @DisplayName("getById: debe lanzar RecursoNoEncontradoException cuando el pago no existe")
    void getById_debeLanzarExcepcion_cuandoPagoNoExiste() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pagoService.getById(99L))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("99");
    }

    // ─── getByPedidoId() ─────────────────────────────────────────────────────

    @Test
    @DisplayName("getByPedidoId: debe retornar pagos asociados al pedido")
    void getByPedidoId_debeRetornarPagosDeLPedido() {
        when(pagoRepository.findByPedidoId(10L)).thenReturn(List.of(pagoConfirmado));

        List<PagoResponseDTO> resultado = pagoService.getByPedidoId(10L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getPedidoId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("getByPedidoId: debe retornar lista vacía cuando no hay pagos para ese pedido")
    void getByPedidoId_debeRetornarListaVacia_sinPagos() {
        when(pagoRepository.findByPedidoId(999L)).thenReturn(Collections.emptyList());

        List<PagoResponseDTO> resultado = pagoService.getByPedidoId(999L);

        assertThat(resultado).isEmpty();
    }

    // ─── procesarPago() ──────────────────────────────────────────────────────

    @Test
    @DisplayName("procesarPago: debe crear pago CONFIRMADO cuando monto es válido")
    void procesarPago_debeCrearPagoConfirmado_conMontoValido() {
        PagoRequestDTO req = buildPagoRequestDTO(20L, 500.0, "TARJETA");

        when(pagoRepository.existsByPedidoIdAndEstado(20L, EstadoPago.CONFIRMADO)).thenReturn(false);
        mockWebClientOk();
        when(pagoRepository.save(any(Pago.class))).thenAnswer(inv -> {
            Pago p = inv.getArgument(0);
            p.setId(99L);
            return p;
        });

        PagoResponseDTO resultado = pagoService.procesarPago(req);

        assertThat(resultado.getEstado()).isEqualTo(EstadoPago.CONFIRMADO);
        assertThat(resultado.getCodigoTransaccion()).isNotNull().startsWith("TXN-");
        assertThat(resultado.getPedidoId()).isEqualTo(20L);
        assertThat(resultado.getMonto()).isEqualTo(500.0);
    }

    @Test
    @DisplayName("procesarPago: debe crear pago FALLIDO cuando monto es menor a 0.01")
    void procesarPago_debeCrearPagoFallido_conMontoMenorAlMinimo() {
        PagoRequestDTO req = buildPagoRequestDTO(21L, 0.005, "EFECTIVO");

        when(pagoRepository.existsByPedidoIdAndEstado(21L, EstadoPago.CONFIRMADO)).thenReturn(false);
        mockWebClientOk();
        when(pagoRepository.save(any(Pago.class))).thenAnswer(inv -> {
            Pago p = inv.getArgument(0);
            p.setId(100L);
            return p;
        });

        PagoResponseDTO resultado = pagoService.procesarPago(req);

        assertThat(resultado.getEstado()).isEqualTo(EstadoPago.FALLIDO);
        assertThat(resultado.getCodigoTransaccion()).isNull();
    }

    @Test
    @DisplayName("procesarPago: debe lanzar ReglaNegocioException si el pedido ya fue pagado")
    void procesarPago_debeLanzarExcepcion_cuandoPedidoYaFuePagado() {
        PagoRequestDTO req = buildPagoRequestDTO(10L, 150.0, "TARJETA");

        when(pagoRepository.existsByPedidoIdAndEstado(10L, EstadoPago.CONFIRMADO)).thenReturn(true);

        assertThatThrownBy(() -> pagoService.procesarPago(req))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("ya fue pagado");

        verify(pagoRepository, never()).save(any());
    }

    @Test
    @DisplayName("procesarPago: debe lanzar ReglaNegocioException si el pedido no existe en ms_pedidos")
    void procesarPago_debeLanzarExcepcion_cuandoPedidoNoExisteEnMsPedidos() {
        PagoRequestDTO req = buildPagoRequestDTO(55L, 100.0, "TRANSFERENCIA");

        when(pagoRepository.existsByPedidoIdAndEstado(55L, EstadoPago.CONFIRMADO)).thenReturn(false);
        mockWebClientError();

        assertThatThrownBy(() -> pagoService.procesarPago(req))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("verificar el pedido");

        verify(pagoRepository, never()).save(any());
    }

    @Test
    @DisplayName("procesarPago: el pago confirmado debe tener código de transacción con formato TXN-")
    void procesarPago_debeGenerarCodigoTransaccionConFormato() {
        PagoRequestDTO req = buildPagoRequestDTO(30L, 99.0, "EFECTIVO");

        when(pagoRepository.existsByPedidoIdAndEstado(30L, EstadoPago.CONFIRMADO)).thenReturn(false);
        mockWebClientOk();
        when(pagoRepository.save(any(Pago.class))).thenAnswer(inv -> {
            Pago p = inv.getArgument(0);
            p.setId(77L);
            return p;
        });

        PagoResponseDTO resultado = pagoService.procesarPago(req);

        assertThat(resultado.getCodigoTransaccion())
                .matches("TXN-[A-Z0-9\\-]+");
    }

    @Test
    @DisplayName("procesarPago: debe asignar fecha de pago al crear el registro")
    void procesarPago_debeAsignarFechaPago() {
        PagoRequestDTO req = buildPagoRequestDTO(40L, 250.0, "TARJETA");

        when(pagoRepository.existsByPedidoIdAndEstado(40L, EstadoPago.CONFIRMADO)).thenReturn(false);
        mockWebClientOk();

        ArgumentCaptor<Pago> captor = ArgumentCaptor.forClass(Pago.class);
        when(pagoRepository.save(captor.capture())).thenAnswer(inv -> {
            Pago p = inv.getArgument(0);
            p.setId(88L);
            return p;
        });

        pagoService.procesarPago(req);

        assertThat(captor.getValue().getFechaPago()).isNotNull();
    }

    // ─── anularPago() ────────────────────────────────────────────────────────

    @Test
    @DisplayName("anularPago: debe cambiar estado a FALLIDO cuando pago está CONFIRMADO")
    void anularPago_debeCambiarEstadoAFallido() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoConfirmado));
        when(pagoRepository.save(any(Pago.class))).thenAnswer(inv -> inv.getArgument(0));

        PagoResponseDTO resultado = pagoService.anularPago(1L);

        assertThat(resultado.getEstado()).isEqualTo(EstadoPago.FALLIDO);
    }

    @Test
    @DisplayName("anularPago: debe lanzar RecursoNoEncontradoException cuando pago no existe")
    void anularPago_debeLanzarExcepcion_cuandoPagoNoExiste() {
        when(pagoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pagoService.anularPago(999L))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("999");
    }

    @Test
    @DisplayName("anularPago: debe lanzar ReglaNegocioException al intentar anular pago PENDIENTE")
    void anularPago_debeLanzarExcepcion_cuandoPagoEstaPendiente() {
        when(pagoRepository.findById(2L)).thenReturn(Optional.of(pagoPendiente));

        assertThatThrownBy(() -> pagoService.anularPago(2L))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("CONFIRMADO");

        verify(pagoRepository, never()).save(any());
    }

    @Test
    @DisplayName("anularPago: debe lanzar ReglaNegocioException al intentar anular pago ya FALLIDO")
    void anularPago_debeLanzarExcepcion_cuandoPagoYaEstaFallido() {
        when(pagoRepository.findById(3L)).thenReturn(Optional.of(pagoFallido));

        assertThatThrownBy(() -> pagoService.anularPago(3L))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("CONFIRMADO");
    }

    @Test
    @DisplayName("anularPago: debe persistir el cambio de estado al guardar")
    void anularPago_debeLlamarSaveConEstadoFallido() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoConfirmado));

        ArgumentCaptor<Pago> captor = ArgumentCaptor.forClass(Pago.class);
        when(pagoRepository.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        pagoService.anularPago(1L);

        assertThat(captor.getValue().getEstado()).isEqualTo(EstadoPago.FALLIDO);
    }

    // ─── eliminar() ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("eliminar: debe llamar deleteById cuando el pago existe")
    void eliminar_debeLlamarDeleteById_cuandoPagoExiste() {
        when(pagoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pagoRepository).deleteById(1L);

        pagoService.eliminar(1L);

        verify(pagoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("eliminar: debe lanzar RecursoNoEncontradoException cuando el pago no existe")
    void eliminar_debeLanzarExcepcion_cuandoPagoNoExiste() {
        when(pagoRepository.existsById(77L)).thenReturn(false);

        assertThatThrownBy(() -> pagoService.eliminar(77L))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("77");

        verify(pagoRepository, never()).deleteById(any());
    }
}
