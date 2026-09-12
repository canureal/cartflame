package com.canureal.cartflame.dtos

class ProductDto {
    public data class AddNewProductDto(
           val itemQuantity: Int,
           val itemName: String,
           val itemCategory: String,
    )

    public data class DeleteProductDto(
        val itemName: String,
        val itemQuantity: Int,
    )

    public data class GetProductDto(
        val itemName: String,
        val itemCategory: String,
        val itemQuantity: Int,
        val itemId: String,
    )
}