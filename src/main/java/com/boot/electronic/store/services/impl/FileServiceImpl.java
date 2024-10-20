package com.boot.electronic.store.services.impl;

import com.boot.electronic.store.exception.BadApiResponse;
import com.boot.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl  implements FileService {
    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {
      String originalFilenamefilename=  file.getOriginalFilename();
  String filename= UUID.randomUUID().toString();
  logger.info("Filename : {}",originalFilenamefilename);
  String extension=originalFilenamefilename.substring((originalFilenamefilename.lastIndexOf(".")));
  String fileNameWithExtension=filename+extension;
  String fullPathWithFileName=path+ File.separator+fileNameWithExtension;

  if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg")){

      File folder=new File(path);
      if(!folder.exists()){
          folder.mkdirs();
      }
      Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
      return fileNameWithExtension;
  }
  else{
      throw  new BadApiResponse("File with this extension"+extension+ "not allowed");
  }



    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath=path+File.separator+name;
        InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;
    }
}
