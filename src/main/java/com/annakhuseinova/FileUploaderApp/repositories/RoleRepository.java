package com.annakhuseinova.FileUploaderApp.repositories;

import com.annakhuseinova.FileUploaderApp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findOneByName(String name);
}
