
package com.saboresglobales.inventario.service;
import org.springframework.stereotype.Service;
import java.util.*;
import com.example.ms_inventario.repository.*;
import com.example.ms_inventario.model.*;

@Service
public class InventarioService {
    private final InventarioRepository repo;
    public InventarioService(InventarioRepository repo){this.repo=repo;}

    public List<Inventario> getAll(){return repo.findAll();}
    public Inventario save(Inventario i){return repo.save(i);}
}
