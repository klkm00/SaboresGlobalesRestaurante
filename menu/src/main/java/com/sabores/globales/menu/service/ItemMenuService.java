package com.sabores.globales.menu.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.sabores.globales.menu.dto.ItemMenuRequest;
import com.sabores.globales.menu.dto.ItemMenuResponse;
import com.sabores.globales.menu.dto.OrigenResponse;
import com.sabores.globales.menu.model.ItemMenuModel;
import com.sabores.globales.menu.model.OrigenMenuModel;
import com.sabores.globales.menu.repository.ItemMenuRepository;
import com.sabores.globales.menu.repository.OrigenMenuRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemMenuService {

    private final ItemMenuRepository itemMenuRepository;
    private final OrigenMenuRepository origenMenuRepository;

    // Convierte model en response
    private ItemMenuResponse toResponse(ItemMenuModel model) {
        ItemMenuResponse response = new ItemMenuResponse();
        response.setIdItem(model.getIdItem());
        response.setProductoId(model.getProductoId());
        response.setItemDisponible(model.getItemDisponible());

        //incluye el origen completo en la respuesta
        OrigenResponse origenResponse = new OrigenResponse();
        origenResponse.setIdOrigen(model.getOrigenItem().getIdOrigen());
        origenResponse.setNombreCarta(model.getOrigenItem().getNombreCarta());
        origenResponse.setDescripcionCarta(model.getOrigenItem().getDescripcionCarta());
        origenResponse.setCartaDisponible(model.getOrigenItem().getCartaDisponible());
        response.setOrigen(origenResponse);

        return response;
    }

    //listar todos
    public List<ItemMenuResponse> listarTodos() {
        return itemMenuRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //listar disponibles
    public List<ItemMenuResponse> listarDisponibles() {
        return itemMenuRepository.findByItemDisponibleTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //listar por origen
    public List<ItemMenuResponse> listarPorOrigen(UUID origenId) {
        return itemMenuRepository.findByOrigenItemIdOrigenAndItemDisponibleTrue(origenId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //buscar por id
    public ItemMenuResponse buscarPorId(UUID id) {
        return toResponse(itemMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemMenu no encontrado: " + id)));
    }

    //guardar
    public ItemMenuResponse guardar(ItemMenuRequest request) {
        OrigenMenuModel origen = origenMenuRepository.findById(request.getIdOrigen())
                .orElseThrow(() -> new RuntimeException("Origen no encontrado: " + request.getIdOrigen()));

        ItemMenuModel model = new ItemMenuModel();
        model.setOrigenItem(origen);
        model.setProductoId(request.getProductoId());
        model.setItemDisponible(request.getItemDisponible() != null ? request.getItemDisponible() : true);

        return toResponse(itemMenuRepository.save(model));
    }

    //actualizar
    public ItemMenuResponse actualizar(UUID id, ItemMenuRequest request) {
        ItemMenuModel model = itemMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemMenu no encontrado: " + id));

        OrigenMenuModel origen = origenMenuRepository.findById(request.getIdOrigen())
                .orElseThrow(() -> new RuntimeException("Origen no encontrado: " + request.getIdOrigen()));

        model.setOrigenItem(origen);
        model.setProductoId(request.getProductoId());
        model.setItemDisponible(request.getItemDisponible());

        return toResponse(itemMenuRepository.save(model));
    }

    //eliminar
    public void eliminar(UUID id) {
        itemMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemMenu no encontrado: " + id));
        itemMenuRepository.deleteById(id);
    }
}