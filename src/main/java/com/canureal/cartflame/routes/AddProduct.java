package com.canureal.cartflame.routes;

import com.canureal.cartflame.models.ProductRepository;
import com.canureal.cartflame.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class AddProduct {
   @Autowired
   private ProductRepository productRepository;

   @PostMapping
    public ResponseEntity<Products> createProduct(@RequestBody Products product) {
       Products saved = productRepository.save(product);
       return ResponseEntity.ok(saved);
   }
}
