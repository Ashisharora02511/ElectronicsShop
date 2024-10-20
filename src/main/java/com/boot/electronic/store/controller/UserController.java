package com.boot.electronic.store.controller;

import com.boot.electronic.store.dtos.ApiResponse;
import com.boot.electronic.store.dtos.ImageResponse;
import com.boot.electronic.store.dtos.PageableResponse;
import com.boot.electronic.store.dtos.UserDto;
import com.boot.electronic.store.services.FileService;
import com.boot.electronic.store.services.UserServices;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//import javax.swing.text.html.parser.Entity;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
//import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserServices userServices;
    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;
    //create
   @PostMapping
    public ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto userDto){
         UserDto userDto1=userServices.createUser(userDto);
         return new ResponseEntity<>(userDto1, HttpStatus.CREATED);

    }
    //update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser( @PathVariable("userId") String UserID,
                                               @Valid @RequestBody UserDto userDto){

      UserDto updatedUser= userServices.updateUser(userDto,UserID);



return  new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }
    //Delete User
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(  @PathVariable("userId") String userID){
       userServices.deleteUser(userID);
       ApiResponse apiResponse=ApiResponse.builder()
               .message("\"user is delete sucessfully !!\"")
               .success(true)
               .status(HttpStatus.OK)

               .build();

     return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    //get all user
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam (value="pageNumber",defaultValue = "0",required = false) int pageNumber,
    @RequestParam(value="pageSize",defaultValue = "2",required = false) int pageSize,
            @RequestParam (value="sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "ASC",required = false) String sortDir){
       return new ResponseEntity<>(userServices.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
    // get single user
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") String userId){
       return new ResponseEntity<>(userServices.getUserById(userId),HttpStatus.OK);
    }
    @GetMapping("/email/{userEmail}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("userEmail") String userEmail){
        return new ResponseEntity<>(userServices.getUserByEmail(userEmail),HttpStatus.OK);
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable("keyword") String keywords){
        return new ResponseEntity<>(userServices.searchUser(keywords),HttpStatus.OK);
    }

//upload user image
    @PostMapping("/image/{userId}")
public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile userImage,@PathVariable String userId) throws IOException {

 String imagename=fileService.uploadImage(userImage,imageUploadPath);
 ImageResponse imageResponse=ImageResponse.builder().imagename(imagename).success(true).status(HttpStatus.CREATED).build();
 UserDto user=userServices.getUserById(userId);
 user.setImagename(imagename);
 userServices.updateUser(user,userId);
return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
}

    //serve user image
    @GetMapping("/image/{userId}")
public void  serverUserImage(@PathVariable String userId, HttpServletResponse httpResponse) throws IOException {
       UserDto user=userServices.getUserById(userId);
        InputStream response=fileService.getResource(imageUploadPath,user.getImagename());
        httpResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(response,httpResponse.getOutputStream());
}
}
