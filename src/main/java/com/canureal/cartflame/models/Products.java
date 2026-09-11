package com.canureal.cartflame.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Table(name = "products")
@Entity
@Getter @Setter
public class Products implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;

    @Column(nullable = false)
    private Integer numberOfItemsInStock;

    @Column(nullable = false)
    private String itemName;
}
