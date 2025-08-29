package com.example.back.reposetory;

import com.example.back.entity.SousMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoSousMenu extends JpaRepository<SousMenu, Integer> {

    SousMenu findByName(String s);
}
