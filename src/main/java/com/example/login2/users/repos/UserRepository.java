package com.example.login2.users.repos;



import com.example.login2.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail (String email);

    User findByActivationCode(String activationCode);

    User findByPwdResetCode(String resetPwdCode);


}
