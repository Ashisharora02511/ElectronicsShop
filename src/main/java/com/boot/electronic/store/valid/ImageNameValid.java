package com.boot.electronic.store.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidation.class)
public @interface ImageNameValid {

    String message() default "{Please Enter Valid Data}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
