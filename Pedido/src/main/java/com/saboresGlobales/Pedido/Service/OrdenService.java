package com.saboresGlobales.Pedido.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.saboresGlobales.Pedido.Model.Orden;
import com.saboresGlobales.Pedido.Repository.OrdenRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class OrdenService {
    private final OrdenRepository ordenRepository;


    public List<Orden> obtenerTodas(){
        return ordenRepository.findAll();
    }

    public Optional<Orden> obtenerporId(Long id){
        return ordenRepository.findById(id);

    }
    public Orden save(Orden orden){
        return ordenRepository.save(orden);

    }

    public void delete(Long id){
        ordenRepository.deleteById(id);
    }
}
