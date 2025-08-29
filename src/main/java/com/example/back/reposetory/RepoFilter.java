package com.example.back.reposetory;

import com.example.back.entity.Filter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoFilter extends JpaRepository<Filter, Integer> {

}
