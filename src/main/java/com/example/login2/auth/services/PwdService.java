package com.example.login2.auth.services;

import com.example.login2.auth.entities.RequestPwdDTO;
import com.example.login2.auth.entities.RestorePwdDTO;
import com.example.login2.notification.services.MailNotificationService;
import com.example.login2.users.entities.User;
import com.example.login2.users.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PwdService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailNotificationService mailNotificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User request (RequestPwdDTO requestPwdDTO) throws Exception
    {
        User user = userRepository.findByEmail(requestPwdDTO.getEmail());
        if(user == null) throw new Exception("User not found");
        user.setPwdResetCode(UUID.randomUUID().toString());
        mailNotificationService.sendPwsResetMail(user);
        return userRepository.save(user);
    }

    public User restore (RestorePwdDTO restorePwdDTO) throws Exception {
        User user = userRepository.findByPwdResetCode(restorePwdDTO.getResetPwdCode());
        if(user == null) throw new Exception("User not found");
        user.setPassword(passwordEncoder.encode(restorePwdDTO.getNewPwd()));
        user.setPwdResetCode(null);

        //activate the user
        user.setActive(true);
        user.setActivationCode(null);

        return userRepository.save(user);
   }

}
