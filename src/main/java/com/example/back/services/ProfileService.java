package com.example.back.services;

import com.example.back.dto.ProfileDto;
import com.example.back.entity.Menu;
import com.example.back.entity.Profile;
import com.example.back.entity.User;
import com.example.back.reposetory.RepoProfile;
import com.example.back.reposetory.RepoUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    public final RepoProfile repoProfile;
    public final MenuService menuService;
    public final RepoUser repoUser;

    public void createProfile(ProfileDto profileDto) {
        Profile profile = new Profile();
        List<Menu> menus= new ArrayList<>();
        for(String s : profileDto.menus()){
            Menu menu = menuService.getMenuByName(s);
            menus.add(menu);


        }
        profile.setMenus(menus);
        profile.setName(profileDto.name());
        repoProfile.save(profile);

    }
    public void addMenu(List<String> menus,int profileId) {
        Profile profile = repoProfile.findById(profileId).get();
        List<Menu> menus1 = profile.getMenus();
        for(String s : menus){
            boolean a = true;
            for(Menu menu : menus1){
                if(menu.getName().equals(s)){
                    a = false;
                }

            }
            if(a){
                Menu menu = menuService.getMenuByName(s);
                menus1.add(menu);
            }
        }
        profile.setMenus(menus1);
        repoProfile.save(profile);

    }

    public List<String> getProfiles() {
        List<String> profiles = new ArrayList<>();
        repoProfile.findAll().forEach(profile -> profiles.add(profile.getName()));
        return profiles;

    }

    public void delete(String name) {
        Profile profile = repoProfile.findByName(name);
        List<User>users=repoUser.findByProfile(profile);
        for(User user : users){
            user.setProfile(null);
            repoUser.save(user);
        }
        profile.getMenus().clear();
        repoProfile.delete(profile);
    }
}
