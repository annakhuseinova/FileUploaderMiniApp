package com.annakhuseinova.FileUploaderApp.security;

import com.annakhuseinova.FileUploaderApp.entities.User;
import com.annakhuseinova.FileUploaderApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService  implements UserDetailsService {



    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userRepository.findOneByLogin(s);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        user.setIsActive(1);
        UserPrincipal userPrincipal = new UserPrincipal(user);
        return userPrincipal;
    }
}
