package com.boot.electronic.store.services;

import com.boot.electronic.store.dtos.PageableResponse;
import com.boot.electronic.store.dtos.UserDto;

import java.util.List;

public interface UserServices {

    //create user
     UserDto createUser(UserDto userDto);
    //update user
     UserDto updateUser(UserDto user,String userId);

    //delete user
      void deleteUser(String userId);
    // get all user
 PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);
    //get user by id
     UserDto getUserById(String userId);
    //get user by email
     UserDto getUserByEmail(String userEmail);
    //search user
  List<UserDto> searchUser(String keyword);

  List<UserDto> getAllUser();

}
