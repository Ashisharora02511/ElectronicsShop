package com.boot.electronic.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {

@Id
@Column(name="id")
    private String categoryID;
@Column(name="category_title",length = 60,nullable = false)
    private String title;
    @Column(name="category_desc",length = 60)

    private String description;
    private String coverImage;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "category")
    List<Product> productList;





}
