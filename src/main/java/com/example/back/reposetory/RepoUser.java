package com.example.back.reposetory;

import com.example.back.entity.Profile;
import com.example.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoUser extends JpaRepository<User, Integer> {


    Optional<User> findUserByUsername(String username);

    Optional<User> findByUsername(String username);

    User getUserByUsername(String username);

    List<User> findByProfile(Profile profile);
}
