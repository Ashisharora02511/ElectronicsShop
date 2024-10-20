package com.boot.electronic.store.services.impl;

import com.boot.electronic.store.dtos.PageableResponse;
import com.boot.electronic.store.dtos.ProductDto;
import com.boot.electronic.store.entities.Category;
import com.boot.electronic.store.entities.Product;
import com.boot.electronic.store.exception.ResourceNotFoundException;
import com.boot.electronic.store.helper.Helper;
import com.boot.electronic.store.repositories.CategoryRepository;
import com.boot.electronic.store.repositories.ProductRepositories;
import com.boot.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepositories productRepositories;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    ModelMapper mapper;
    @Override
    public ProductDto create(ProductDto product) {
      String userId=  UUID.randomUUID().toString();
      product.setProductId(userId);
      product.setAddedDate(new Date());
        Product productEntity=mapper.map(product, Product.class);
       Product product1= productRepositories.save(productEntity);
       ProductDto dto=mapper.map(product1,ProductDto.class);

        return dto;
    }

    @Override
    public ProductDto update(String productId, ProductDto product) {
        //fetch product by id
      Product fetchProduct = productRepositories.findById(productId).orElseThrow(()->new ResourceNotFoundException("There is no data aviable"));

      fetchProduct.setTitle(product.getTitle());
      fetchProduct.setDescription(product.getDescription());
      fetchProduct.setLive(product.isLive());
      fetchProduct.setPrice(product.getPrice());
     fetchProduct.setQuantity(product.getQuantity());
     fetchProduct.setStock(product.isStock());
     fetchProduct.setDiscountPrice(product.getDiscountPrice());
     fetchProduct.setProductImage(product.getProductImage());

     Product updsateProduct=productRepositories.save(fetchProduct);


      return mapper.map(updsateProduct,ProductDto.class);


    }

    @Override
    public void delete(String productID) {
        Product fetchProduct = productRepositories.findById(productID).orElseThrow(()->new ResourceNotFoundException("There is no data aviable"));
        productRepositories.delete(fetchProduct);


    }

    @Override
    public ProductDto getSingleProduct(String productID) {
        Product fetchProduct = productRepositories.findById(productID).orElseThrow(()->new ResourceNotFoundException("There is no data aviable"));

        return mapper.map(fetchProduct,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAlProduct(int pageNumber,int pageSize,String sortBy,String sortDir) {
   Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy)).descending() :(Sort.by(sortBy)).ascending();
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> page= productRepositories.findAll(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProduct(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy)).descending() :(Sort.by(sortBy)).ascending();
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> page= productRepositories.findByLiveTrue(pageable);



        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String title,int pageNumber,int pageSize,String sortBy,String sortDir) {


        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy)).descending() :(Sort.by(sortBy)).ascending();
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> page= productRepositories.findByTitleContaining(title,pageable);



        return Helper.getPageableResponse(page,ProductDto.class);


    }

    @Override
    public ProductDto createWithCategory(ProductDto dto, String categoryId) {
       //fetch the category
       Category  category= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("There is no category is avilable"));
        Product productEntity=mapper.map(dto, Product.class);
        String userId=  UUID.randomUUID().toString();
        productEntity.setProductId(userId);
        productEntity.setAddedDate(new Date());
        productEntity.setCategory(category);
        Product product1= productRepositories.save(productEntity);



        return  mapper.map(product1,ProductDto.class);
    }
}
