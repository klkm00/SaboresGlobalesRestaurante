package com.saboresglobales.carrito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.saboresglobales.carrito.model.CarritoModel;
import com.saboresglobales.carrito.model.ItemCarritoModel;
import com.saboresglobales.carrito.repository.ItemCarritoRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemCarritoService {
    @Autowired
    
    private final ItemCarritoRepository itemCarritoRepository;
    private final CarritoService carritoService;

    //listar todos los items de un carrito
    public List<ItemCarritoModel> listarItems(UUID idCarrito) {
        return itemCarritoRepository.findByCarritoIdCarrito(idCarrito);
    }

    //agregar un item al carrito
    public ItemCarritoModel agregarItem(UUID idCarrito, UUID productoId, Integer cantidad, Double precioUnitario) {
        CarritoModel carrito = carritoService.buscarPorId(idCarrito);

        //si el producto ya esta en el carrito, aumenta su cantidad
        ItemCarritoModel itemExistente = itemCarritoRepository
                .findByCarritoIdCarritoAndProductoId(idCarrito, productoId);

        if (itemExistente != null) {
            itemExistente.setCantidad(itemExistente.getCantidad() + cantidad);
            itemExistente.setSubtotal(itemExistente.getCantidad() * precioUnitario);
            itemCarritoRepository.save(itemExistente);
        } else {
            itemExistente = new ItemCarritoModel();
            itemExistente.setCarrito(carrito);
            itemExistente.setProductoId(productoId);
            itemExistente.setCantidad(cantidad);
            itemExistente.setPrecioUnitario(precioUnitario);
            itemExistente.setSubtotal(cantidad * precioUnitario);
            itemCarritoRepository.save(itemExistente);
        }
        recalcularTotal(idCarrito);
        return itemExistente;
    }

    //quitar un item del carrito
    public void quitarItem(UUID idItemCarrito) {
        ItemCarritoModel item = itemCarritoRepository.findById(idItemCarrito)
                .orElseThrow(() -> new RuntimeException("Item no encontrado: " + idItemCarrito));
        UUID idCarrito = item.getCarrito().getIdCarrito();
        itemCarritoRepository.deleteById(idItemCarrito);
        recalcularTotal(idCarrito);
    }

    //vaciar el carrito completo
    public void vaciarCarrito(UUID idCarrito) {
        List<ItemCarritoModel> items = itemCarritoRepository.findByCarritoIdCarrito(idCarrito);
        itemCarritoRepository.deleteAll(items);
        carritoService.actualizarTotal(idCarrito, 0.0);
    }

    //recalcular el total del carrito sumando todos los items
    private void recalcularTotal(UUID idCarrito) {
        List<ItemCarritoModel> items = itemCarritoRepository.findByCarritoIdCarrito(idCarrito);
        Double total = items.stream()
                .mapToDouble(ItemCarritoModel::getSubtotal)
                .sum();
        carritoService.actualizarTotal(idCarrito, total);
    }
}