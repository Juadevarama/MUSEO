package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.Keyword;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Integer>{

    
}
