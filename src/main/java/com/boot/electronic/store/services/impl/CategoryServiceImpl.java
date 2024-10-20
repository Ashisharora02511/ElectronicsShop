package com.boot.electronic.store.services.impl;

import com.boot.electronic.store.dtos.CategoryDto;
import com.boot.electronic.store.dtos.PageableResponse;
import com.boot.electronic.store.entities.Category;
import com.boot.electronic.store.entities.User;
import com.boot.electronic.store.exception.BadApiResponse;
import com.boot.electronic.store.exception.ResourceNotFoundException;
import com.boot.electronic.store.helper.Helper;
import com.boot.electronic.store.repositories.CategoryRepository;
import com.boot.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
private CategoryRepository repository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category cat=mapper.map(categoryDto, Category.class);
        Category saved= repository.save(cat);


        return mapper.map(saved,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {

        Category cat= repository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found exception"));
        //upadte category details
       // cat.setCategoryID(categoryId);
        cat.setTitle(categoryDto.getTitle());
        cat.setDescription(categoryDto.getDescription());
        cat.setCoverImage(categoryDto.getCoverImage());
        Category update=repository.save(cat);

        return mapper.map(update,CategoryDto.class);
    }

    @Override
    public void delete(String catgoryId) {

        Category cat= repository.findById(catgoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found exception"));
  repository.delete(cat);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending() );

        Pageable pageable= PageRequest.of(pageNumber,pageSize);

        Page<Category> page= repository.findAll(pageable);
      PageableResponse<CategoryDto> pageableResponse=  Helper.getPageableResponse(page,CategoryDto.class);
        return pageableResponse;
    }

    @Override
    public CategoryDto getSinglecategoryDetail(String categoryId) {
        Category cat= repository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found exception"));

        return mapper.map(cat,CategoryDto.class);
    }

    @Override
    public String uploadCategoryImage(MultipartFile file, String path) throws IOException {

        String orignalFileName = file.getOriginalFilename();
        String randomFileName = UUID.randomUUID().toString();
        String extension = orignalFileName.substring(orignalFileName.lastIndexOf("."));
        String fullFileNameWithExtension = randomFileName + extension;
        String fullnamewithpath = path + File.separator + fullFileNameWithExtension;

        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg")) {

            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(fullnamewithpath));
            return null;
        }
        else{
            throw  new BadApiResponse("File with this extension"+extension+ "not allowed");
        }
    }

        @Override
        public InputStream getCategoryImage (String path, String name) throws FileNotFoundException {
            String fullPath=path+File.separator+name;
            InputStream inputStream=new FileInputStream(fullPath);
            return inputStream;
        }
    }

