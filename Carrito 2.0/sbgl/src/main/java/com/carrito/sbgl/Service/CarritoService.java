package com.carrito.sbgl.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.carrito.sbgl.DTO.CarritoRequest;
import com.carrito.sbgl.DTO.CarritoResponse;
import com.carrito.sbgl.Model.Carrito;
import com.carrito.sbgl.Repository.CarritoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarritoService {
    private final CarritoRepository repository;

    public List<CarritoResponse> obtenerTodos(){
        return repository.findAll().stream().map(carrito -> new CarritoResponse(carrito.getId(),carrito.getId_producto(),carrito.getCantidad()))
        .toList();
    }

    public Optional <CarritoResponse> obtenerPorId(Long id){
        return repository.findById(id).map(carrito -> new CarritoResponse(carrito.getId(),carrito.getId_producto(),carrito.getCantidad()));        
    }

    public CarritoResponse guardar(CarritoRequest fto){
        Carrito carrito = new Carrito();
        carrito.setId_producto(fto.getId_producto());
        carrito.setCantidad(fto.getCantidad());
        Carrito nuevoCarrito = repository.save(carrito);
        return new CarritoResponse(nuevoCarrito.getId(),nuevoCarrito.getId_producto(),nuevoCarrito.getCantidad()); 
    }

    public Optional<CarritoResponse> actualizar(Long id, CarritoRequest dto){
        return repository.findById(id)
        .map(carrito ->{
            carrito.setId_producto(dto.getId_producto());
            carrito.setCantidad(dto.getCantidad());
            
            Carrito actuCarrito = repository.save(carrito);

            return new CarritoResponse(actuCarrito.getId(),actuCarrito.getId_producto(),actuCarrito.getCantidad());

        });
    }

    public void eliminar(Long id){
        repository.deleteById(id);
    }
}
