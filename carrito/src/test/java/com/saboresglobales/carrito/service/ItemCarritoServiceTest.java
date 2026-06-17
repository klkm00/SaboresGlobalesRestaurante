package com.saboresglobales.carrito.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.saboresglobales.carrito.dto.ItemCarritoRequest;
import com.saboresglobales.carrito.dto.ItemCarritoResponse;
import com.saboresglobales.carrito.model.CarritoModel;
import com.saboresglobales.carrito.model.ItemCarritoModel;
import com.saboresglobales.carrito.repository.CarritoRepository;
import com.saboresglobales.carrito.repository.ItemCarritoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCarritoServiceTest {

    @Mock
    private ItemCarritoRepository itemCarritoRepository;

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private CarritoService carritoService;

    @InjectMocks
    private ItemCarritoService itemCarritoService;

    private CarritoModel carritoModel;
    private ItemCarritoModel itemModel;
    private ItemCarritoRequest itemRequest;

    @BeforeEach
    void setUp() {
        carritoModel = new CarritoModel();
        carritoModel.setIdCarrito(UUID.randomUUID());
        carritoModel.setClienteId(UUID.randomUUID());
        carritoModel.setEstado("ACTIVO");
        carritoModel.setTotal(0.0);

        itemModel = new ItemCarritoModel();
        itemModel.setIdItemCarrito(UUID.randomUUID());
        itemModel.setCarrito(carritoModel);
        itemModel.setProductoId(UUID.randomUUID());
        itemModel.setCantidad(2);
        itemModel.setPrecioUnitario(9.99);
        itemModel.setSubtotal(19.98);

        itemRequest = new ItemCarritoRequest();
        itemRequest.setProductoId(itemModel.getProductoId());
        itemRequest.setCantidad(2);
        itemRequest.setPrecioUnitario(9.99);
    }

    @Test
    void cuandoListarItems_debeRetornarLista() {
        UUID idCarrito = carritoModel.getIdCarrito();
        when(itemCarritoRepository.findByCarritoIdCarrito(idCarrito))
                .thenReturn(List.of(itemModel));

        List<ItemCarritoResponse> resultado = itemCarritoService.listarItems(idCarrito);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(itemModel.getProductoId(), resultado.get(0).getProductoId());
    }

    @Test
    void cuandoAgregarItemNuevo_debeRetornarItemResponse() {
        UUID idCarrito = carritoModel.getIdCarrito();
        when(carritoRepository.findById(idCarrito)).thenReturn(Optional.of(carritoModel));
        when(itemCarritoRepository.findByCarritoIdCarritoAndProductoId(idCarrito, itemRequest.getProductoId()))
                .thenReturn(null);
        when(itemCarritoRepository.save(any(ItemCarritoModel.class))).thenReturn(itemModel);
        when(itemCarritoRepository.findByCarritoIdCarrito(idCarrito)).thenReturn(List.of(itemModel));

        ItemCarritoResponse resultado = itemCarritoService.agregarItem(idCarrito, itemRequest);

        assertNotNull(resultado);
        assertEquals(itemModel.getProductoId(), resultado.getProductoId());
    }

    @Test
    void cuandoAgregarItemExistente_debeAumentarCantidad() {
        UUID idCarrito = carritoModel.getIdCarrito();
        when(carritoRepository.findById(idCarrito)).thenReturn(Optional.of(carritoModel));
        when(itemCarritoRepository.findByCarritoIdCarritoAndProductoId(idCarrito, itemRequest.getProductoId()))
                .thenReturn(itemModel);
        when(itemCarritoRepository.save(any(ItemCarritoModel.class))).thenReturn(itemModel);
        when(itemCarritoRepository.findByCarritoIdCarrito(idCarrito)).thenReturn(List.of(itemModel));

        ItemCarritoResponse resultado = itemCarritoService.agregarItem(idCarrito, itemRequest);

        assertNotNull(resultado);
        verify(itemCarritoRepository).save(any(ItemCarritoModel.class));
    }

    @Test
    void cuandoQuitarItem_debeEliminarCorrectamente() {
        UUID idItem = itemModel.getIdItemCarrito();
        when(itemCarritoRepository.findById(idItem)).thenReturn(Optional.of(itemModel));
        when(itemCarritoRepository.findByCarritoIdCarrito(any(UUID.class))).thenReturn(List.of());

        assertDoesNotThrow(() -> itemCarritoService.quitarItem(idItem));
        verify(itemCarritoRepository).deleteById(idItem);
    }

    @Test
    void cuandoQuitarItemNoExiste_debeLanzarExcepcion() {
        when(itemCarritoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> itemCarritoService.quitarItem(UUID.randomUUID()));
    }

    @Test
    void cuandoVaciarCarrito_debeEliminarTodosLosItems() {
        UUID idCarrito = carritoModel.getIdCarrito();
        when(itemCarritoRepository.findByCarritoIdCarrito(idCarrito))
                .thenReturn(List.of(itemModel));

        assertDoesNotThrow(() -> itemCarritoService.vaciarCarrito(idCarrito));
        verify(itemCarritoRepository).deleteAll(any());
    }
}