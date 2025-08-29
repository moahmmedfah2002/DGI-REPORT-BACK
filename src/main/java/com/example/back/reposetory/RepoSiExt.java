package com.example.back.reposetory;

import com.example.back.entity.SiExterne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoSiExt extends JpaRepository<SiExterne, Integer> {
}
