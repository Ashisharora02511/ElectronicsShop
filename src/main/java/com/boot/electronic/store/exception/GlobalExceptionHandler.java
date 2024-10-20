package com.boot.electronic.store.exception;

import com.boot.electronic.store.dtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // handle resource not found exception

 @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException rnfe){
 ApiResponse build=ApiResponse.builder().message(rnfe.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();

 return new ResponseEntity(build,HttpStatus.NOT_FOUND);
    }
/// MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>>  resourceNotFoundException(MethodArgumentNotValidException ex)
    {
           List<ObjectError> allError=ex.getBindingResult().getAllErrors();
           Map<String,Object> response=new HashMap<>();
           allError.stream().forEach(objectError->{
              String message= objectError.getDefaultMessage();
               String field=((FieldError) objectError).getField();
               response.put(field,message);
           });
           return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadApiResponse.class)
    public ResponseEntity<ApiResponse>  handleBadApiRequest(BadApiResponse ex)
    {
        ApiResponse build=ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
        return new ResponseEntity<>(build,HttpStatus.BAD_REQUEST);
    }

    }

