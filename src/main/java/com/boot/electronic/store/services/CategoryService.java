package com.boot.electronic.store.services;

import com.boot.electronic.store.dtos.CategoryDto;
import com.boot.electronic.store.dtos.PageableResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface CategoryService {
    //create
CategoryDto create(CategoryDto categoryDto);
    //update
CategoryDto update(CategoryDto categoryDto,String category);

    //delete
    void delete(String catgoryId);

    //get all
    //get all
    PageableResponse<CategoryDto> getAllCategory(int pageNumber,int pageSize,String shortBy,String sort);

    //get single category detail
CategoryDto getSinglecategoryDetail(String categoryID);
    //search

//updalod categoryimage
    public String uploadCategoryImage(MultipartFile file,String path) throws IOException;

InputStream getCategoryImage(String path,String id) throws FileNotFoundException;


}
