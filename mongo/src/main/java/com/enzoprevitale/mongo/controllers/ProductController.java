package com.enzoprevitale.mongo.controllers;

import com.enzoprevitale.mongo.dtos.ProductDto;
import com.enzoprevitale.mongo.models.Product;
import com.enzoprevitale.mongo.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Product>> getOne(@PathVariable String name) {
        return ResponseEntity.ok(repository.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Product> postProduct(@RequestBody ProductDto dto) {
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        repository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchProduct(@PathVariable String id, @RequestBody Map<String, ?> fields) {
        Optional<Product> product = repository.findById(id);
        return product.map(p -> {
            fields.forEach((key, value) -> {
                switch (key) {
                    case "name" -> p.setName((String) value);
                    case "price" -> p.setPrice((float) value);
                    case "description" -> p.setDescription((String) value);
                    case "releaseDate" -> p.setReleaseDate((LocalDate) value);
                }
            });
            return ResponseEntity.accepted().body(p);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        Optional<Product> product = repository.findById(id);
        return product.map(p -> {
            repository.delete(p);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
