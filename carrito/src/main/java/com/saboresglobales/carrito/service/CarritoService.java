package com.saboresglobales.carrito.service;


import org.springframework.stereotype.Service;
import com.saboresglobales.carrito.dto.CarritoRequest;
import com.saboresglobales.carrito.dto.CarritoResponse;
import com.saboresglobales.carrito.dto.ItemCarritoResponse;
import com.saboresglobales.carrito.model.CarritoModel;
import com.saboresglobales.carrito.repository.CarritoRepository;
import com.saboresglobales.carrito.repository.ItemCarritoRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final ItemCarritoRepository itemCarritoRepository;

    //convierte model en response 
    public CarritoResponse toResponse(CarritoModel model) {
        CarritoResponse response = new CarritoResponse();
        response.setIdCarrito(model.getIdCarrito());
        response.setClienteId(model.getClienteId());
        response.setEstado(model.getEstado());
        response.setTotal(model.getTotal());
        response.setFechaCreado(model.getFechaCreado());
        response.setFechaActualizado(model.getFechaActualizado());

        //incluye los items del carrito en la respuesta
        List<ItemCarritoResponse> items = itemCarritoRepository
                .findByCarritoIdCarrito(model.getIdCarrito())
                .stream()
                .map(item -> {
                    ItemCarritoResponse itemResponse = new ItemCarritoResponse();
                    itemResponse.setIdItemCarrito(item.getIdItemCarrito());
                    itemResponse.setProductoId(item.getProductoId());
                    itemResponse.setCantidad(item.getCantidad());
                    itemResponse.setPrecioUnitario(item.getPrecioUnitario());
                    itemResponse.setSubtotal(item.getSubtotal());
                    return itemResponse;
                })
                .toList();

        response.setItems(items);
        return response;
    }

    //crea un carrito nuevo para un cliente
    public CarritoResponse crearCarrito(CarritoRequest request) {
        CarritoModel carrito = new CarritoModel();
        carrito.setClienteId(request.getClienteId());
        carrito.setEstado("ACTIVO");
        carrito.setTotal(0.0);
        carrito.setFechaCreado(LocalDateTime.now());
        carrito.setFechaActualizado(LocalDateTime.now());
        return toResponse(carritoRepository.save(carrito));
    }

    //obtiene el carrito activo de un cliente
    public CarritoResponse obtenerCarritoActivo(UUID clienteId) {
        CarritoModel carrito = carritoRepository.findByClienteIdAndEstado(clienteId, "ACTIVO");
        if (carrito == null) {
            throw new RuntimeException("No hay carrito activo para el cliente: " + clienteId);
        }
        return toResponse(carrito);
    }

    //buscar por id
    public CarritoResponse buscarPorId(UUID idCarrito) {
        return toResponse(carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado: " + idCarrito)));
    }

    //cctualiza el total del carrito
    public void actualizarTotal(UUID idCarrito, Double nuevoTotal) {
        CarritoModel carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado: " + idCarrito));
        carrito.setTotal(nuevoTotal);
        carrito.setFechaActualizado(LocalDateTime.now());
        carritoRepository.save(carrito);
    }

    //confirma el carrito para enviarlo a pagos
    public CarritoResponse confirmarCarrito(UUID idCarrito) {
        CarritoModel carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado: " + idCarrito));
        if (!carrito.getEstado().equals("ACTIVO")) {
            throw new RuntimeException("Solo se puede confirmar un carrito ACTIVO");
        }
        carrito.setEstado("CONFIRMADO");
        carrito.setFechaActualizado(LocalDateTime.now());
        return toResponse(carritoRepository.save(carrito));
    }

    //cancela el carrito
    public CarritoResponse cancelarCarrito(UUID idCarrito) {
        CarritoModel carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado: " + idCarrito));
        carrito.setEstado("CANCELADO");
        carrito.setTotal(0.0);
        carrito.setFechaActualizado(LocalDateTime.now());
        return toResponse(carritoRepository.save(carrito));
    }
}