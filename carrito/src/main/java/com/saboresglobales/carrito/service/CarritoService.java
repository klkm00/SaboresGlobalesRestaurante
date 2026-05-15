package com.saboresglobales.carrito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.saboresglobales.carrito.model.CarritoModel;
import com.saboresglobales.carrito.repository.CarritoRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarritoService {
    @Autowired
    private final CarritoRepository carritoRepository;

    //crear un carrito nuevo para un cliente
    public CarritoModel crearCarrito(UUID clienteId) {
        CarritoModel carrito = new CarritoModel();
        carrito.setClienteId(clienteId);
        carrito.setEstado("ACTIVO");
        carrito.setTotal(0.0);
        carrito.setFechaCreado(LocalDateTime.now());
        carrito.setFechaActualizado(LocalDateTime.now());
        return carritoRepository.save(carrito);
    }

    //obtiene el carrito activo de un cliente
    public CarritoModel obtenerCarritoActivo(UUID clienteId) {
        CarritoModel carrito = carritoRepository.findByClienteIdAndEstado(clienteId, "ACTIVO");
        if (carrito == null) {
            throw new RuntimeException("No se encontro un carrito activo para el cliente: " + clienteId);
        }
        return carrito;
    }

    //buscar por id del carrito
    public CarritoModel buscarPorId(UUID idCarrito) {
        return carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado: " + idCarrito));
    }

    //actualiza el total sumando todos los valores de los items
    public CarritoModel actualizarTotal(UUID idCarrito, Double nuevoTotal) {
        CarritoModel carrito = buscarPorId(idCarrito);
        carrito.setTotal(nuevoTotal);
        carrito.setFechaActualizado(LocalDateTime.now());
        return carritoRepository.save(carrito);
    }

    //confirma datos del carrito para enviarlo a pagos
    public CarritoModel confirmarCarrito(UUID idCarrito) {
        CarritoModel carrito = buscarPorId(idCarrito);
        if (!carrito.getEstado().equals("ACTIVO")) {
            throw new RuntimeException("Solo se puede confirmar un carrito en estado ACTIVO");
        }
        carrito.setEstado("CONFIRMADO");
        carrito.setFechaActualizado(LocalDateTime.now());
        return carritoRepository.save(carrito);
    }

    //vaciar un carrito y marcarlo como cancelado 
    public CarritoModel cancelarCarrito(UUID idCarrito) {
        CarritoModel carrito = buscarPorId(idCarrito);
        carrito.setEstado("CANCELADO");
        carrito.setTotal(0.0);
        carrito.setFechaActualizado(LocalDateTime.now());
        return carritoRepository.save(carrito);
    }
}