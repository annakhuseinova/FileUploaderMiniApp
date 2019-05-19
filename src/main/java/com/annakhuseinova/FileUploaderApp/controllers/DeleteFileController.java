package com.annakhuseinova.FileUploaderApp.controllers;

import com.annakhuseinova.FileUploaderApp.services.FileService;
import com.annakhuseinova.FileUploaderApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DeleteFileController {

    private FileService fileService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/deletefiles/{filename}")
    public String deleteUserFile(@PathVariable("filename") String filename, Model model){

        fileService.deleteFile(filename);
        model.addAttribute("listOfFilesInfos", fileService.getListOfUserFilesInfos(DownloadFileController.class, "downloadFile",
                DeleteFileController.class, "deleteUserFile"));
        model.addAttribute("currentUserLogin", userService.getCurrentAuthenticatedUserLogin());
        return "redirect:/main";
    }
}
