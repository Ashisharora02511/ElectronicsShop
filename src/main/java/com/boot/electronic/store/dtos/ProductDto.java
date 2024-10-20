package com.boot.electronic.store.dtos;

import com.boot.electronic.store.entities.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private String productId;

    private String description;
    private String title;
    private int price;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private int discountPrice;
    private String productImage;
    private CategoryDto category;
}
