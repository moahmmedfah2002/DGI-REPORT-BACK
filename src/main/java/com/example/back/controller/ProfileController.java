package com.example.back.controller;

import com.example.back.dto.ProfileDto;
import com.example.back.dto.UpdateProfileMenu;
import com.example.back.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/profile/")
@RequiredArgsConstructor
public class ProfileController {
    public final ProfileService profileService;
    @PostMapping("add")
    public void createProfile(@RequestBody ProfileDto profileDto) {
        profileService.createProfile(profileDto);

    }
    @PutMapping("/addMenu")
    public void  addMenu(@RequestBody UpdateProfileMenu updateProfileMenu) {
        profileService.addMenu(updateProfileMenu.menu(),updateProfileMenu.id());

    }
    @GetMapping("/getAll")
    public List<String> getAllProfiles() {
        return profileService.getProfiles();

    }
    @DeleteMapping("")
    public void deleteProfile(@RequestParam String name) {
        profileService.delete(name);
    }

}
