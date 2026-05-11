package com.sabores.globales.menu.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sabores.globales.menu.model.ItemMenuModel;
import com.sabores.globales.menu.repository.ItemMenuRepository;
import com.sabores.globales.menu.repository.OrigenMenuRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemMenuService {
    @Autowired
    private final ItemMenuRepository itemMenuRepository;
    private final OrigenMenuRepository origenMenuRepository;

    //listar todos los items
    public List<ItemMenuModel> listarTodos() {
        return itemMenuRepository.findAll();
    }

    //listar todos los items disponibles
    public List<ItemMenuModel> listarDisponibles() {
        return itemMenuRepository.findByItemDisponibleTrue();
    }

    //listar los items por origen
    public List<ItemMenuModel> listarPorOrigen(UUID origenId) {
        return itemMenuRepository.findByOrigenItemIdOrigenAndItemDisponibleTrue(origenId);
    }

     //buscar item por id
    public ItemMenuModel buscarPorId(UUID id) {
        return itemMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemMenu no encontrado con id: " + id));
    }

    //guardar item nuevo
    public ItemMenuModel guardar(ItemMenuModel item) {
        // Verifica que el origen existe antes de guardar
        origenMenuRepository.findById(item.getOrigenItem().getIdOrigen())
                .orElseThrow(() -> new RuntimeException("Origen no encontrado"));
        return itemMenuRepository.save(item);
    }

    //actualizar item
    public ItemMenuModel actualizar(UUID id, ItemMenuModel datosNuevoItem) {
        ItemMenuModel item = buscarPorId(id);
        item.setOrigenItem(datosNuevoItem.getOrigenItem());
        item.setProductoId(datosNuevoItem.getProductoId());
        item.setItemDisponible(datosNuevoItem.getItemDisponible());
        return itemMenuRepository.save(item);
    }

    //eliminar item de una categoria
    public void eliminar(UUID id) {
        buscarPorId(id);
        itemMenuRepository.deleteById(id);
    }
}
