package com.example.login2.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.login2.auth.entities.LoginDTO;
import com.example.login2.auth.entities.LoginRTO;
import com.example.login2.users.entities.Role;
import com.example.login2.users.entities.User;
import com.example.login2.users.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class LoginService {

    public static final String SECRET = "secret";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public LoginRTO login (LoginDTO loginDTO)
    {
        if(loginDTO == null) return null;
        User user = userRepository.findByEmail(loginDTO.getEmail());
        if(user == null || !user.isActive()) return null;
        boolean canLogin = canUserLogin(user,loginDTO.getPassword());
        if(!canLogin) return null;

        String jwt = generateJwt(user);
        user.setJwtCreatedOn(LocalDateTime.now());

        userRepository.save(user);
        user.setPassword(null);

        LoginRTO loginRTO = new LoginRTO();
        loginRTO.setUser(user);
        loginRTO.setJwt(jwt);
        return  loginRTO;
    }

    public boolean canUserLogin (User user, String pwd){
        return passwordEncoder.matches(pwd, user.getPassword());
    }

    public static String generateJwt(User user)
    {
        Date expireDate = Date.from(
                LocalDateTime.now().plusDays(15)
                .atZone(ZoneId.systemDefault())
                        .toInstant());

        return JWT.create()
                .withIssuer("Giovanni")
                .withIssuedAt(new Date())
                .withExpiresAt(expireDate)
                .withClaim("roles", String.join(",",user.getRoles().stream().map(Role::getName).toArray(String[]::new)))
                .withClaim("id",user.getId())
                .withClaim("email",user.getEmail())
                .sign(Algorithm.HMAC512(SECRET));
    }




}
