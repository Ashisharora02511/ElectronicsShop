package com.boot.electronic.store.repositories;

import com.boot.electronic.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepositories extends JpaRepository<Product,String> {
    Page<Product>  findByTitleContaining(String title,Pageable pageable);
    Page<Product> findByLiveTrue(Pageable pageable);
}
