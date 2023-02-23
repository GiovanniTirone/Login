package com.example.login2.users.repos;


import com.example.login2.users.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

     Optional<Role> findByName (String name);

}
