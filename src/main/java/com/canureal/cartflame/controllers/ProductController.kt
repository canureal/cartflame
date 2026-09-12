package com.canureal.cartflame.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import com.canureal.cartflame.dtos.ProductDto
import com.canureal.cartflame.models.Products
import com.canureal.cartflame.services.ProductService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping

@RestController
public class ProductController(
    private val productService: ProductService
) {
    @PostMapping("/products")
    public fun addNewProduct(@RequestBody dto: ProductDto.AddNewProductDto): ProductDto.AddNewProductDto {
        productService.addNewItem(dto)
        return dto
    }
    @GetMapping("/products")
    public fun getProducts(): List<Products> {
        return productService.getAllProduct()
    }

    @DeleteMapping("/products/{id}")
    public fun deleteProduct(@RequestBody id: Int, deleteQuantity: UInt) {
        return productService.deleteProduct(id, deleteQuantity)
    }
}