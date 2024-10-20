package com.boot.electronic.store.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageNameValidation implements ConstraintValidator<ImageNameValid,String>{



    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

if(s.isBlank()){
    return false;
}
else {
    return true;
}


    }
}