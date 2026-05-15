
package com.example.productos.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.productos.repository.ProductoRepository;
import com.example.productos.repository.RecetaRepository;
import com.example.productos.model.Producto;
import com.example.productos.model.Receta;

@Service
public class ProductoService {

    private final ProductoRepository productoRepo;
    private final RecetaRepository recetaRepo;

    public ProductoService(ProductoRepository productoRepo, RecetaRepository recetaRepo) {
        this.productoRepo = productoRepo;
        this.recetaRepo = recetaRepo;
    }

    public Producto crear(Producto p) {
        return productoRepo.save(p);
    }

    public List<Receta> obtenerReceta(Long productoId) {
        return recetaRepo.findByProductoId(productoId);
    }
}
