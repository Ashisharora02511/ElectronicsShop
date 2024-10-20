package com.boot.electronic.store.services;

import com.boot.electronic.store.dtos.PageableResponse;
import com.boot.electronic.store.dtos.ProductDto;
import com.boot.electronic.store.entities.Product;

import java.util.List;

public interface ProductService {

    //create
    ProductDto create(ProductDto product);

    // update
    ProductDto update(String productId,ProductDto product);

    //delete
    void delete(String productID);

    //getSingle

    ProductDto getSingleProduct(String productID);
// get All
   PageableResponse<ProductDto>getAlProduct(int pageNumber,int pageSize,String sortBy,String sortDir);
    //getAll:Live
    PageableResponse<ProductDto> getAllLiveProduct(int pageNumber,int pageSize,String sortBy,String sortDir);
//search product
PageableResponse<ProductDto>searchByTitle(String title,int pageNumber,int pageSize,String sortBy,String sortDir);
    //other methods
// create a product with category id
    ProductDto createWithCategory(ProductDto dto,String categoryId);
 //update category of product
}
