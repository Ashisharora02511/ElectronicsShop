package com.boot.electronic.store.controller;

import com.boot.electronic.store.dtos.*;
import com.boot.electronic.store.entities.Product;
import com.boot.electronic.store.services.CategoryService;
import com.boot.electronic.store.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @Value("${category.image.path}")
    private String categoryImagePath;
    //create
    @PostMapping(value = "/createcategory")
    public ResponseEntity<CategoryDto> createCategory( @Valid @RequestBody CategoryDto category){

        CategoryDto dto= categoryService.create(category);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }



    //update
    @PutMapping ("/updatecategory/{CategoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String CategoryId,@Valid @RequestBody CategoryDto category){

        CategoryDto dto= categoryService.update(category,CategoryId);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //get all

    //delete
    @DeleteMapping("/deletecategory/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(String categoryId){
        categoryService.delete(categoryId);
       ApiResponse response= ApiResponse.builder().success(true).message("Category is delete Successfully !!").status(HttpStatus.OK).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/getallcategory")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value="pageNumber",defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title") String sortBy,
            @RequestParam(value="sortOrder",defaultValue = "asc")String sortOrder){
        PageableResponse<CategoryDto> response=categoryService.getAllCategory(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }
    @GetMapping("/getcategorybyid/{CategoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String CategoryId){
       CategoryDto dto= categoryService.getSinglecategoryDetail(CategoryId);
       return new ResponseEntity<>(dto,HttpStatus.OK);
    }


    public ResponseEntity<ImageResponse> uploadCategoryImage(@RequestParam("categoryImage") MultipartFile categoryImage,@PathVariable String categoryid) throws IOException {

       String imagename= categoryService.uploadCategoryImage(categoryImage,categoryImagePath);


        ImageResponse imageResponse= ImageResponse.builder().imagename(imagename).success(true).status(HttpStatus.CREATED).build();
        CategoryDto dto= categoryService.getSinglecategoryDetail(categoryid);
        dto.setCoverImage(imagename);
        categoryService.update(dto,categoryid);
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }


    //serve category image
    public void getCategoryImage(@PathVariable String categoryId, HttpServletResponse httpResponse) throws IOException {

       CategoryDto category=categoryService.getSinglecategoryDetail(categoryId);
        InputStream image= categoryService.getCategoryImage(categoryImagePath,category.getCoverImage());
        httpResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(image,httpResponse.getOutputStream());

    }

    //create product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
              @RequestBody ProductDto dto){

     ProductDto productWithCategory=  productService.createWithCategory(dto,categoryId);
  return new ResponseEntity<>(productWithCategory,HttpStatus.CREATED);
    }
}
