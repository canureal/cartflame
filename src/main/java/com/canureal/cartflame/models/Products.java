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
    private Integer itemId;

    @Column(nullable = false)
    private Integer itemQuantity;

    @Column(nullable = false)
    private String itemName;

    // tag itemCategory in the controller .toUpperCase all time.
    @Column(nullable = false)
    private String itemCategory = "GENERAL";
}
