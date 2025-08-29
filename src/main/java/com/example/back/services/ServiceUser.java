package com.example.back.services;

import com.example.back.dto.AuthDto;
import com.example.back.entity.Menu;
import com.example.back.entity.Profile;
import com.example.back.entity.User;
import com.example.back.jwt.JwtService;
import com.example.back.reposetory.RepoProfile;
import com.example.back.reposetory.RepoUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceUser {
    private final RepoUser repoUser;
    private final MenuService menuService;
    private final RepoProfile repoProfile;

    public Optional<User> findUserByUsername(String username) {
        return repoUser.findByUsername(username);
    }

    public void addMenutoUser(List<String> menus , String username) {
        User user=repoUser.getUserByUsername(username);
        List<Menu> menusList = user.getProfile().getMenus();
        for (String menu : menus) {
            Menu m =menuService.getMenuByName(menu);
            boolean a=true;
            for(Menu i : menusList) {
                if(Objects.equals(i.getName(), m.getName())){
                    a=false;
                    break;
                }
            }
            if(a){
                menusList.add(m);
            }
        }

        Profile profile=user.getProfile();
        profile.setMenus(menusList);
        repoProfile.save(profile);
        user.setProfile(profile);
        repoUser.save(user);


    }
    public List<String> getMenusByUsername(String username) {
        return repoUser.findByUsername(username).get().getProfile().getMenus().stream().map(Menu::getName).collect(Collectors.toList());
    }


}
