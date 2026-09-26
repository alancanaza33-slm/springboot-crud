package com.ejemplo.crud.controller;

import com.ejemplo.crud.model.Producto;
import com.ejemplo.crud.repository.ProductoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Endpoints para la gestión de productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    @Operation(summary = "Obtener todos los productos")
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto")
    public Producto crear(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    // Endpoint PUT (Actualizar en Base de Datos)
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto por ID")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto detallesProducto) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(detallesProducto.getNombre());
                    producto.setPrecio(detallesProducto.getPrecio());
                    Producto actualizado = productoRepository.save(producto);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint DELETE (Eliminar en Base de Datos)
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto por ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(producto -> {
                    productoRepository.delete(producto);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}