package com.sabores.globales.menu.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.sabores.globales.menu.dto.OrigenRequest;
import com.sabores.globales.menu.dto.OrigenResponse;
import com.sabores.globales.menu.model.OrigenMenuModel;
import com.sabores.globales.menu.repository.OrigenMenuRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrigenMenuService {

    private final OrigenMenuRepository origenMenuRepository;

    //convierte model en response
    private OrigenResponse toResponse(OrigenMenuModel model) {
        OrigenResponse response = new OrigenResponse();
        response.setIdOrigen(model.getIdOrigen());
        response.setNombreCarta(model.getNombreCarta());
        response.setDescripcionCarta(model.getDescripcionCarta());
        response.setCartaDisponible(model.getCartaDisponible());
        return response;
    }

    //convierte request en model
    private OrigenMenuModel toModel(OrigenRequest request) {
        OrigenMenuModel model = new OrigenMenuModel();
        model.setNombreCarta(request.getNombreCarta());
        model.setDescripcionCarta(request.getDescripcionCarta());
        return model;
    }

    //listar todos los activos
    public List<OrigenResponse> listarActivos() {
        return origenMenuRepository.findByCartaDisponibleTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //buscar por id
    public OrigenResponse buscarPorId(UUID id) {
        return toResponse(origenMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Origen no encontrado: " + id)));
    }

    //guardar
    public OrigenResponse guardar(OrigenRequest request) {
        return toResponse(origenMenuRepository.save(toModel(request)));
    }

    //actualizar
    public OrigenResponse actualizar(UUID id, OrigenRequest request) {
        OrigenMenuModel model = origenMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Origen no encontrado: " + id));
        model.setNombreCarta(request.getNombreCarta());
        model.setDescripcionCarta(request.getDescripcionCarta());
        return toResponse(origenMenuRepository.save(model));
    }

    // Eliminar
    public void eliminar(UUID id) {
        origenMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Origen no encontrado: " + id));
        origenMenuRepository.deleteById(id);
    }
}