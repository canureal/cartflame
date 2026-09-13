package com.canureal.cartflame.services

import com.canureal.cartflame.models.ProductRepository
import org.springframework.stereotype.Service
import com.canureal.cartflame.dtos.ProductDto
import com.canureal.cartflame.dtos.EmailJobDto
import com.canureal.cartflame.exceptions.ProductNotFoundException
import com.canureal.cartflame.models.Products
import com.rabbitmq.stream.amqp.UnsignedInteger
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.web.client.HttpStatusCodeException
import java.util.Optional

@Service
public class ProductService(
    private val productRepository: ProductRepository,
    private val rabbitTemplate: RabbitTemplate,
) {
    public fun addNewItem(dto: ProductDto.AddNewProductDto): Products {
        val product = Products()
        product.itemQuantity = dto.itemQuantity;
        product.itemCategory = dto.itemCategory;
        product.itemName = dto.itemName;

        val saved = productRepository.save(product)

        // let's add rabbitmq producer.
        rabbitTemplate.convertAndSend(
            "email.exchange",
            "email.send",
            EmailJobDto(
                // I still did not add my admin role but let's use my email
                // dang it
                to = "dnzdyn2012@gmail.com",
                subject = "New product added!",
                body = """
                    Item name: ${saved.itemName},
                    Quantity: ${saved.itemQuantity},
                    Category: ${saved.itemCategory},
                """.trimIndent()
            )
        )
        return saved
    }

    public fun getAllProduct(): List<Products> {
        return productRepository.findAll()
    }

    public fun getProductById(id: Int): Optional<Products> {
        return productRepository.findById(id)
    }

    public fun deleteProduct(id: Int, deleteQuantity: UInt) {
        val product = productRepository.findById(id)
            .orElseThrow { ProductNotFoundException("Product with id $id not found") }

        if (deleteQuantity.toInt() > product.itemQuantity) {
            productRepository.deleteById(id)
        } else {
            product.itemQuantity -= deleteQuantity.toInt()
            productRepository.save(product)
        }

        // les go send thy email
        rabbitTemplate.convertAndSend(
            "email.exchange",
            "email.send",
            EmailJobDto(
                to = "dnzdyn2012@gmail.com",
                subject = "Product deleted.",
                body = """
                Deleted item: ${product.itemName},
                Quantity removed: $deleteQuantity,
                Remaining quantity: ${product.itemQuantity},
                Category: ${product.itemCategory},
            """.trimIndent(),
            )
        )
    }
}