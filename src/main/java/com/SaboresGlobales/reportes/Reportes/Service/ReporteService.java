package com.SaboresGlobales.reportes.Reportes.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.SaboresGlobales.reportes.Reportes.DTO.ReportesRequestDTO;
import com.SaboresGlobales.reportes.Reportes.DTO.ReportesResponseDTO;
import com.SaboresGlobales.reportes.Reportes.Modelo.Reporte;
import com.SaboresGlobales.reportes.Reportes.Repository.ReporteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReporteService {
    private final ReporteRepository repo;

    private ReportesResponseDTO maptoDTO(Reporte reporte){
        return new ReportesResponseDTO(
            reporte.getId(),
            reporte.getPedido(),
            reporte.getPagos(),
            reporte.getInventario(),
            reporte.getDelivery()
        );
    }

    public List<ReportesResponseDTO> obtenerDtos(){
        return repo.findAll()
        .stream().map(this::maptoDTO).collect(Collectors.toList());
    }

    public Optional<ReportesResponseDTO> obtenerporID(long id){
        return repo.findById(id).map(this::maptoDTO);
    
    }

    public ReportesResponseDTO guardar(ReportesRequestDTO dto){
       
        Reporte reporte = new Reporte(null, dto.getPedido(),dto.getPagos(),dto.getInventario(),dto.getDelivery());

        return maptoDTO(repo.save(reporte));
    }

    public void eliminar(Long id){
        repo.deleteById(id);
    }

    public List<ReportesResponseDTO> buscarporPedido(String texto){
        return repo.findByPedidoContainingIgnoreCase(texto)
        .stream().map(this::maptoDTO).collect(Collectors.toList());
    }


}
