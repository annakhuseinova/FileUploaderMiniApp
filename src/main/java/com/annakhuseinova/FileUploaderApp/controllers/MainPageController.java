package com.annakhuseinova.FileUploaderApp.controllers;

import com.annakhuseinova.FileUploaderApp.services.FileService;
import com.annakhuseinova.FileUploaderApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainPageController {

    private UserService userService;

    private FileService fileService;

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/main")
    public String showMainPage(Model model){

        model.addAttribute("listOfFilesInfos", fileService.getListOfUserFilesInfos(DownloadFileController.class, "downloadFile",
                DeleteFileController.class, "deleteUserFile"));
        model.addAttribute("currentUserLogin", userService.getCurrentAuthenticatedUserLogin());
        return "mainpage";
    }


}
