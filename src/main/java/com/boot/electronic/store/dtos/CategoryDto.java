package com.boot.electronic.store.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {



       private String categoryID;
        @NotBlank(message = "Title is required !!")
        @Size(min = 4,max = 30,message = "title must be of minimum 5 characters !!")
       // @Max(value = 30)
       private String title;
      @NotBlank
       private  String description;
       @NotBlank
        String coverImage;




}
