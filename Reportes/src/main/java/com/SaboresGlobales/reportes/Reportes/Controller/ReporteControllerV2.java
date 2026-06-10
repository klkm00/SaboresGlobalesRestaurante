package com.SaboresGlobales.reportes.Reportes.Controller;

import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SaboresGlobales.reportes.Reportes.Assembler.reporteModelAssembler;
import com.SaboresGlobales.reportes.Reportes.DTO.ReportesRequestDTO;
import com.SaboresGlobales.reportes.Reportes.DTO.ReportesResponseDTO;
import com.SaboresGlobales.reportes.Reportes.Service.ReporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v2/reportes")
@Tag(name = "Reportes Version 2", description = "Reportes V2")
public class ReporteControllerV2 {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private reporteModelAssembler assembler;


    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los reportes")
    public CollectionModel<EntityModel<ReportesResponseDTO>> getAllReportes() {

    List<EntityModel<ReportesResponseDTO>> reportes =
            reporteService.obtenerDtos()
                    .stream()
                    .map(assembler::toModel)
                    .toList();

    return CollectionModel.of(
            reportes,
            linkTo(methodOn(ReporteControllerV2.class)
                    .getAllReportes())
                    .withSelfRel()
                     );
    }

    @GetMapping("/{pedido}")
    @Operation(summary = "Obtener reportes por pedidos")
    public EntityModel<ReportesResponseDTO> getReporteByPedido(
        @PathVariable String pedido) {

    ReportesResponseDTO reporte =
            (ReportesResponseDTO) reporteService.buscarporPedido(pedido);

    return assembler.toModel(reporte);

    }

    @PostMapping
    @Operation(summary = "Crear un reporte")
    public ResponseEntity<EntityModel<ReportesResponseDTO>> crearReporte(
        @RequestBody ReportesRequestDTO request) {

    ReportesResponseDTO nuevo =
            reporteService.guardar(request);

    return ResponseEntity
            .created(
                linkTo(
                    methodOn(ReporteControllerV2.class)
                    .getReporteByPedido(nuevo.getPedido())
                ).toUri()
            )
            .body(assembler.toModel(nuevo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un reporte")
    public ResponseEntity<Void> borrarReporte(
        @PathVariable Long id) {

    reporteService.eliminar(id);

    return ResponseEntity.noContent().build();
}

}
