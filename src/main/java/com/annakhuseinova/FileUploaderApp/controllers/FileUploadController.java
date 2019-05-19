package com.annakhuseinova.FileUploaderApp.controllers;


import com.annakhuseinova.FileUploaderApp.services.FileService;
import com.annakhuseinova.FileUploaderApp.services.MailService;
import com.annakhuseinova.FileUploaderApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class FileUploadController {

    private UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    private FileService fileService;
    private MailService mailService;

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/main")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model){
        if (file.isEmpty()) {
            model.addAttribute("nullFileError", "You've sent no file");
        }else {
            try {

                file.transferTo(new File(uploadPath+File.separator+userService.getCurrentAuthenticatedUserLogin()+
                        File.separator+file.getOriginalFilename()));
                mailService.sendMailMessage("You've successfully uploaded file "+file.getOriginalFilename(),
                        "FileUploaderApp Notification", "annakhuseinova@yandex.ru");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("listOfFilesInfos", fileService.getListOfUserFilesInfos(DownloadFileController.class, "downloadFile",
                DeleteFileController.class, "deleteUserFile"));
        model.addAttribute("currentUserLogin", userService.getCurrentAuthenticatedUserLogin());
        return "mainpage";
    }
}
