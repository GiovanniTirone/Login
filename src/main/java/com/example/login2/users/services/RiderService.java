package com.example.login2.users.services;

import com.example.login2.users.entities.User;
import com.example.login2.users.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RiderService {

    @Autowired
    private UserRepository userRepository;

    public User pickRider () {
        Optional<User> riderOpt = userRepository.pickRider();
        if(riderOpt.isPresent())
            return riderOpt.get();
        //if all riders are busy take the first
        return userRepository.findAll(PageRequest.of(0,1)).toList().get(0);
    }


}
