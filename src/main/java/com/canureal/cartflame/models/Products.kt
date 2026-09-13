package com.canureal.cartflame.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "products")
data class Products(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var itemId: Int? = null,

    @Column(nullable = false)
    var itemQuantity: Int = 0,

    @Column(nullable = false)
    var itemName: String = "",

    @Column(nullable = false)
    var itemCategory: String = "GENERAL",
)
