package com.SaboresGlobales.reportes.Reportes.Assembler;


import org.springframework.stereotype.Component;

import com.SaboresGlobales.reportes.Reportes.Controller.ReporteControllerV2;
import com.SaboresGlobales.reportes.Reportes.DTO.ReportesResponseDTO;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class reporteModelAssembler implements RepresentationModelAssembler<ReportesResponseDTO, EntityModel<ReportesResponseDTO>> {

    @Override
    public EntityModel<ReportesResponseDTO> toModel(ReportesResponseDTO reporte) {

        return EntityModel.of(
                reporte,

                linkTo(
                    methodOn(ReporteControllerV2.class)
                    .getReporteByPedido(reporte.getPedido())
                ).withSelfRel(),

                linkTo(
                    methodOn(ReporteControllerV2.class)
                    .getAllReportes()
                ).withRel("reportes")
        );
    }
}
