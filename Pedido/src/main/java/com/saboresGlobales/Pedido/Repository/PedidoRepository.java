package com.saboresGlobales.Pedido.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saboresGlobales.Pedido.Model.Pedido;

@Repository
public interface PedidoRepository  extends JpaRepository<Pedido,Long>{

@Query("SELECT p FROM Pedido p WHERE p.orden.id = :ordenId")
List<Pedido> findByOrdenId(@Param("ordenId") Long ordenId);


}
