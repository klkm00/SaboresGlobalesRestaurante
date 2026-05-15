package com.SaboresGlobales.reportes.Reportes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SaboresGlobales.reportes.Reportes.Modelo.*;
import java.util.List;


@Repository
public interface ReporteRepository  extends JpaRepository<Reporte,Long>{


    List<Reporte> findByPedidoContainingIgnoreCase(String pedido);
}