package com.saboresGlobales.Delivery.delivery.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saboresGlobales.Delivery.delivery.Modelo.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery,Long>{
    
    List<Delivery> findByRepartidorContainingIgnoreCase(String repartidor);


}
