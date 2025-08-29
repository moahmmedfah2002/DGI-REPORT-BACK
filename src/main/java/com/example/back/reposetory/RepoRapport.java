package com.example.back.reposetory;

import com.example.back.entity.Rapport;
import com.example.back.entity.SousMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
@Repository
public interface RepoRapport extends JpaRepository<Rapport, Integer> {
    @Query("SELECT r FROM Rapport r WHERE r.id_rapport = :id")
    Rapport findById_rapport(int id);

    List<Rapport> findBySousMenus(SousMenu sousMenu);

    Rapport findByName(String name);
}
