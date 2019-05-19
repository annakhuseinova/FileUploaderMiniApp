package com.annakhuseinova.FileUploaderApp.services;


import com.annakhuseinova.FileUploaderApp.model.FileInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.net.MalformedURLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class FileService {


    private UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<File> getListOfUserFiles(){

        List<File> userFilesAsList = Arrays.asList(userService.getCurrentAuthenticatedUserDirectory().listFiles()) ;
        return userFilesAsList;
    }

    public List<FileInfo> getListOfUserFilesInfos(Class downloadClass, String downloadMethodName, Class deleteClass, String deleteMethodName){
        List<FileInfo> listOfFilesInfos = getListOfUserFiles().stream().map(path ->{
            String fileName = path.getName();
            String fileSize;
            if ((path.length()/(1024*1024)>= 1)){
                fileSize = (path.length()/(1024*1024))+" Mbs";
            }else if((path.length()/1024>= 1)){
                fileSize = (path.length()/(1024))+" Kbs";
            }else {
                fileSize = (path.length())+" bytes";
            }

            String lastModifiedDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(path.lastModified()),
                    TimeZone.getDefault().toZoneId()).format(dateTimeFormatter);
            String downloadUrl = MvcUriComponentsBuilder.fromMethodName(downloadClass,downloadMethodName,
                    path.getName()).build().toString();
            String deleteUrl = MvcUriComponentsBuilder.fromMethodName(deleteClass,deleteMethodName, path.getName(), "model").build().toString();
            return new FileInfo(fileName,fileSize,lastModifiedDate, downloadUrl, deleteUrl);
        }).collect(Collectors.toList());

        return listOfFilesInfos;
    }

    public Resource loadFile(String filename) {
        try {
            File fileToLoad = new File(uploadPath + File.separator + userService.getCurrentAuthenticatedUserLogin() +
                    File.separator + filename);
            Resource resource = new UrlResource(fileToLoad.toURI());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Resource " + resource.getFilename() + " not found");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean deleteFile(String filename){
        File fileToDelete = new File(uploadPath + File.separator + userService.getCurrentAuthenticatedUserLogin()+
                File.separator+filename);
        return fileToDelete.delete();
    }
}
