package com.example.login2.auth.services;

import com.example.login2.auth.entities.SignUpActivationDTO;
import com.example.login2.auth.entities.SignUpDTO;
import com.example.login2.notification.services.MailNotificationService;
import com.example.login2.users.entities.Role;
import com.example.login2.users.entities.User;
import com.example.login2.users.repos.RoleRepository;
import com.example.login2.users.repos.UserRepository;
import com.example.login2.users.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class SignUpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailNotificationService mailNotificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public User signUp (SignUpDTO signUpDTO,String role) throws Exception {
        User userInDb = userRepository.findByEmail(signUpDTO.getEmail());
        if (userInDb != null) throw new Exception("User already exists!");
        User user = new User();
        user.setName(signUpDTO.getName());
        user.setSurname(signUpDTO.getSurname());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setActive(false); //potrebbero iscriversi utenti con una mail fasulla
        user.setActivationCode(UUID.randomUUID().toString());

        setRole(Roles.REGISTERED,user);
        if(role != null) setRole(role,user);

        mailNotificationService.sendActivationMail(user);
        return userRepository.save(user);
    }

    public User signUp (SignUpDTO signUpDTO) throws Exception {
        return signUp(signUpDTO,null);
    }

    private void setRole (String role,User user) throws Exception {
        Optional<Role> roleOpt = roleRepository.findByName(role.toUpperCase());
        if(roleOpt.isEmpty()) throw new Exception("cannot set user role");
        user.getRoles().add(roleOpt.get());
    }

    public User activate (SignUpActivationDTO signUpActivationDTO) throws Exception {
        User user = userRepository.findByActivationCode(signUpActivationDTO.getActivationCode());
        if(user == null) throw new Exception("User not found");
        user.setActive(true);
        user.setActivationCode(null);
        return userRepository.save(user);
    }

}
