package com.example.login2.auth.services;

import com.example.login2.auth.entities.SignUpActivationDTO;
import com.example.login2.auth.entities.SignUpDTO;
import com.example.login2.notification.services.MailNotificationService;
import com.example.login2.users.entities.Role;
import com.example.login2.users.entities.User;
import com.example.login2.users.repos.RoleRepository;
import com.example.login2.users.repos.UserRepo;
import com.example.login2.users.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
public class SignUpService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailNotificationService mailNotificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public User signUp (SignUpDTO signUpDTO) throws Exception {
        User userInDb = userRepo.findByEmail(signUpDTO.getEmail());
        if (userInDb != null) throw new Exception("User already exists!");
        User user = new User();
        user.setName(signUpDTO.getName());
        user.setSurname(signUpDTO.getSurname());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setActive(false); //potrebbero iscriversi utenti con una mail fasulla
        user.setActivationCode(UUID.randomUUID().toString());

        Set<Role> roles = new HashSet<>();
        Optional<Role> roleOpt = roleRepository.findByName(Roles.REGISTERED);
        if(roleOpt.isEmpty()) throw new Exception("cannot set user role");
        roles.add(roleOpt.get());
        user.setRoles(roles);
        mailNotificationService.sendActivationMail(user);

        return userRepo.save(user);
    }

    public User activate (SignUpActivationDTO signUpActivationDTO) throws Exception {
        User user = userRepo.findByActivationCode(signUpActivationDTO.getActivationCode());
        if(user == null) throw new Exception("User not found");
        user.setActive(true);
        user.setActivationCode(null);
        return userRepo.save(user);
    }

}
