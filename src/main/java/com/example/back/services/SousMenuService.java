package com.example.back.services;

import com.example.back.dto.SousMenuDto;
import com.example.back.entity.Menu;
import com.example.back.entity.SousMenu;
import com.example.back.reposetory.RepoMenu;
import com.example.back.reposetory.RepoSousMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SousMenuService {
    private final RepoSousMenu repoSousMenu;
    private final RepoMenu repoMenu;
    public void createSousMenu(SousMenuDto sousMenuDto) {
        SousMenu sousMenu = new SousMenu();
        sousMenu.setName(sousMenuDto.name());
        Menu menu = repoMenu.findByName(sousMenuDto.menu());
        menu.getSous_menus().add(sousMenu);
        repoSousMenu.save(sousMenu);
        repoMenu.save(menu);

    }

}
