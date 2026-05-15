package com.saboresglobales.carrito.service;

import org.springframework.stereotype.Service;
import com.saboresglobales.carrito.dto.ItemCarritoRequest;
import com.saboresglobales.carrito.dto.ItemCarritoResponse;
import com.saboresglobales.carrito.model.CarritoModel;
import com.saboresglobales.carrito.model.ItemCarritoModel;
import com.saboresglobales.carrito.repository.CarritoRepository;
import com.saboresglobales.carrito.repository.ItemCarritoRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemCarritoService {

    private final ItemCarritoRepository itemCarritoRepository;
    private final CarritoRepository carritoRepository;
    private final CarritoService carritoService;

    //convierte el model en response
    private ItemCarritoResponse toResponse(ItemCarritoModel model) {
        ItemCarritoResponse response = new ItemCarritoResponse();
        response.setIdItemCarrito(model.getIdItemCarrito());
        response.setProductoId(model.getProductoId());
        response.setCantidad(model.getCantidad());
        response.setPrecioUnitario(model.getPrecioUnitario());
        response.setSubtotal(model.getSubtotal());
        return response;
    }

    //listar todos los items de un carrito
    public List<ItemCarritoResponse> listarItems(UUID idCarrito) {
        return itemCarritoRepository.findByCarritoIdCarrito(idCarrito)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //agregar un producto al carrito
    public ItemCarritoResponse agregarItem(UUID idCarrito, ItemCarritoRequest request) {
        CarritoModel carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado: " + idCarrito));

        //si el producto ya esta en el carrito, aumenta la cantidad de este
        ItemCarritoModel itemExistente = itemCarritoRepository
                .findByCarritoIdCarritoAndProductoId(idCarrito, request.getProductoId());

        if (itemExistente != null) {
            itemExistente.setCantidad(itemExistente.getCantidad() + request.getCantidad());
            itemExistente.setSubtotal(itemExistente.getCantidad() * request.getPrecioUnitario());
            itemCarritoRepository.save(itemExistente);
            recalcularTotal(idCarrito);
            return toResponse(itemExistente);
        }

        ItemCarritoModel nuevoItem = new ItemCarritoModel();
        nuevoItem.setCarrito(carrito);
        nuevoItem.setProductoId(request.getProductoId());
        nuevoItem.setCantidad(request.getCantidad());
        nuevoItem.setPrecioUnitario(request.getPrecioUnitario());
        nuevoItem.setSubtotal(request.getCantidad() * request.getPrecioUnitario());
        itemCarritoRepository.save(nuevoItem);

        recalcularTotal(idCarrito);
        return toResponse(nuevoItem);
    }

    //quitar un item del carrito
    public void quitarItem(UUID idItemCarrito) {
        ItemCarritoModel item = itemCarritoRepository.findById(idItemCarrito)
                .orElseThrow(() -> new RuntimeException("Item no encontrado: " + idItemCarrito));
        UUID idCarrito = item.getCarrito().getIdCarrito();
        itemCarritoRepository.deleteById(idItemCarrito);
        recalcularTotal(idCarrito);
    }

    //vaciar todos los items del carrito
    public void vaciarCarrito(UUID idCarrito) {
        List<ItemCarritoModel> items = itemCarritoRepository.findByCarritoIdCarrito(idCarrito);
        itemCarritoRepository.deleteAll(items);
        carritoService.actualizarTotal(idCarrito, 0.0);
    }

    //recalcula el total sumando todos los subtotales
    private void recalcularTotal(UUID idCarrito) {
        List<ItemCarritoModel> items = itemCarritoRepository.findByCarritoIdCarrito(idCarrito);
        Double total = items.stream()
                .mapToDouble(ItemCarritoModel::getSubtotal)
                .sum();
        carritoService.actualizarTotal(idCarrito, total);
    }
}