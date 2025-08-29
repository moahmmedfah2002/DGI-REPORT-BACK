package com.example.back.reposetory;

import com.example.back.entity.Menu;
import com.example.back.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoProfile  extends JpaRepository<Profile, Integer> {

    List<Profile> findAllByMenusContaining(Menu menu);

    Profile findByName(String admin);

    void deleteByName(String name);
}
