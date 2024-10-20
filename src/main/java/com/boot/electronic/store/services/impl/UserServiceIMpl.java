package com.boot.electronic.store.services.impl;

import com.boot.electronic.store.dtos.PageableResponse;
import com.boot.electronic.store.dtos.UserDto;
import com.boot.electronic.store.entities.User;
import com.boot.electronic.store.exception.ResourceNotFoundException;
import com.boot.electronic.store.helper.Helper;
import com.boot.electronic.store.repositories.UserRepository;
import com.boot.electronic.store.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

//import javax.print.attribute.standard.PageRanges;

import java.io.File;
import java.io.IOException;
import java.lang.module.ResolutionException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceIMpl implements UserServices {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private  String imagePath;

    @Override
    public UserDto createUser(UserDto userDto) {
        //dto->entity
        //get random user id
      String userId=  UUID.randomUUID().toString();
      userDto.setUserId(userId);
      User user=  dtoToEntity(userDto);
       User  saveUser= userRepository.save(user);
       //entity ->to dto object
       UserDto userDtoSaved= entityToDto(saveUser);

        return userDtoSaved;
    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        User foundUser=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException(" the user is not found"));
        foundUser.setName(userDto.getName());
        foundUser.setEmail(userDto.getEmail());
        foundUser.setPassword(userDto.getPassword());
        foundUser.setGender(userDto.getGender());
        foundUser.setAbout(userDto.getAbout());
        foundUser.setImagename(userDto.getImagename());
        User updateUser=userRepository.save(foundUser);
        //user to dto convert
        UserDto UpdateduserDto=entityToDto(updateUser);

        return UpdateduserDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("no user is found"));
// delete user iamge file

        String fullPath=imagePath+user.getImagename();
        try {
            Path path= Paths.get(fullPath);

            Files.delete(path);
        }
        catch (NoSuchFileException ex){
            ex.printStackTrace();
        }
         catch(IOException exception){
             exception.printStackTrace();
         }

userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending() );
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);

       Page<User> page= userRepository.findAll(pageable);

      PageableResponse<UserDto> response = Helper.getPageableResponse(page,UserDto.class);





        return response;
    }

    @Override
    public UserDto getUserById(String userId) {
      User user=  userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("no user is foynd"));
      UserDto userDto=entityToDto(user);
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String userEmail) {
        Optional<User> user=userRepository.findByEmail(userEmail);
        UserDto userDto=entityToDto(user.orElseThrow(()->new ResourceNotFoundException("User is not found")));
        return userDto;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
List<User> users=userRepository.findByNameContaining(keyword);
        List<UserDto> allUserDto=users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());

        return allUserDto;
    }

    @Override
    public List<UserDto> getAllUser() {

       List<User> aluser= userRepository.findAll();
     List<UserDto> alluserData=  aluser.stream().map(user->entityToDto(user)).collect(Collectors.toList());

        return alluserData;
    }

    private UserDto entityToDto(User saveUser) {
      /*  UserDto userDto=UserDto.builder()
                .userId(saveUser.getUserId())
                .name(saveUser.getName())
                .email(saveUser.getEmail())
                .password(saveUser.getPassword())
                .about(saveUser.getAbout())
                .gender(saveUser.getGender())
                .imagename(saveUser.getImagename())
                .build();*/
        return  mapper.map(saveUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
        /*User user= User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .about(userDto.getAbout())
                .gender(userDto.getGender())
                .imagename(userDto.getImagename()).build();*/
        return mapper.map(userDto,User.class);

    }
}
