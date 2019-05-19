package com.annakhuseinova.FileUploaderApp.services;


import com.annakhuseinova.FileUploaderApp.Utils.UserAlreadyExistsException;
import com.annakhuseinova.FileUploaderApp.entities.SystemUser;
import com.annakhuseinova.FileUploaderApp.entities.User;
import com.annakhuseinova.FileUploaderApp.repositories.RoleRepository;
import com.annakhuseinova.FileUploaderApp.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Arrays;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger("UserServiceLogger");
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Authentication auth;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User findUserByLogin(String userLogin){
        return userRepository.findOneByLogin(userLogin);
    }

    @Transactional
    public boolean saveUser(SystemUser systemUser) throws UserAlreadyExistsException {

        User user = new User();

        if (findUserByLogin(systemUser.getLogin()) != null){

            throw new UserAlreadyExistsException("user already exists");

        }

        user.setLogin(systemUser.getLogin());
        user.setFirstName(systemUser.getFirstName());
        user.setLastName(systemUser.getLastName());
        user.setEmail(systemUser.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(systemUser.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findOneByName("ROLE_USER")));
        user.setIsActive(1);
        userRepository.save(user);
        createDirectoryForNewUser(user);
        logger.info("Successfully created user directory");
        return true;
    }

    public boolean createDirectoryForNewUser(User user){
        File file = new File("usersdirectories"+ File.separator+user.getLogin());
        if (file.exists()){
            return false;
        }else {
            file.mkdir();
            return true;
        }
    }

    public String getCurrentAuthenticatedUserLogin(){
        auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public File getCurrentAuthenticatedUserDirectory(){
        File file = new File(uploadPath + File.separator + getCurrentAuthenticatedUserLogin());
        return file;
    }
}
