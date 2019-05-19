package com.annakhuseinova.FileUploaderApp.repositories;

import com.annakhuseinova.FileUploaderApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findOneByLogin(String userLogin);
    String findEmailByLogin(String login);
}
