package com.sabores.globales.menu.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sabores.globales.menu.model.OrigenMenuModel;
import com.sabores.globales.menu.repository.OrigenMenuRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrigenMenuService {
    @Autowired

    private final OrigenMenuRepository origenMenuRepository;

    //listar todas las categorias por actividad
    public List<OrigenMenuModel> listarActivos() {
        return origenMenuRepository.findByCartaDisponibleTrue();
    }

    //buscar categoria por id
    public OrigenMenuModel buscarPorId(UUID id) {
        return origenMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Origen no encontrado con id: " + id));
    }


    //guardar una nueva categoria
    public OrigenMenuModel guardar(OrigenMenuModel origen) {
        return origenMenuRepository.save(origen);
    }

    //actualizar una categoria
    public OrigenMenuModel actualizar(UUID id, OrigenMenuModel datosNuevos) {
        OrigenMenuModel origen = buscarPorId(id);
        origen.setNombreCarta(datosNuevos.getNombreCarta());
        origen.setDescripcionCarta(datosNuevos.getDescripcionCarta());
        origen.setCartaDisponible(datosNuevos.getCartaDisponible());
        return origenMenuRepository.save(origen);
    }
    
    //eliminar una categoria
    public void eliminar(UUID id) {
        buscarPorId(id); //se verifica que existe antes de eliminar
        origenMenuRepository.deleteById(id);
    }
    
}
