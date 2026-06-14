package com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO.RolRequesrDTO;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO.RolResponseDTO;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Modelo.Rol;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Repository.RolRepository;
@ExtendWith(MockitoExtension.class)
public class RolServiceTest {
    @Mock
    private RolRepository repo;

    @InjectMocks
    private RolService serv;


    @Test
    void testActualizar() {
        Long id = 1L;
    
        Rol rolActualizado = new Rol(id,
             "Empleado");
        RolRequesrDTO dto = new RolRequesrDTO("Empleado");

        when(repo.findById(id)).thenReturn(Optional.of(new Rol()));
        
        when(repo.save(any(Rol.class)))
            .thenReturn(rolActualizado);

        Optional<RolResponseDTO> resultado =
        serv.actualizar(id, dto);

        assertNotNull(rolActualizado);
        assertEquals(id, resultado.get().getId());
        assertEquals("Empleado", resultado.get().getNombre());
    }

    @Test
    public void testEliminar() {
        Long id = 1L;

        doNothing().when(repo).deleteById(id);

        serv.eliminar(id);

        verify(repo,times(1)).deleteById(id);
    }

    @Test
    public void testGuardar() {
       RolRequesrDTO dto = new RolRequesrDTO(
        "Cliente"
        );

        Rol rolGuardado = new Rol(
            1L, 
            "Cliente"
            );

        when(repo.save(any(Rol.class)))
                .thenReturn(rolGuardado);

        RolResponseDTO resultado =
            serv.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Cliente", resultado.getNombre());
    }

    @Test
    public void testObtenerPorId() {
        Long id = 1L;

        Rol rol = new Rol(id, 
            "Cliente");

            when(repo.findById(id))
            .thenReturn(Optional.of(rol));

        Optional<RolResponseDTO> resultado =
            serv.obtenerPorId(id);
        
        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        assertEquals("Cliente", 
        resultado.get().getNombre());
        
    }

    @Test
    public void testObtenerTodas() {
        Rol rol = new Rol(
            1L,
            "Cliente"
        );

        when(repo.findAll()).thenReturn(List.of(rol));

        List<RolResponseDTO> resultado =
        serv.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Cliente", resultado.get(0).getNombre());

        
    }
}
