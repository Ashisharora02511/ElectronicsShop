package com.boot.electronic.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Product {


    @Id
    private String productId;
    @Column(length = 10000)
    private String description;
    private String title;
    private int price;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private int discountPrice;
    private String productImage;
    @ManyToOne
    private  Category category;

}
