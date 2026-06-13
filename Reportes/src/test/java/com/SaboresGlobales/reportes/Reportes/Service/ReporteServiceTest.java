package com.SaboresGlobales.reportes.Reportes.Service;

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

import com.SaboresGlobales.reportes.Reportes.DTO.ReportesRequestDTO;
import com.SaboresGlobales.reportes.Reportes.DTO.ReportesResponseDTO;
import com.SaboresGlobales.reportes.Reportes.Modelo.Reporte;
import com.SaboresGlobales.reportes.Reportes.Repository.ReporteRepository;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock
    private ReporteRepository repo;

    @InjectMocks
    private ReporteService reporteService;

    @Test
    void testObtenerDtos() {

        Reporte reporte = new Reporte(
                1L,
                "Pedido#200",
                20000.0,
                200,
                "Entregado");

        when(repo.findAll()).thenReturn(List.of(reporte));

        List<ReportesResponseDTO> resultado =
                reporteService.obtenerDtos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pedido#200",
                resultado.get(0).getPedido());
    }

    @Test
    void testObtenerPorId() {

        Long id = 1L;

        Reporte reporte = new Reporte(
                id,
                "Pedido#02",
                10000.0,
                23,
                "Entregado");

        when(repo.findById(id))
                .thenReturn(Optional.of(reporte));

        Optional<ReportesResponseDTO> resultado =
                reporteService.obtenerporID(id);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        assertEquals("Pedido#02",
                resultado.get().getPedido());
    }

    @Test
    void testGuardar() {

        ReportesRequestDTO dto = new ReportesRequestDTO(
                "Pedido#02",
                10000.0,
                23,
                "Entregado");

        Reporte reporteGuardado = new Reporte(
                1L,
                "Pedido#02",
                10000.0,
                23,
                "Entregado");

        when(repo.save(any(Reporte.class)))
                .thenReturn(reporteGuardado);

        ReportesResponseDTO resultado =
                reporteService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Pedido#02",
                resultado.getPedido());
        assertEquals(10000.0,
                resultado.getPagos());
    }

    @Test
    void testEliminar() {

        Long id = 1L;

        doNothing().when(repo).deleteById(id);

        reporteService.eliminar(id);

        verify(repo, times(1))
                .deleteById(id);
    }

    @Test
    void testBuscarPorPedido() {

        String pedido = "Pedido#02";

        Reporte reporte = new Reporte(
                1L,
                pedido,
                10000.0,
                23,
                "Entregado");

        when(repo.findByPedidoContainingIgnoreCase(pedido))
                .thenReturn(List.of(reporte));

        List<ReportesResponseDTO> resultado =
                reporteService.buscarporPedido(pedido);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(pedido,
                resultado.get(0).getPedido());
    }
}