package com.boot.electronic.store.controller;

import com.boot.electronic.store.dtos.ApiResponse;
import com.boot.electronic.store.dtos.ImageResponse;
import com.boot.electronic.store.dtos.PageableResponse;
import com.boot.electronic.store.dtos.ProductDto;
import com.boot.electronic.store.services.FileService;
import com.boot.electronic.store.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/product")
public class ProductController {


    @Autowired
    ProductService productService;
    @Autowired
    private FileService service;
    @Value("{product.image.path}")
    private String imagePath;
    //create
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
     ProductDto productDto1=productService.create(productDto);
     return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> cupdateProduct(@RequestBody ProductDto productDto,@PathVariable String productId){
        ProductDto productDto1=productService.update(productId,productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.OK);
    }


    @DeleteMapping("/{productId}")
    public  ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId){
     productService.delete(productId);
    ApiResponse response= ApiResponse.builder().message("Product is delete successfully!!!").status(HttpStatus.OK).success(true).build();
    return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping ("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId){
        ProductDto productDto1=productService.getSingleProduct(productId);
        return new ResponseEntity<>(productDto1, HttpStatus.OK);
    }
@GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllList(
            @RequestParam(value="pageNumber",defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title") String sortBy,
            @RequestParam(value="sortOrder",defaultValue = "asc")String sortOrder


    ){
       PageableResponse<ProductDto> res= productService.getAlProduct(pageNumber,pageSize,sortBy,sortOrder);
    return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value="pageNumber",defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title") String sortBy,
            @RequestParam(value="sortOrder",defaultValue = "asc")String sortOrder


    ){
        PageableResponse<ProductDto> res= productService.getAllLiveProduct(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> getSearch(
            @PathVariable String query,
            @RequestParam(value="pageNumber",defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title") String sortBy,
            @RequestParam(value="sortOrder",defaultValue = "asc")String sortOrder


    ){
        PageableResponse<ProductDto> res= productService.searchByTitle(query,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    //upload image
    @PostMapping("/productimage/{ProductId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable String ProductId,
            @RequestParam("productImage")MultipartFile image
            ) throws IOException {
     String uploadfilename=service.uploadImage(image,imagePath);
     ProductDto productdto=productService.getSingleProduct(ProductId);
     productdto.setProductImage(uploadfilename);
    ProductDto updatedProduct= productService.update(ProductId,productdto);
    ImageResponse response=ImageResponse.builder().imagename(productdto.getProductImage()).message("Product image succesfully loaded").status(HttpStatus.CREATED).build();
    return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    //serve image
@GetMapping("/productimage/{productid}")
    public void serveProductImage(@PathVariable String productid, HttpServletResponse response) throws IOException {
        ProductDto productDto=productService.getSingleProduct(productid);
        InputStream ressoure=service.getResource(imagePath,productDto.getProductImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(ressoure,response.getOutputStream());

    }
}
