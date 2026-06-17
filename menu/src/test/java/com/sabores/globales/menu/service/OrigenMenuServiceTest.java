package com.sabores.globales.menu.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.sabores.globales.menu.dto.OrigenRequest;
import com.sabores.globales.menu.dto.OrigenResponse;
import com.sabores.globales.menu.model.OrigenMenuModel;
import com.sabores.globales.menu.repository.OrigenMenuRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrigenMenuServiceTest {

    @Mock
    private OrigenMenuRepository origenMenuRepository;  // repositorio simulado

    @InjectMocks
    private OrigenMenuService origenMenuService;        // clase que vamos a probar

    // datos de prueba
    private OrigenMenuModel origenModel;
    private OrigenRequest origenRequest;

    @BeforeEach
    void setUp() {
        //se ejecuta antes de cada prueba (prepara los datos)
        origenModel = new OrigenMenuModel();
        origenModel.setIdOrigen(UUID.randomUUID());
        origenModel.setNombreCarta("Comida Italiana");
        origenModel.setDescripcionCarta("Pastas y pizzas");
        origenModel.setCartaDisponible(true);

        origenRequest = new OrigenRequest();
        origenRequest.setNombreCarta("Comida Italiana");
        origenRequest.setDescripcionCarta("Pastas y pizzas");
    }

    @Test
    void cuandoGuardar_debeRetornarOrigenResponse() {
        //simula que el repository guarda y devuelve el modelo
        when(origenMenuRepository.save(any(OrigenMenuModel.class))).thenReturn(origenModel);

        //llama al metodo original
        OrigenResponse resultado = origenMenuService.guardar(origenRequest);

        //verifica que el resultado esta bien
        assertNotNull(resultado);
        assertEquals("Comida Italiana", resultado.getNombreCarta());
    }

    @Test
    void cuandoBuscarPorIdExiste_debeRetornarOrigenResponse() {
        UUID id = origenModel.getIdOrigen();

        //simula que el repository encuentra el origen
        when(origenMenuRepository.findById(id)).thenReturn(Optional.of(origenModel));

        //llama al metodo original
        OrigenResponse resultado = origenMenuService.buscarPorId(id);

        //verifica que el resultado esta bien
        assertNotNull(resultado);
        assertEquals("Comida Italiana", resultado.getNombreCarta());
    }

    @Test
    void cuandoBuscarPorIdNoExiste_debeLanzarExcepcion() {
        UUID id = UUID.randomUUID();

        //simula que el repository no encuentra nada
        when(origenMenuRepository.findById(id)).thenReturn(Optional.empty());

        //verifica que lanza excepcion
        assertThrows(RuntimeException.class, () -> origenMenuService.buscarPorId(id));
    }

    @Test
    void cuandoListarActivos_debeRetornarLista() {
        //simula que el repository devuelve una lista con un origen
        when(origenMenuRepository.findByCartaDisponibleTrue()).thenReturn(List.of(origenModel));

        // llama al metodo original
        List<OrigenResponse> resultado = origenMenuService.listarActivos();

        //verifica que la lista no esta vacia
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Comida Italiana", resultado.get(0).getNombreCarta());
    }

    @Test
    void cuandoEliminar_debeEliminarCorrectamente() {
        UUID id = origenModel.getIdOrigen();

        when(origenMenuRepository.findById(id)).thenReturn(Optional.of(origenModel));

        //verifica que no lanza excepcion al eliminar
        assertDoesNotThrow(() -> origenMenuService.eliminar(id));

        //verifica que el repository llamo a deleteById
        verify(origenMenuRepository).deleteById(id);
    }
}