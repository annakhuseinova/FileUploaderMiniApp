package com.annakhuseinova.FileUploaderApp.controllers;
import com.annakhuseinova.FileUploaderApp.entities.SystemUser;
import com.annakhuseinova.FileUploaderApp.entities.User;
import com.annakhuseinova.FileUploaderApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationPage(Model model){
        model.addAttribute("systemUser", new SystemUser());
        return "registration";
    }

    @PostMapping
    public String addUser(@Valid @ModelAttribute(name = "systemUser") SystemUser systemUser, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            return "registration";
        }
        User trialUser  = userService.findUserByLogin(systemUser.getLogin());

        if (trialUser != null){
            model.addAttribute("systemUser", systemUser);
            model.addAttribute("UserCreationError"," User already exists");
            return "registration";
        }
        try {
            userService.saveUser(systemUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
