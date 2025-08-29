package com.example.back.services;

import com.example.back.dto.AuthDto;
import com.example.back.entity.Profile;
import com.example.back.entity.User;
import com.example.back.jwt.JwtService;
import com.example.back.reposetory.RepoUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AuthService {
    private final RepoUser repoUser;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String login(AuthDto authDto) {

        Optional<User> res= repoUser.findUserByUsername(authDto.username());
        System.out.println(authDto.username());
        if(res.isEmpty()){

            return "no user found";
        }else {
            boolean res1= passwordEncoder.matches(authDto.password(), res.get().getPassword());
            System.out.println(res1);
            System.out.println(authDto.password());
            if(res1){
                return jwtService.generateToken(res.get());

            }else {
                return "wrong password";
            }

        }



    }
    public void register(AuthDto authDto) {
        LocalDateTime timestamp= LocalDateTime.now();
        Profile profile= authDto.profile();
        User user =User.builder()
                .username(authDto.username())
                .password(passwordEncoder.encode(authDto.password()))
                .profile(profile)
                .created_at(timestamp)
                .build();
        repoUser.save(user);


    }
}
