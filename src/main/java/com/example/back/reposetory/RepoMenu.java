package com.example.back.reposetory;

import com.example.back.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoMenu extends JpaRepository<Menu, Integer> {

    List<Menu> findAll();

    Menu findByName(String name);

    void deleteByName(String name);
}
