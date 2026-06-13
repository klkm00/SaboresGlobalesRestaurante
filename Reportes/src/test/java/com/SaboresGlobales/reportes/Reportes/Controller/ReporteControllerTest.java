package com.SaboresGlobales.reportes.Reportes.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.SaboresGlobales.reportes.Reportes.DTO.ReportesRequestDTO;
import com.SaboresGlobales.reportes.Reportes.DTO.ReportesResponseDTO;
import com.SaboresGlobales.reportes.Reportes.Modelo.Reporte;
import com.SaboresGlobales.reportes.Reportes.Service.ReporteService;
import com.fasterxml.jackson.databind.ObjectMapper;



@WebMvcTest({ReporteController.class})
public class ReporteControllerTest {
   
   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private ReporteService reporteService;

   @Autowired
   private ObjectMapper objectMapper;

   private Reporte reporte;

   @BeforeEach
   void setUp() {
    reporte = new Reporte();
    reporte.setId(1L);
    reporte.setPedido("Pedido#02");
    reporte.setPagos(150000);
    reporte.setInventario(50);
    reporte.setDelivery("Uber Eats");
   }
   
   
   
    @Test
     public void testBuscarporPedido() {
        when(reporteService.buscarporPedido("Pedido#02")).thenReturn(java.util.List.of(new ReportesResponseDTO()));

    }

    @Test
    public void testCrear() throws Exception {

    ReportesRequestDTO request = new ReportesRequestDTO(
            "Pedido#02",
            150000,
            50,
            "Uber Eats"
    );

    ReportesResponseDTO response = new ReportesResponseDTO(
            1L,
            "Pedido#02",
            150000,
            50,
            "Uber Eats"
    );

    when(reporteService.guardar(any(ReportesRequestDTO.class)))
            .thenReturn(response);

    mockMvc.perform(post("/api/v1/reportes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.pedido").value("Pedido#02"))
            .andExpect(jsonPath("$.pagos").value(150000))
            .andExpect(jsonPath("$.inventario").value(50))
            .andExpect(jsonPath("$.delivery").value("Uber Eats"));
    }
    @Test
    public void testEliminar() throws Exception {
    when(reporteService.obtenerporID(1L))
            .thenReturn(java.util.Optional.of(new ReportesResponseDTO()));

    doNothing().when(reporteService).eliminar(1L);

    mockMvc.perform(delete("/api/v1/reportes/1"))
            .andExpect(status().isNoContent());

    verify(reporteService, times(1)).eliminar(1L);
}   

    @Test
    public void testObtenerTodos() throws Exception {

    ReportesResponseDTO dto = new ReportesResponseDTO(
            1L,
            "Pedido#02",
            150000,
            50,
            "Uber Eats"
    );

    when(reporteService.obtenerDtos())
            .thenReturn(List.of(dto));

    mockMvc.perform(get("/api/v1/reportes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].pedido").value("Pedido#02"))
            .andExpect(jsonPath("$[0].pagos").value(150000))
            .andExpect(jsonPath("$[0].inventario").value(50))
            .andExpect(jsonPath("$[0].delivery").value("Uber Eats"));
}

    @Test
    public void testObtenerporID() throws Exception {

    ReportesResponseDTO dto = new ReportesResponseDTO(
            1L,
            "Pedido#02",
            150000,
            50,
            "Uber Eats"
    );

    when(reporteService.obtenerporID(1L))
            .thenReturn(java.util.Optional.of(dto));

    mockMvc.perform(get("/api/v1/reportes/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.pedido").value("Pedido#02"))
            .andExpect(jsonPath("$.pagos").value(150000))
            .andExpect(jsonPath("$.inventario").value(50))
            .andExpect(jsonPath("$.delivery").value("Uber Eats"));
        }
}
