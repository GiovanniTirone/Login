package com.example.login2.users.repos;



import com.example.login2.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail (String email);

    User findByActivationCode(String activationCode);

    User findByPwdResetCode(String resetPwdCode);

    @Query(""
            + "select * from ( "
            + "select u.*, count(busyOrders.id) as numberOfOrders "
            + "from `user` as u "
            + "left join users_roles as ur in ur.user_id = u.id "
            + "left join (select * from `orders` where `status` in(4) ) as busyOrders on busyOrders.rider_id = u.id "
            + "where ur.role_id = 2 u.is_active = 1 "
            + "group by u.id "
            + ")as allRiders "
            + "where allRiders.numberOfOrders =0"
            + "limit 1"
    )
    Optional<User> pickRider();
}
