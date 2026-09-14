package com.canureal.cartflame.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class ProductDto {
    public data class AddNewProductDto(
        // is 20k products a lot? i think so maybe i change it
        @NotBlank @Size(max = 20_000) val itemQuantity: Int,
        @NotBlank val itemName: String,
        @NotBlank val itemCategory: String,
    )

    public data class GetProductDto(
        val itemName: String,
        val itemCategory: String,
        val itemQuantity: Int,
        val itemId: String,
    )
}
