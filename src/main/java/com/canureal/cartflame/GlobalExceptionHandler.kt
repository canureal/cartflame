package com.canureal.cartflame

import com.canureal.cartflame.exceptions.ProductNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@RestController
open class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException::class)
    open fun handleProductNotFoundException(ex: ProductNotFoundException): ResponseEntity<Map<String, String>> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(mapOf("error" to (ex.message ?: "product not found")))
    }
    /// when adding new custom exception handler
    /// take the existing one as reference.
}
